pipeline {
    environment {
        JAVA_TOOL_OPTIONS = "-Duser.home=/var/maven"
        APP_NAME = "build-the-guild"
        PORT = "8104"
    }

    agent {
        docker {
            image "maven:3.8.7-openjdk-18"
            args "-v /tmp/maven:/var/maven/.m2 -e MAVEN_CONFIG=/var/maven/.m2"
        }
    }

    stages {
        stage("Build") {
            steps {
                sh "mkdir -p jenkins-creds"
                dir("jenkins-creds") {
                    git branch: 'main', credentialsId: 'jenkins-uspas', url: 'https://github.com/GORR174/jenkins-credentials.git'
                }
//                 sh "cat Dockerfile >> jenkins-creds/${env.APP_NAME}.txt"
//                 sh "cat jenkins-creds/${env.APP_NAME}.txt > Dockerfile"
                sh "(cat Dockerfile; echo \"\"; cat jenkins-creds/${env.APP_NAME}.txt; echo ) > Dockerfile.tmp"
                sh "cat Dockerfile.tmp > Dockerfile"

                sh "mvn -version"
                sh "mvn clean install -DskipTests"
            }
        }
        stage("Tests") {
            steps {
                sh "mvn surefire:test"
            }
        }
        stage("Build Docker Image") {
            steps {
                sh "docker image prune -f"
                sh "docker build --force-rm -t catstack/${env.APP_NAME} ."
            }
        }
        stage("Run Docker Image") {
            steps {
                sh "docker stop ${env.APP_NAME} || true && docker container rm ${env.APP_NAME} || true"
                sh "docker run --name ${env.APP_NAME} -d -p ${env.PORT}:8080 catstack/${env.APP_NAME}"
                sh "docker image prune -f"
            }
        }
    }

    post {
        always {
            script {
                withCredentials([string(credentialsId: 'telegram-bot-token', variable: 'TOKEN'),
                        string(credentialsId: 'telegram-bot-chat-id', variable: 'CHAT_ID')]) {
                sh """
                curl -s -X POST https://api.telegram.org/bot$TOKEN/sendMessage -d chat_id=$CHAT_ID -d parse_mode=markdown -d text="\
*${env.BUILD_TAG}*: Build Complete!\n\n\
*Project:* ${env.APP_NAME}\n\
*Build status:* ${currentBuild.currentResult}\n\n\
*App URL:* catstack.net:${env.PORT}/
"                    """
                }
            }

            cleanWs()
        }
    }
}