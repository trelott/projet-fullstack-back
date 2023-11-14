pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git branch: 'jenkins-build', url: 'https://github.com/trelott/projet-fullstack-back.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    docker.build("charleswatrelot/covid-api");
                }
            }
        }

        stage('Push') {
            steps {
                sh 'docker login -u charleswatrelot -p dckr_pat_0qWKHQyu6R3aOXmzeqo7f-P1SqU'
                sh 'docker image push charleswatrelot/covid-api'
            }
        }
    }
}