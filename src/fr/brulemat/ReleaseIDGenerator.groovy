package fr.brulemat

class ReleaseIDGenerator {
    /**
     *
     */
    def script

    ReleaseIDGenerator(script) {
        this.script = script
    }

    def generate(pom) {
        def version = version(pom)
        def buildID = script.env.BUILD_ID
        def shortSha = script.env.GIT_COMMIT.take(7)

        return "${version}+b${buildID}.${shortSha}"
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    static
    def version(pom) {
        def matcher = pom =~ '<version>(.+)</version>'
        def version = matcher ? matcher[0][1] : null
        return version
    }
}
