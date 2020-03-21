pipeline {
    agent { docker { image 'maven:3.3.3' } }
	environment {
		PATH = "/C/dockerjenkins/workspace/MyJavaGames_master:$PATH"
	}
    stages {
        stage('build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}