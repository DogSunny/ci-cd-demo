

pipeline {
    // 设置构建环境
    agent {
        // 执行构建的机器
        label 'vmcent7lable'
    }
    options {
        // 插件彩色日志
        ansiColor('xterm')
        // 输出带时间戳
        timestamps()
    }
    parameters {
        // 脚本需要的参数
        booleanParam(name: 'RELEASE', defaultValue: false, description: '发布到私服')
        booleanParam(name: 'DEPLOY', defaultValue: true, description: '部署到服务器')
    }
    tools {
        // 使用默认的 maven
        maven 'default'
    }
    stages {
        stage('Maven Package') {
            steps {
                sh 'mvn clean package -DskipTests'
                sh 'pwd'
                echo 'build success'
            }
        }
        stage('发布到私服') {
            when {
                allOf {
                    not { buildingTag() }
                    environment(name: 'RELEASE', value: true)
                }
            }
            steps {
                sh 'chmod +x *.sh'
                sh './prepare.sh'
                sh './perform.sh'
                echo 'release success'
            }
        }
        stage('DEPLOY') {
            // 声明执行条件
            when {
                environment(name: 'DEPLOY', value: true)
            }
            steps {
                // 计算分支名，作为远端部署的目录，我这里设置了不同分支端口号不一致
                script {
                    if (evn.TAG_NAME != null) {
                        environment {
                            REMOTE_DEPLOY_DIR = evn.TAG_NAME.split('-')[-1]
                        }
                    }
                    else {
                        environment {
                            REMOTE_DEPLOY_DIR = evn.GIT_BRANCH
                        }
                    }
                    echo "远端部署目录 ${evn.REMOTE_DEPLOY_DIR}"
                }
                // 移动文件，方便操作
                sh 'mv target/*.jar app.jar'
                sh 'mv target/classes/scripts/app.sh app.sh'
                // 使用插件通过ssh上传文件，执行命令
                sshPublisher(
                        failOnError: true,
                        publishers: [
                                sshPublisherDesc(
                                        configName: 'vmcent7-slave',
                                        transfers: [
                                                sshTransfer(
                                                        cleanRemote: false,
                                                        excludes: '',
                                                        execCommand: "cd /home/devin/jenkins/${evn.REMOTE_DEPLOY_DIR};sh app.sh",
                                                        execTimeout: 120000,
                                                        flatten: false,
                                                        makeEmptyDirs: false,
                                                        noDefaultExcludes: false,
                                                        patternSeparator: '[, ]+',
                                                        remoteDirectory: "/${evn.REMOTE_DEPLOY_DIR}",
                                                        remoteDirectorySDF: false,
                                                        removePrefix: '',
                                                        sourceFiles: 'app.jar,app.sh')
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
