node {
    stage('Initialize') {
        def dockerHome = tool 'Docker'
        def mavenHome = tool 'Maven'
        def javaHome = tool 'openjdk-13'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${javaHome}/bin:${env.PATH}"
    }

    stage('Project Checkout') {
        echo 'Doing Project Checkout..'
        checkout([$class                           : 'GitSCM',
                  branches                         : [[name: '*/master']],
                  doGenerateSubmoduleConfigurations: false,
                  extensions                       : [],
                  submoduleCfg                     : [],
                  userRemoteConfigs                : [[url: 'https://github.com/Nahuel92/to-doList']]
        ])
    }

    stage('Project Build') {
        echo 'Building..'
        sh 'mvn clean package'
    }

    stage('Docker Image Build') {
        docker.build('todo-list')
    }

    stage('Deploy') {
        echo 'Deploying to Docker...'
        sh 'docker-compose up'
    }
}
