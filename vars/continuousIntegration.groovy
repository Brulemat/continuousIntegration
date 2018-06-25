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
            ic.config(context)
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
        if (ic.isAskDeploymentIntegration()) {
            stage('integration') {
                ic.deploiementIntegration()
                ic.testIntegration()
            }
        }
        if (ic.isAskRelease()) {
            stage('release') {
                ic.release()
            }
        }
        if (ic.isAskDeploymentProduction()) {
            stage('deployment prod') {

                ic.deploiementProduction()
            }
        }
    }
}