node {

  environment {}
  stage('Scm checkout') {
    def gitexec = tool name: 'Default', type: 'git'
    git branch: 'master', credentialsId: 'GH', url: 'https://github.com/jugaad-zw/fudzo-main-service'
  }
  stage('Build') {
    def mvnHome = "/usr/share/maven"
    def mvnCMD = "${mvnHome}/bin/mvn"
    sh "${mvnCMD} -B -DskipTests clean package"
  }

  stage('Build docker image') {
    sh 'docker-compose build'
  }


  stage('Push new image') {
   withCredentials([string(credentialsId: 'docker-password-new', variable: 'dockerHubPwd')]) {
   sh "docker login -u cap10 -p ${dockerHubPwd}"
           }
           sh 'docker-compose push'
   }


  stage('Deploy New Image'){
def dockerRun ='docker compose pull && docker compose up'
sshagent(['fudzo']) {
    sh "ssh -o StrictHostKeyChecking=no  venon@38.242.196.215 ${dockerRun}"
}




                }




}
