package fr.brulemat

import fr.brulemat.util.Maven
import fr.brulemat.util.Release
import fr.brulemat.util.Selenium

class ContinuousIntegration implements Serializable {
    def script
    Configuration config
    Maven maven

    ContinuousIntegration(script) {
        this.script = script
        this.config = new Configuration()
    }

    void config(conf) {
        this.config.release = (this.script.env.RELEASE == 'true')
        this.config.branch = this.script.env.BRANCH_NAME
        this.config.tool = conf['tool']

        maven = new Maven(this.script, this.config.tool)
    }

    String build() {
        maven.mvn("clean package")

        return "ok"
    }

    String testIntegration() {
        new Selenium(this.script).go()

        return "ok"
    }

    String release() {
        new Release(this.script).maven(maven)

        return "ok"
    }
}
