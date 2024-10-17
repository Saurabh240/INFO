### Ansible Basics to Advanced with Code Examples

Ansible is an open-source automation tool for configuration management, application deployment, and task automation. It uses a simple language called YAML to describe automation tasks.

---

### 1. **Ansible Basics**

1. **Ansible Inventory:** 
   - An inventory is a list of nodes (hosts) that Ansible manages.
   - A simple inventory file:

   ```ini
   [webservers]
   server1 ansible_host=192.168.1.100
   server2 ansible_host=192.168.1.101

   [dbservers]
   db1 ansible_host=192.168.1.102
   ```

2. **Ansible Playbooks:**
   - Playbooks are YAML files that define a series of automation tasks.

   Example Playbook:
   
   ```yaml
   ---
   - name: Install and start Nginx
     hosts: webservers
     become: yes
     tasks:
       - name: Install Nginx
         apt:
           name: nginx
           state: present
       - name: Start Nginx
         service:
           name: nginx
           state: started
   ```

3. **Ansible Modules:**
   - Modules are reusable scripts that perform tasks (e.g., `apt`, `yum`, `service`).
   - Common modules include `apt`, `yum`, `copy`, `file`, `service`, `user`, etc.

4. **Running a Playbook:**
   - Run the playbook using the command:
   
   ```bash
   ansible-playbook -i inventory_file playbook.yml
   ```

---

### 2. **Intermediate Concepts**

1. **Variables in Ansible:**
   - Variables allow you to store values dynamically.

   Example with variables:
   
   ```yaml
   ---
   - name: Deploy a website
     hosts: webservers
     vars:
       nginx_port: 8080
     tasks:
       - name: Install Nginx
         apt:
           name: nginx
           state: present
       - name: Configure Nginx to listen on port {{ nginx_port }}
         lineinfile:
           path: /etc/nginx/sites-enabled/default
           regexp: 'listen 80'
           line: 'listen {{ nginx_port }}'
       - name: Start Nginx
         service:
           name: nginx
           state: started
   ```

2. **Handlers in Ansible:**
   - Handlers are triggered when a task reports a change.

   Example with a handler:
   
   ```yaml
   ---
   - name: Install and configure Nginx
     hosts: webservers
     tasks:
       - name: Install Nginx
         apt:
           name: nginx
           state: present
         notify:
           - Restart Nginx

     handlers:
       - name: Restart Nginx
         service:
           name: nginx
           state: restarted
   ```

3. **Ansible Loops:**
   - Loops allow you to iterate over a list.

   Example of a loop:
   
   ```yaml
   ---
   - name: Create multiple users
     hosts: all
     become: yes
     tasks:
       - name: Create users
         user:
           name: "{{ item }}"
           state: present
         loop:
           - alice
           - bob
           - charlie
   ```

4. **Ansible Templates:**
   - Templates are files written using Jinja2 templating language. They allow dynamic content rendering.

   Example of using a template:
   
   ```yaml
   ---
   - name: Configure Nginx using a template
     hosts: webservers
     tasks:
       - name: Copy Nginx configuration template
         template:
           src: nginx.conf.j2
           dest: /etc/nginx/nginx.conf
   ```

   **nginx.conf.j2** (Jinja2 template):
   ```nginx
   server {
       listen {{ nginx_port }};
       server_name {{ server_name }};
   }
   ```

5. **Conditionals:**
   - You can use conditionals to run tasks based on certain conditions.

   Example with a conditional:
   
   ```yaml
   ---
   - name: Install packages based on OS
     hosts: all
     tasks:
       - name: Install Apache on Ubuntu
         apt:
           name: apache2
           state: present
         when: ansible_os_family == "Debian"
       - name: Install httpd on RedHat
         yum:
           name: httpd
           state: present
         when: ansible_os_family == "RedHat"
   ```

---

### 3. **Advanced Concepts**

1. **Roles in Ansible:**
   - Roles help organize playbooks into reusable components.

   Directory structure for a role:
   ```
   roles/
   └── nginx
       ├── tasks
       │   └── main.yml
       ├── handlers
       │   └── main.yml
       ├── templates
       │   └── nginx.conf.j2
       └── vars
           └── main.yml
   ```

   Main playbook using roles:

   ```yaml
   ---
   - name: Configure webservers
     hosts: webservers
     roles:
       - nginx
   ```

2. **Dynamic Inventory:**
   - Ansible supports dynamic inventory where inventory can be generated from cloud platforms like AWS, Azure, etc.

   Example of using AWS dynamic inventory:
   
   ```bash
   ansible-playbook -i aws_ec2.yml playbook.yml
   ```

3. **Ansible Vault:**
   - Ansible Vault allows you to encrypt sensitive data like passwords or keys.

   Encrypt a file:
   
   ```bash
   ansible-vault encrypt vars.yml
   ```

   Decrypt a file:
   
   ```bash
   ansible-vault decrypt vars.yml
   ```

4. **Ansible Galaxy:**
   - Ansible Galaxy is a hub for sharing roles. You can download pre-built roles from Galaxy.

   Download a role:
   
   ```bash
   ansible-galaxy install geerlingguy.nginx
   ```

5. **Ansible Tower (AWX):**
   - Ansible Tower is an enterprise framework for controlling, securing, and managing your Ansible automation with a UI.

6. **Parallelism and Performance:**
   - Control parallelism using the `forks` option in the configuration file:
   
   ```ini
   [defaults]
   forks = 10
   ```

7. **Dynamic Includes and Imports:**
   - Dynamically include tasks or roles based on conditions.
   
   ```yaml
   ---
   - name: Dynamic inclusion
     hosts: all
     tasks:
       - include_tasks: tasks_for_debian.yml
         when: ansible_os_family == "Debian"
       - include_tasks: tasks_for_redhat.yml
         when: ansible_os_family == "RedHat"
   ```

---

### 4. **End-to-End Example: Deploy a Web Server**

1. **Playbook Structure:**
   ```yaml
   ---
   - name: Set up web server
     hosts: webservers
     become: yes
     vars:
       server_port: 80
     tasks:
       - name: Install Nginx
         apt:
           name: nginx
           state: present
       - name: Copy Nginx config
         template:
           src: nginx.conf.j2
           dest: /etc/nginx/nginx.conf
       - name: Start Nginx
         service:
           name: nginx
           state: started
   ```

2. **Jinja2 Template (nginx.conf.j2):**
   ```nginx
   server {
       listen {{ server_port }};
       server_name {{ ansible_hostname }};
   }
   ```

3. **Running the Playbook:**
   ```bash
   ansible-playbook -i inventory_file webserver.yml
   ```

---

### 5. **Best Practices**

- Use **roles** to organize your playbooks.
- Use **Ansible Vault** for secrets management.
- Separate **inventory** files for different environments.
- Write **idempotent** tasks to avoid repeated actions.
- Version control your Ansible playbooks.

---

Ansible offers an easy yet powerful approach to automation. Let me know if you'd like more details or a specific deep dive on any topic!