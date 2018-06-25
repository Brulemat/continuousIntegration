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
        this.config.release = (this.script.env.RELEASE == "true")
        this.config.branch = this.script.env.BRANCH_NAME
        script.sh "echo ${conf}"
        conf.inject(this.config) { Configuration attributes, attr ->
            def object = attr.call()
            if (object['tool'] instanceof String) {
                attributes.setTool(object['tool'] as String)
            }
            if (object['deploymentIntegration'] instanceof Boolean) {
                attributes.setDeploymentIntegration(object['deploymentIntegration'] as Boolean)
            }
            if (object['deploymentProd'] instanceof Boolean) {
                attributes.setDeploymentProd(object['deploymentProd'] as Boolean)
            }
            return attributes
        }

        maven = new Maven(this.script, this.config.tool)
    }

    String build() {
        maven.mvn("clean package")

        return "ok"
    }

    boolean isAskDeploymentIntegration() {
        return config.deploymentIntegration
    }

    boolean isAskRelease() {
        return config.release
    }

    boolean isAskDeploymentProduction() {
        return config.deploymentProd
    }

    String deploiementIntegration() {
        new Selenium(this.script).go()

        return "ok"
    }

    String testIntegration() {

        new Selenium(this.script).go()

        return "ok"
    }

    String release() {
        Release release = new Release(this.script)

        release.maven(maven)

        return "ok"
    }

    static String deploiementProduction() {
        return "ok"
    }
}
