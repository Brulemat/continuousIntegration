#!groovy
//noinspection GroovyUnusedAssignment
@Library('continuousIntegration@develop') _

continuousIntegration ({
    [tool='maven35']
})

//def libraryFromLocalRepo() {
    // Workaround for loading the current repo as shared build lib.
    // Checks out to workspace local folder named like the identifier.
    // We have to pass an identifier with version (which is ignored). Otherwise the build fails.
//    library(identifier: 'continuousIntegration@master', retriever: legacySCM(scm))
//}