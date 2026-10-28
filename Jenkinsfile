pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }

    post {
        always {
            script {
                // Only for pull request builds (CHANGE_ID is the PR number)
                if (env.CHANGE_ID) {
                    env.BUILD_RESULT = currentBuild.currentResult
                    // GitHub App credential used by this multibranch job: the password is an installation token
                    withCredentials([usernamePassword(credentialsId: '3d5d38c8-3156-405b-845b-a8f98ad84021',
                                                      usernameVariable: 'GITHUB_APP',
                                                      passwordVariable: 'GITHUB_TOKEN')]) {
                        sh '''
                            curl -sS --fail -X POST \
                              -H "Authorization: Bearer $GITHUB_TOKEN" \
                              -H "Accept: application/vnd.github+json" \
                              "https://api.github.com/repos/amine-java/urlshortener/issues/$CHANGE_ID/comments" \
                              -d "{\\"body\\": \\"Jenkins has checked the PR on $(date '+%Y-%m-%d %H:%M:%S') (build #$BUILD_NUMBER: $BUILD_RESULT)\\"}"
                        '''
                    }
                } else {
                    echo 'Not a pull request build (CHANGE_ID is empty): no PR comment posted.'
                }
            }
        }
    }
}
