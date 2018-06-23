#!groovy
//noinspection GroovyUnusedAssignment
@Library('continuousIntegration') _


node {
    stage ('Initialize') {
        env.PATH = "${tool 'maven35'}/bin:${env.PATH}"
    }
    stage ('Build') {
        echoBlue('Start')
        echoBlue(env.GIT_COMMIT)
        echo "${env.WORKSPACE}\\pom.xml"
        if (fileExists(env.WORKSPACE+"\\"+"pom.xml")) {
//            echo readFile("${env.WORKSPACE}\\pom.xml")
            //echo readFile("${env.WORKSPACE}\\pom.xml") =~ '<version>(.+)</version>'
            echoRed(getReleaseID())
        }
        continuousIntegration {
            gitUrl = 'https://github.com/Brulemat/continuousIntegration.git'
            credentialsId = 'brulemat-github'
        }

    }
}

//def libraryFromLocalRepo() {
    // Workaround for loading the current repo as shared build lib.
    // Checks out to workspace local folder named like the identifier.
    // We have to pass an identifier with version (which is ignored). Otherwise the build fails.
//    library(identifier: 'continuousIntegration@master', retriever: legacySCM(scm))
//}