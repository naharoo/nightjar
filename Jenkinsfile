#!groovy​

buildProfile = "development-ci"
if (BRANCH_NAME == "master") {
    buildProfile = "production"
}

// Adds version information to artifacts, for Spinnaker
@NonCPS
def static createRunInformationJson(workspacePath, pom) {

    def fileContent = ""

    fileContent += "version=" + pom.version + "\n"
    fileContent += "DOCKER_VERSION=" + pom.version + "\n"

    def directoryPath = workspacePath + "/target"
    def filePath = workspacePath + "/target/run-information.properties"

    new File(directoryPath).mkdirs()

    def file = new File(filePath)
    file.createNewFile()
    file.write(fileContent)
}

pipeline {
    agent any

    parameters {
        string(name: 'DOCKER_TAG', defaultValue: '', description: 'Optional custom docker tag to tag and push the docker image with. Leave blank to skip pushing custom tags. "/"-s will be replaced with "-"-s. "latest" and "snapshot" are not supported.')
    }

    environment {
        DOCKER_HOST = 'tcp://172.17.0.1:4243'

        // This will create DOCKER_ACCESS_USR and DOCKER_ACCESS_PSW variables
        DOCKER_ACCESS = credentials('jenkins-nexus-credentials')

        // This will create MAVEN_ACCESS_USR and MAVEN_ACCESS_PSW variables
        MAVEN_ACCESS = credentials('jenkins-nexus-credentials')
    }

    tools {
        jdk "jdk-11.0.6"
        maven 'maven-3.6.1'
    }

    stages {
        stage('Prepare') {
            steps {
                checkout scm
                sh "mvn clean"
                script {
                    properties([pipelineTriggers([[$class: 'GitHubPushTrigger'], pollSCM('H/10 * * * *')])])
                    properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '70', artifactNumToKeepStr: '50', daysToKeepStr: '', numToKeepStr: '']]])
                }
            }
        }

        stage('Build & test') {
            steps {
                sh "mvn -B -T 1.5C -P ${buildProfile} verify -Ddockerfile.build.skip=true"
                script {
                    pom = readMavenPom file: 'pom.xml'
                    createRunInformationJson(pwd(), pom)
                }
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml,**/target/failsafe-reports/*.xml'
                }
                unstable {
                    junit '**/target/surefire-reports/*.xml,**/target/failsafe-reports/*.xml'
                }
            }
        }

        stage('Push to docker') {
            when {
                expression { return (BRANCH_NAME == "master" || BRANCH_NAME == "develop") }
            }
            steps {
                archiveArtifacts artifacts: 'target/*.jar, ' +
                        'target/run-information.properties', fingerprint: true

                sh "mvn -B -T 1.5C -P ${buildProfile} -s mavenSettings.xml -Dmaven.test.skip=true deploy"
            }
        }

        stage('Push custom tag to docker') {
            when {
                expression {
                    return (params.DOCKER_TAG != '' && params.DOCKER_TAG != "latest" && params.DOCKER_TAG != "snapshot")
                }
            }
            steps {
                archiveArtifacts artifacts: 'target/*.jar, ' +
                        'target/run-information.properties', fingerprint: true

                sh "mvn -B -T 1.5C -P custom-docker-tag -s mavenSettings.xml -Dmaven.test.skip=true -Ddocker.tag=${params.DOCKER_TAG} deploy"
            }
        }

        stage('Cleanup') {
            steps {
                cleanWs()
            }
        }
    }
}
