pipeline {
	agent any

    stages {
		stage('Start Pipeline') {
			steps {
				script {
					echo "=== PIPELINE STARTED ==="
                }
            }
        }

        stage('Checkout Code') {
			steps {
				git 'https://github.com/vspelykh/staff.git'
            }
        }

        stage('Build') {
			steps {
				script {
					echo "=== BUILD STARTED ==="
                }
                sh 'mvn clean compile'
                script {
					echo "=== BUILD COMPLETED ==="
                }
            }
        }

        stage('Run Tests') {
			steps {
				script {
					echo "=== TESTS STARTED ==="
                }
                sh 'mvn test'
                script {
					echo "=== TESTS COMPLETED ==="
                }
            }
        }

        stage('End Pipeline') {
			steps {
				script {
					echo "=== PIPELINE COMPLETED ==="
                }
            }
        }
    }
}
