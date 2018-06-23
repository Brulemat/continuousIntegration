#!groovy
@Library('continuousIntegration')
import fr.brulemat.util.Color

node {
    stage ('Initialize') {
//        env.PATH = "${tool 'maven35'}/bin:${env.PATH}"
        checkout scm

    }
    stage ('Build') {
        echo Color.red('Texte Rouge')
        echoBlue('Texte bleu')
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