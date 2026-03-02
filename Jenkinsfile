pipeline {
    agent any
    
    tools{
		maven 'Maven'
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

        stage("Deploy to QA"){
            steps{
                echo("Deploy to QA")
            }
        }

        stage('Run QA Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
                    git 'https://github.com/AshwiniBhawar/GroceryStoreRestAssured.git'
                    bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/regression.xml"
                }
            }
        }

//         stage('Publish Allure Report For QA') {
//                 steps {
//                      allure([
//                          includeProperties: false,
//                          jdk: '',
//                          properties:[],
//                          reportBuildPolicy: 'ALWAYS',
//                          results: [[path: 'allure-results']]
//                      ])
//                 }
//         }

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

        stage('Run UAT Tests') {
              steps {
                   catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
                        git 'https://github.com/AshwiniBhawar/GroceryStoreRestAssured.git'
                        bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/sanity.xml"
                   }
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
  }
}