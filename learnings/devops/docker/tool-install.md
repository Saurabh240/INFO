### Run Jenkins in Docker

docker network create jenkins-sonarqube
mkdir C:\jenkins_home
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins --network jenkins-sonarqube -v C:\jenkins_home:/var/jenkins_home jenkins/jenkins:lts
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword

### Run Sonarqube on Docker
mkdir C:\sonarqube_data
mkdir C:\sonarqube_logs
mkdir C:\sonarqube_extensions

docker run -d -p 9000:9000 --name sonarqube --network jenkins-sonarqube -v C:\sonarqube_data:/opt/sonarqube/data -v C:\sonarqube_logs:/opt/sonarqube/logs -v C:\sonarqube_extensions:/opt/sonarqube/extensions sonarqube:lts



mvn clean verify sonar:sonar `
-D"sonar.projectKey=emp-mgmt-project_emp-mgmt-repo_AZMKX6caq0wq_ekJzBXQ" `
-D"sonar.host.url=http://localhost:9000" `
-D"sonar.login=sqp_2de573bd5f8d3446eab79b88c26fc94d6ac56a95"




====
To create a Jenkins pipeline that uses SonarQube for static code analysis on your Spring Boot application and can run on a local Jenkins instance, here are the steps from configuring Jenkins locally to making changes in your Spring Boot repository.

### Step 1: Set Up Local Jenkins

1. **Install Jenkins**:
   - Download Jenkins from [Jenkins.io](https://www.jenkins.io/download/) and follow the setup instructions for your OS.
   - Start Jenkins and complete the initial setup wizard.

2. **Install Required Plugins**:
   - Go to **Manage Jenkins** > **Manage Plugins** > **Available Plugins**.
   - Install:
     - **SonarQube Scanner** (for SonarQube integration)
     - **Pipeline Utility Steps** (for YAML file parsing)
     - **Git** (for source control)
   - Restart Jenkins after installation if needed.

3. **Configure SonarQube in Jenkins**:
   - Go to **Manage Jenkins** > **Configure System** > **SonarQube Servers**.
   - Click **Add SonarQube** and provide:
     - **Name**: Name your SonarQube server (e.g., `SonarQube`).
     - **Server URL**: URL for SonarQube (e.g., `http://localhost:9000` if running locally).
     - **Server Authentication Token**: Add a **Jenkins Credential** with your SonarQube token.

4. **Create a GitHub or Local Repository with Jenkinsfile**:
   - Ensure your Spring Boot project is version-controlled in GitHub or a local Git repository.
   - You will add the Jenkinsfile to this repository in the following steps.

### Step 2: Configure SonarQube Locally

1. **Install and Run SonarQube**:
   - Download SonarQube from [SonarQube](https://www.sonarqube.org/downloads/).
   - Start SonarQube by running `./sonar.sh start` from the bin folder.
   - Open SonarQube at `http://localhost:9000` in a browser.

2. **Create a SonarQube Project and Token**:
   - Log in to SonarQube (default credentials: `admin`/`admin`).
   - Create a new project, set the project key, and generate a token.
   - Copy this token (you’ll use it in the Jenkins pipeline).

### Step 3: Update Your Spring Boot Repository

1. **Add `config.yml` to the Repository Root**:
   - Add a `config.yml` file at the root of your Spring Boot project. This file will contain the configuration for SonarQube:
   
   ```yaml
   environment:
     sonarqubeHost: "http://localhost:9000"
     sonarqubeToken: "your-sonarqube-token"
     projectKey: "spring-boot-app"
   ```

2. **Add a Jenkinsfile to Your Repository**:
   - Create a `Jenkinsfile` at the root of your Spring Boot project.
   - Use the following Jenkinsfile template to define the pipeline steps:

   ```groovy
   pipeline {
       agent any
       
       environment {
           CONFIG_FILE = 'config.yml'
           SONARQUBE = credentials('sonarqube-token-id')
       }

       stages {
           stage('Checkout') {
               steps {
                   checkout scm
               }
           }

           stage('Load Config') {
               steps {
                   script {
                       def config = readYaml file: "${CONFIG_FILE}"
                       env.SONAR_HOST = config.environment.sonarqubeHost
                       env.SONAR_PROJECT_KEY = config.environment.projectKey
                       env.SONAR_TOKEN = config.environment.sonarqubeToken
                   }
               }
           }

           stage('Build') {
               steps {
                   sh './mvnw clean package -DskipTests'
               }
           }

           stage('SonarQube Analysis') {
               environment {
                   SONAR_HOST_URL = env.SONAR_HOST
                   SONAR_AUTH_TOKEN = env.SONAR_TOKEN
               }
               steps {
                   script {
                       withSonarQubeEnv('SonarQube') {
                           sh './mvnw sonar:sonar ' +
                              "-Dsonar.projectKey=${env.SONAR_PROJECT_KEY} " +
                              "-Dsonar.host.url=${SONAR_HOST_URL} " +
                              "-Dsonar.login=${SONAR_AUTH_TOKEN}"
                       }
                   }
               }
           }

           stage('Post Analysis Quality Gate') {
               steps {
                   timeout(time: 5, unit: 'MINUTES') {
                       waitForQualityGate abortPipeline: true
                   }
               }
           }
       }

       post {
           always {
               archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
           }
           failure {
               echo 'Pipeline failed.'
           }
           success {
               echo 'Pipeline completed successfully.'
           }
       }
   }
   ```

   - **Replace `sonarqube-token-id`** with your Jenkins credential ID for the SonarQube token.

### Step 4: Run Pipeline on Local Jenkins

1. **Create a New Pipeline Job**:
   - Go to **Jenkins Dashboard** > **New Item**.
   - Name your job, select **Pipeline**, and click **OK**.

2. **Configure Pipeline**:
   - Under **Pipeline** settings:
     - Choose **Pipeline script from SCM**.
     - Select **Git** and provide your repository URL.
     - Set the **Script Path** to `Jenkinsfile`.
   - Save the job configuration.

3. **Run the Pipeline**:
   - Click **Build Now** to start the pipeline.
   - Monitor the build logs to see each stage’s output.

4. **Check SonarQube for Analysis Results**:
   - Once the pipeline completes, go to your SonarQube server at `http://localhost:9000`.
   - Check your project dashboard for analysis results and the Quality Gate status.

### Summary of Changes to Spring Boot Repository

1. **Add `config.yml`** for SonarQube configuration variables.
2. **Add `Jenkinsfile`** to define the pipeline stages for build, SonarQube analysis, and post-analysis checks.
====
