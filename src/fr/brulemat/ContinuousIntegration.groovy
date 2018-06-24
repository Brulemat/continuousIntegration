package fr.brulemat

import fr.brulemat.util.Maven

class ContinuousIntegration implements Serializable {
    def script
    Configuration config

    ContinuousIntegration(script) {
        this.script = script
        this.config = new Configuration()
    }

    void config(conf) {
        this.config.release = (this.script.env.RELEASE == 'true')
        this.config.branch = this.script.env.BRANCH_NAME
        this.config.tool = conf['tool']
    }

    String build() {
        new Maven(script, this.config.tool).mvn("clean package")

        return "ok"
    }
}
