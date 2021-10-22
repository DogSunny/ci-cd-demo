

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
    environment {
        RELEASED = false
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
        stage('发布到私服') {
            when {
                allOf {
                    not { buildingTag() }
                    environment(name: 'RELEASE', value: 'true')
                }
            }

            steps {
                script {
                    env.RELEASED == 'true'
                }
                sh 'sh prepare.sh'
                sh 'sh perform.sh'
                echo 'release success'
            }
        }
        stage('test') {
            steps {
                echo " ${env.DEPLOY}"
                echo "${env.RELEASED}"
                echo "${env.DEPLOY && !env.RELEASED}"
                echo "${env.DEPLOY && env.RELEASED == 'false'}"
                echo "${env.DEPLOY == 'true' && env.RELEASED == 'false'}"
            }
        }
        stage('Maven 打包') {
            // 声明执行条件
            when {
                expression {
                    // 需要部署，且没发布（发布会自动打包的）
                    return env.DEPLOY == 'true' && env.RELEASED == 'false'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
                sh 'pwd'
                echo 'build success'
            }
        }

        stage('部署到服务器') {
            // 声明执行条件
            when {
                environment(name: 'DEPLOY', value: 'true')
            }
            steps {
                // 计算分支名，作为远端部署的目录，我这里设置了不同分支端口号不一致
                script {
                    if (env.TAG_NAME != null) {
                        echo "TAG_NAME ${env.TAG_NAME}"
                        env.REMOTE_DEPLOY_DIR = env.TAG_NAME.split('-')[-1]
                        if (evn.REMOTE_DEPLOY_DIR == env.TAG_NAME) {
                            evn.REMOTE_DEPLOY_DIR = 'master'
                        }
                    } else {
                        env.REMOTE_DEPLOY_DIR = env.BRANCH_NAME
                    }
                }
                echo "远端部署目录 ${env.REMOTE_DEPLOY_DIR}"
                echo "GIT_BRANCH ${env.GIT_BRANCH}"
                echo "JOB_BASE_NAME ${env.JOB_BASE_NAME}"
                echo "BRANCH_NAME ${env.BRANCH_NAME}"
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
                                                        execCommand: "cd /home/devin/jenkins/${env.REMOTE_DEPLOY_DIR};sh app.sh",
                                                        execTimeout: 120000,
                                                        flatten: false,
                                                        makeEmptyDirs: false,
                                                        noDefaultExcludes: false,
                                                        patternSeparator: '[, ]+',
                                                        remoteDirectory: "/${env.REMOTE_DEPLOY_DIR}",
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

    }
    post {
        always {
            sh 'printenv'
        }

    }
}
