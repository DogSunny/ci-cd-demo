pipeline {
    agent { label 'vmcent7lable' }
    parameters {
        booleanParam(name: 'RELEASE', defaultValue: true, description: '发布到私服')
        booleanParam(name: 'DEPLOY', defaultValue: true, description: '部署到指定机器')
    }
    stages {
        stage('check') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('RELEASE') {
            steps {
                sh 'mvn package -DskipTests'
                sh 'll ./'
                echo 'package success'
            }
        }
        stage('DEPLOY') {
            steps {
                sh 'mvn --version'
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
