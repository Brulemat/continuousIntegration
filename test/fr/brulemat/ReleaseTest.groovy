package fr.brulemat

import fr.brulemat.util.Maven
import fr.brulemat.util.Release
import org.codehaus.groovy.runtime.GStringImpl

class ReleaseTest extends GroovyTestCase {

    // We're stubbing out a pipeline script. This one pretends to be
    // a script that's running against the master branch.
    class MasterPipelineScript {
        def version = ""
        def requestedFilename = ""
        def requestedTool = ""

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

        def tool(name) {
            this.requestedTool = name
            return "mvn"
        }

        def sh(params) {
            if (params instanceof GStringImpl) {
                return ""
            }
            if (params.returnStdout == true && params.script == "grep --max-count=1 '<version>' ./pom.xml | awk -F '>' '{ print \$2 }' | awk -F '<' '{ print \$1 }'")
                return "0.1.0-SNAPSHOT"

            return ""
        }

        MasterPipelineScript() {
        }
    }

    void testExtractVersion() {
        def pipeline = new MasterPipelineScript()
        new Release(pipeline).maven(new Maven(pipeline, "maven"))
    }
}