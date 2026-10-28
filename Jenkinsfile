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
                    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                        sh '''
                            curl -sS -X POST \
                              -H "Authorization: Bearer $GITHUB_TOKEN" \
                              -H "Accept: application/vnd.github+json" \
                              "https://api.github.com/repos/amine-java/urlshortener/issues/$CHANGE_ID/comments" \
                              -d "{\\"body\\": \\"Jenkins has checked the PR on $(date '+%Y-%m-%d %H:%M:%S') (build #$BUILD_NUMBER: $BUILD_RESULT)\\"}"
                        '''
                    }
                }
            }
        }
    }
}
