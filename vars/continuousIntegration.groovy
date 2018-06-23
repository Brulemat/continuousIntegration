#!/usr/bin/env groovy

// fr/brulemat/GlobalVars.groovy
// /vars/continuousIntegration.groovy
def call(Closure context) {
    // création d'un map
    def config = [:]
    // affecter la map en tant que delegate de la closure
    context.resolveStrategy = Closure.DELEGATE_FIRST
    context.delegate = config
    // appeler la closure
    context()
    // commencer son workflow
    node {
        stage('clean') {
            deleteDir()
        }
        stage('checkout') {
            def branch = 'master'
            if (env.getEnvironment().containsKey('BRANCH')) {
                branch = BRANCH
            }
            git branch: branch, credentialsId: config.credentialsId, url: config.gitUrl
        }
        stage('configure') {
            def branches = "master\ndevelop"
            // récupérer automatiquement les branches via un script sh git
            //noinspection GroovyAssignabilityCheck
            properties([
                    parameters([
                            choice(choices: branches, description: 'Branche de build', name: 'BRANCH'),
                            booleanParam(defaultValue: false, description: 'Faire une release', name: 'RELEASE')
                    ]),
                    disableConcurrentBuilds(),
                    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')),
                    pipelineTriggers([pollSCM('@hourly')])
            ])
        }
        stage('build') {
            sh('mvn clean package')
        }
        stage('release') {
            // 1. Git flow release
            // 2. Push nexus OSS // etc...
        }
    }
}