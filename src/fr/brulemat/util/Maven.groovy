package fr.brulemat.util;

class Maven implements Serializable {
    def script
    def name

    Maven(script, name) {
        this.script = script
        this.name = name
    }

    def mvn(args) {
        def cmd = script.tool this.name
        script.sh "${cmd}/bin/mvn -B ${args}"
    }

    String extractVersion() {
        def version = script.sh(returnStdout: true, script: "grep --max-count=1 '<version>' " + script.env.WORKSPACE + "/pom.xml | awk -F '>' '{ print \$2 }' | awk -F '<' '{ print \$1 }'").trim()
        return version
    }
}