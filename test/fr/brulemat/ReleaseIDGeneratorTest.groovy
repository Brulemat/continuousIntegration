package fr.brulemat

class ReleaseIDGeneratorTest extends GroovyTestCase {

    // We're stubbing out a pipeline script. This one pretends to be
    // a script that's running against the master branch.
    class MasterPipelineScript {
        def version = ""
        def requestedFilename = ""

        def env = [
                'BUILD_ID'  : 15,
                'GIT_COMMIT': 'a3bb4b7f9bf5db1c436b96a970c04d553feed1c5',
                'WORKSPACE' : '.'
        ]

        @SuppressWarnings("GroovyUnusedDeclaration")
        def readFile(file) {
            this.requestedFilename = file
            return "<project ><version>${this.version}</version></project>"
        }

        MasterPipelineScript(version) {
            this.version = version
        }
    }

    //
    // Tests
    //

    void testMasterPipelineHappyPath() {
        def version = '1.2.3'
        def pipeline = new MasterPipelineScript(version)
        def shortSha = pipeline.env.GIT_COMMIT.take(7)
        def expectedReleaseID = "${version}+b${pipeline.env.BUILD_ID}.${shortSha}"

        def returnedReleaseID = new ReleaseIDGenerator(pipeline).generate("<project ><version>${version}</version></project>")

//        assert '.\\pom.xml' == pipeline.requestedFilename
        assert expectedReleaseID == returnedReleaseID
    }
}