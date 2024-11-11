### Step 1: Create a Spring Boot Template

1. **Define a template file** (e.g., `springboot-template.yml`).
2. **Include Parameters and Variables**: Add parameters to allow customization (e.g., Java version, build profile, and environment variables).
3. **Integrate Secrets**: Use `$(<secret-name>)` syntax to reference secrets stored in Azure DevOps.

Here is a sample Spring Boot template:

#### `azure-pipelines/templates/springboot-template.yml`

```yaml
parameters:
  javaVersion: '11'                  # Specify the Java version to use
  buildConfiguration: 'Release'      # Build configuration (e.g., Release or Debug)
  buildPlatform: 'Any CPU'           # Platform configuration
  springProfile: 'default'           # Spring profile (e.g., dev, prod)
  environmentName: ''                # Environment (e.g., Dev, Prod)
  appName: 'springboot-app'          # Name of the application
  dockerImageName: 'myapp-image'     # Docker image name for containerized apps (if needed)
  dockerRegistry: ''                 # Docker registry (if applicable)
  secrets:                           # Dictionary to store secret names
    dbPassword: 'DB_PASSWORD'        # Secret for database password
    apiKey: 'API_KEY'                # Secret for external API key

jobs:
  - job: BuildAndTest
    displayName: 'Build and Test Spring Boot App'
    pool:
      vmImage: 'ubuntu-latest'

    steps:
      # Install Java
      - task: UseJavaVersion@1
        inputs:
          versionSpec: ${{ parameters.javaVersion }}
          jdkArchitecture: 'x64'

      # Set up Maven cache
      - task: Cache@2
        inputs:
          key: 'maven | "$(Agent.OS)" | pom.xml'
          restoreKeys: |
            maven | "$(Agent.OS)"
          path: $(MAVEN_CACHE_FOLDER)

      # Maven build
      - script: |
          mvn clean install -P${{ parameters.springProfile }} -DskipTests
        displayName: 'Build the Spring Boot application'

      # Run tests
      - script: |
          mvn test -P${{ parameters.springProfile }}
        displayName: 'Run Tests'

      # Publish test results
      - task: PublishTestResults@2
        inputs:
          testResultsFiles: '**/target/surefire-reports/TEST-*.xml'
          testRunTitle: 'Spring Boot Tests'
          failTaskOnFailedTests: true

  - job: Deploy
    displayName: 'Deploy Spring Boot App'
    dependsOn: BuildAndTest
    pool:
      vmImage: 'ubuntu-latest'

    variables:
      DB_PASSWORD: $(secrets.${{ parameters.secrets.dbPassword }})  # Access secret for DB password
      API_KEY: $(secrets.${{ parameters.secrets.apiKey }})          # Access secret for external API

    steps:
      # Deploy using a Docker image (if containerized)
      - script: |
          docker build -t ${{ parameters.dockerImageName }} .
          echo $(DB_PASSWORD) | docker login -u myUser --password-stdin ${{ parameters.dockerRegistry }}
          docker tag ${{ parameters.dockerImageName }} ${{ parameters.dockerRegistry }}/${{ parameters.dockerImageName }}
          docker push ${{ parameters.dockerRegistry }}/${{ parameters.dockerImageName }}
        displayName: 'Build and push Docker image'

      # Or Deploy using JAR (non-containerized app)
      - script: |
          java -jar target/${{ parameters.appName }}.jar --spring.profiles.active=${{ parameters.springProfile }} \
          --DB_PASSWORD=$(DB_PASSWORD) --API_KEY=$(API_KEY)
        displayName: 'Deploy Spring Boot application'
        condition: ne(variables['BUILD_REASON'], 'PullRequest')  # Skip deployment for PRs
```

### Step 2: Use the Template in Your Pipeline

Now that you have the `springboot-template.yml`, reference it in your main pipeline YAML file, passing the required parameters and secrets.

#### Example of Using the Template in `azure-pipelines.yml`

```yaml
trigger:
  branches:
    include:
      - main

variables:
  # Standard variables for environment settings
  environmentName: 'Production'
  springProfile: 'prod'
  javaVersion: '11'

  # Specify Docker and application details if needed
  dockerRegistry: 'myregistry.azurecr.io'
  dockerImageName: 'springboot-app'

stages:
  - stage: BuildAndDeploy
    displayName: 'Build and Deploy Spring Boot Application'
    jobs:
      - template: templates/springboot-template.yml
        parameters:
          javaVersion: $(javaVersion)
          buildConfiguration: 'Release'
          springProfile: $(springProfile)
          environmentName: $(environmentName)
          appName: 'my-springboot-app'
          dockerImageName: $(dockerImageName)
          dockerRegistry: $(dockerRegistry)
          secrets:
            dbPassword: 'MY_DB_PASSWORD'
            apiKey: 'MY_API_KEY'
```

### Explanation of Key Sections

- **Parameters**: Use parameters for Java version, environment, build profile, and any deployment-specific information (like Docker details).
- **Secrets**: Use secret variables stored in Azure DevOps and pass them as parameters to the template. This keeps sensitive information secure and reduces hardcoding.
- **Build and Test**: Run a Maven build and test job.
- **Deployment Steps**:
  - The template can handle both containerized and non-containerized deployments based on your needs. 
  - If deploying with Docker, specify `dockerImageName` and `dockerRegistry`.
  - Non-containerized deployment uses a `java -jar` command to run the Spring Boot application, with secrets (like `DB_PASSWORD`) passed as environment variables.
  
### Tips

1. **Variable Groups**: Use variable groups to organize environment-specific settings in Azure DevOps and reference them in the pipeline for better manageability.
2. **Secure Files**: Store sensitive files (like keystores or SSL certs) securely in Azure DevOps and reference them in your pipeline if needed.
3. **Parameters Flexibility**: Add more parameters if you anticipate differences in environments (e.g., different build profiles or application names). 