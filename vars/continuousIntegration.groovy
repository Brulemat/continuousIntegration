#!/usr/bin/env groovy
import fr.brulemat.ContinuousIntegration

def call(Closure context) {
    // création d'un map
    def config = [:]
    // affecter la map en tant que delegate de la closure
    context.resolveStrategy = Closure.DELEGATE_FIRST
    context.delegate = config
    // appeler la closure
    context()

    ContinuousIntegration ic = new ContinuousIntegration(this)
    // commencer son workflow
    node {
        stage('init') {
            ic.config(config)
        }
        stage('configure') {
            //noinspection GroovyAssignabilityCheck
            properties([
                    parameters([
                            booleanParam(defaultValue: false, description: 'Faire une release', name: 'RELEASE')
                    ]),
                    disableConcurrentBuilds(),
                    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')),
                    pipelineTriggers([pollSCM('@hourly')])
            ])
        }
        stage('build') {
            ic.build()
        }
        stage('release') {

            // 1. Git flow release
            // 2. Push nexus OSS // etc...
        }
    }
}