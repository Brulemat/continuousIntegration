package fr.brulemat

import fr.brulemat.util.HotFix
import fr.brulemat.util.Maven
import org.codehaus.groovy.runtime.GStringImpl

class HotFixTest extends GroovyTestCase {

    // We're stubbing out a pipeline script. This one pretends to be
    // a script that's running against the master branch.
    @SuppressWarnings("unused")
    class MasterPipelineScript {
        def version = ""
        def requestedFilename = ""
        def requestedTool = ""

        def env = [
                'BUILD_ID'  : 15,
                'GIT_COMMIT': 'a3bb4b7f9bf5db1c436b96a970c04d553feed1c5',
                'WORKSPACE' : '.'
        ]

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
            if (params.returnStdout == true && params.script == "grep --max-count=1 '<version>' ./pom.xml | awk -F '>' '{ print \$2 }' | awk -F '<' '{ print \$1 }'") {
                return "0.1.1-SNAPSHOT"
            }

            return ""
        }

        MasterPipelineScript() {
        }
    }

    void testExtractVersion() {
        def pipeline = new MasterPipelineScript()
        def hotFix = new HotFix(pipeline)
        hotFix.maven(new Maven(pipeline, "maven"))
        assert "0.1.1" == hotFix.hotFixVersion
        assert "0.1.2-SNAPSHOT" == hotFix.nextVersion
    }
}