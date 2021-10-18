pipeline {
    agent { label 'vmcent7lable' }
    parameters {
        //choice(name: 'CHOICES', choices: ['one', 'two', 'three'], description: 'Choice of Destiny')
        //string(name: 'STRING', defaultValue: 'defaultString', description: 'Type Any String Here')
        //text(name: 'TEXT', defaultValue: 'One\nTwo\nThree\n', description: 'A text parameter, which can contain multiple line')
        gitParameter(name: 'BRANCH_TAG', type: 'PT_BRANCH_TAG', defaultValue: 'origin/master', description: "选择版本或分支")
        booleanParam(name: 'BUILD', defaultValue: true, description: '构建项目')
        booleanParam(name: 'RELEASE', defaultValue: true, description: '发布到私服')
        booleanParam(name: 'DEPLOY', defaultValue: true, description: '部署到指定机器')
        //password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'A secret password')

    }
    stages {
        stage('print info') {
            steps {
                echo "${params.BRANCH_TAG}"
            }
        }
        stage('checkout') {
            steps {
                /*checkout([$class: 'GitSCM',
                          branches: [[name: "${params.BRANCH_TAG}"]],
                          doGenerateSubmoduleConfigurations: false,
                          extensions: [],
                          gitTool: 'Default',
                          submoduleCfg: [],
                          *//*userRemoteConfigs: [[url: 'https://github.com/jenkinsci/git-parameter-plugin.git']]*//*
                ])*/
                echo 'checkout'
            }
        }
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('evn') {
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
