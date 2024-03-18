pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Checkout source code from version control
                git 'https://github.com/your/repo.git'

                sh './gradlew clean build'
            }
        }

        stage('Deploy to AWS') {
            environment {
                AWS_ACCESS_KEY_ID = credentials('your-aws-access-key-id')
                AWS_SECRET_ACCESS_KEY = credentials('your-aws-secret-access-key')
            }
            steps {
                // Deploy CloudFormation stack for Fargate service
                sh 'aws cloudformation deploy --template-file github-codechallenge-service.yaml --stack-name github-codechallenge-service-stack --region your-region'

                // Deploy CloudFormation stack for API Gateway
                sh 'aws cloudformation deploy --template-file api-gateway.yaml --stack-name github-codechallenge-api-stack --region your-region'
            }
        }
    }
}
