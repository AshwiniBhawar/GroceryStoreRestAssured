pipeline {
    agent any
    
    tools{
		maven 'Maven'
	}

	environment {
            DOCKER_IMAGE = "ashwinibhawar2892/grocerystoreframework:${BUILD_NUMBER}"
            DOCKER_CREDENTIALS_ID = 'dockerhub_credentials'
        }

  stages {
		
		stage('Clean Workspace') {
            steps {
                script {
                    // Clean the workspace before running the job
                    cleanWs()
                }
            }
        }
        
        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            
            post{
				success{
					junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
				}
			}
        }

        stage('Checkout Code') {
              steps {
                  git 'https://github.com/AshwiniBhawar/APIFramaework.git'
              }
        }

        stage('Build Docker Image') {
              steps {
                   bat "docker build -t %DOCKER_IMAGE% ."
              }
        }

        stage('Push Docker Image to Docker Hub') {
              steps {
                    withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
              )]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push %DOCKER_IMAGE%
                    '''
                    }
              }
        }

        stage("Deploy to QA") {
            steps {
               echo "Deploying to QA"
            }
        }

        stage('Run QA API Automation Tests') {
           steps {
                script {
                    def status = bat(
                        script: """
                            docker run --rm -v "%WORKSPACE%":/app -w /app %DOCKER_IMAGE% \
                            mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/sanity.xml -Denv=qa
                        """,
                        returnStatus: true
                )
             if (status != 0) {
                 currentBuild.result = 'UNSTABLE'
                 }
             }
           }
        }

        stage('Publish ChainTest Report For QA') {
             steps {
                 publishHTML([
                     allowMissing: false,
                     alwaysLinkToLastBuild: false,
                     keepAll: true,
                     reportDir: 'target/chaintest',
                     reportFiles: '*.html',
                     reportName: 'HTML QA API Report',
                     reportTitles: ''
                 ])
             }
        }

        stage("Deploy to UAT"){
              steps{
                    echo("Deploy to UAT")
              }
        }

        stage('Run UAT API Automation Tests') {
            steps {
                 script {
                       def status = bat(
                        script: """
                        docker run --rm -v "%WORKSPACE%":/app -w /app %DOCKER_IMAGE% \
                        mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/regression.xml -Denv=uat
                        """,
                  returnStatus: true
                 )
                 if (status != 0) {
                   currentBuild.result = 'UNSTABLE'
                 }
                 }
            }
        }

        stage('Publish ChainTest Report For UAT') {
                steps {
                     publishHTML([
                         allowMissing: false,
                         alwaysLinkToLastBuild: false,
                         keepAll: true,
                         reportDir: 'target/chaintest',
                         reportFiles: '*.html',
                         reportName: 'HTML UAT API Report',
                         reportTitles: ''
                     ])
                }
        }

        stage('Publish Allure Report') {
            steps {
                    allure([
                       includeProperties: false,
                       jdk: '',
                       properties:[],
                       reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
            }
        }
  }
}