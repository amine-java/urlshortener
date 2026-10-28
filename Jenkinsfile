pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        DOCKERHUB_REPO_BACK  = 'aminejava/urlshortener'
        DOCKERHUB_REPO_FRONT = 'aminejava/urlshortener-front'
        // Jenkins credential (Username with password) holding the DockerHub login
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub-credentials'
    }

    stages {
        stage('Build & Test back-end') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-11'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                sh 'mvn -B clean install'
                script {
                    env.PROJECT_VERSION = sh(
                        script: 'mvn -B help:evaluate -Dexpression=project.version -q -DforceStdout',
                        returnStdout: true
                    ).trim()
                }
                echo "Project version: ${env.PROJECT_VERSION}"
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
                success {
                    archiveArtifacts artifacts: 'urlshortener-api/target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker images') {
            parallel {
                stage('Back-end image') {
                    steps {
                        sh "docker build -t ${DOCKERHUB_REPO_BACK}:${PROJECT_VERSION}-RC3 ."
                    }
                }
                stage('Front-end image') {
                    steps {
                        dir('front/url-shortener-front') {
                            sh "docker build -t ${DOCKERHUB_REPO_FRONT}:latest ."
                        }
                    }
                }
            }
        }

        stage('Push Docker images') {
            when {
                branch 'main'
            }
            steps {
                withCredentials([usernamePassword(credentialsId: env.DOCKERHUB_CREDENTIALS_ID,
                                                  usernameVariable: 'DOCKERHUB_USERNAME',
                                                  passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    sh 'echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin'
                    sh "docker push ${DOCKERHUB_REPO_BACK}:${PROJECT_VERSION}-RC3"
                    sh "docker push ${DOCKERHUB_REPO_FRONT}:latest"
                }
            }
            post {
                always {
                    sh 'docker logout || true'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
