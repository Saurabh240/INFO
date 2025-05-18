### Q-1 What is Git Squash and Merge ?
**Git Squash and Merge Strategy** is a Git workflow technique used to combine multiple commits into a single commit before merging into the main branch. This strategy is useful for keeping the Git history clean and concise, as it allows you to condense a large number of intermediate, granular commits into a single meaningful commit.

### Key Concepts:
1. **Squash**: It compresses multiple commits into one. The result is a simpler, cleaner Git history.
2. **Merge**: Combines branches, integrating changes from one branch into another.

### When to Use Squash and Merge:
- When you have multiple small, iterative commits on a feature branch that don't need to be preserved individually (e.g., work-in-progress commits).
- When you want to maintain a clean history with one commit per feature or bug fix.
- When you're working in a **feature branching model**, where feature branches are eventually merged into the main branch (like `master` or `main`).

### Benefits:
- **Clean History**: A single, atomic commit per feature or bug fix simplifies the Git history and makes it easier to track what was changed.
- **Focused Commits**: Squashing commits helps create focused, meaningful commits with relevant commit messages.
- **Reduces Noise**: Eliminates unnecessary intermediate commits that might include debugging, fixing typos, or minor tweaks.
  
### How Squash and Merge Works:

#### 1. **Locally Squashing Commits Before Merging**

You can use the `git rebase` command to squash commits into a single commit before merging the branch.

##### Step-by-Step Example:

1. **Check out the feature branch**:
   ```bash
   git checkout feature-branch
   ```

2. **Rebase interactively to squash commits**:
   - If you want to squash the last 3 commits:
     ```bash
     git rebase -i HEAD~3
     ```
   - This will open a text editor where you can see a list of the last 3 commits:
     ```
     pick 1234567 First commit
     pick 2345678 Second commit
     pick 3456789 Third commit
     ```
   - Change `pick` to `squash` for the second and third commits:
     ```
     pick 1234567 First commit
     squash 2345678 Second commit
     squash 3456789 Third commit
     ```

3. **Rebase completes**: You'll be prompted to edit the commit message for the squashed commit. You can either combine the messages from all squashed commits or write a new commit message.

4. **Push the squashed changes** (use `-f` if needed):
   ```bash
   git push origin feature-branch --force
   ```

5. **Merge the squashed commit into the main branch**:
   ```bash
   git checkout main
   git merge feature-branch
   ```

#### 2. **Using Squash and Merge in GitHub or GitLab**

Most Git hosting services like **GitHub**, **GitLab**, and **Bitbucket** provide an option for **Squash and Merge** directly in the user interface when merging pull requests.

##### Example in GitHub:
1. Open the **Pull Request**.
2. Click the dropdown on the **Merge button**.
3. Select **Squash and Merge**.
4. GitHub will allow you to edit the commit message, which is a combination of all the squashed commit messages.
5. Click **Confirm** to squash all commits in the pull request into one and merge them into the base branch.

### Commands Involved in Git Squash and Merge:

1. **Rebase Interactively (`git rebase -i`)**:
   - Used to squash commits locally. You can interactively select which commits to squash and combine.

2. **Squash and Merge via GitHub/GitLab UI**:
   - Provides a built-in option to squash commits before merging.

3. **Force Push (`git push --force`)**:
   - After squashing, you may need to force-push your changes if you've rewritten history in the feature branch.

### Example Scenario:

You have the following commit history in your `feature-branch`:

```
commit 1234567 - Fix bug in API
commit 2345678 - Update API documentation
commit 3456789 - Refactor API logic
```

You want to squash these three commits into one meaningful commit before merging into `main`.

1. **Rebase interactively**: Use `git rebase -i HEAD~3` to squash them.
2. **Resulting commit**: After squashing, the commit history would look like:
   ```
   commit 4567890 - Update API with refactor and documentation changes
   ```

3. **Merge to main**: You can now merge this single commit into the `main` branch.

### Pros and Cons of Squash and Merge

#### **Pros**:
- **Clean history**: Combines multiple small commits into one, making the commit history easier to read.
- **Focused commits**: Each commit in the main branch is meaningful, usually representing a complete feature or fix.
- **Simplifies pull requests**: Maintainers only need to review a single commit per feature or bug fix.

#### **Cons**:
- **Lose detailed history**: Intermediate commits (e.g., debug commits, small fixes) are lost, which might be useful in some cases.
- **Complex merge conflicts**: If there are merge conflicts during the squash process, resolving them can be more challenging than in a regular merge.

### Conclusion:

- **Git Squash and Merge** is ideal when you want to maintain a clean and concise commit history, typically in projects where each feature or fix is merged into the main branch as a single commit.
- It can be done locally using `git rebase -i`, or via hosting services like GitHub using their **Squash and Merge** functionality.
- While it cleans up history and simplifies logs, it's important to weigh the pros and cons based on your team's workflow and the level of detail you need in your commit history.