pipeline {
    agent { label 'vmcent7lable' }
    parameters {
        booleanParam(name: 'RELEASE', defaultValue: true, description: '发布到私服')
        booleanParam(name: 'DEPLOY', defaultValue: true, description: '部署到指定机器')
    }
    tools {
        maven 'default'
    }
    stages {
        stage('check') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('build') {
            steps {
                sh 'mvn clean package -DskipTests'
                sh 'pwd'
                echo 'build success'
            }
        }
        stage('RELEASE') {
            steps {
                echo 'releces success'
            }
        }
        stage('DEPLOY') {
            steps {
                sh 'mv target/*.jar application.jar'
                sh 'mv target/classes/scripts/app.sh app.sh'
                sh 'chmod +x app.sh'
                sshPublisher(
                        failOnError: true,
                        publishers: [
                                sshPublisherDesc(
                                        configName: 'vmcent7-slave',
                                        transfers: [
                                                sshTransfer(
                                                        cleanRemote: false,
                                                        excludes: '',
                                                        execCommand: './app.sh',
                                                        execTimeout: 120000,
                                                        flatten: false,
                                                        makeEmptyDirs: false,
                                                        noDefaultExcludes: false,
                                                        patternSeparator: '[, ]+',
                                                        remoteDirectory: '/',
                                                        remoteDirectorySDF: false,
                                                        removePrefix: '',
                                                        sourceFiles: 'application.jar,app.sh')
                                        ],
                                        usePromotionTimestamp: false,
                                        useWorkspaceInPromotion: false,
                                        verbose: false
                                )
                        ]
                )
            }

        }
        stage('printenv') {
            steps {
                sh 'printenv'
            }
        }
    }
    post {
        always {
            echo 'always run'
        }

    }
}
