package fr.brulemat.util;

class Maven implements Serializable {
    def steps
    def name

    Maven(steps, name) {
        this.steps = steps
        this.name = name
    }

    def mvn(args) {
        def cmd = steps.tool this.name
        steps.sh "${cmd}/bin/mvn -B ${args}"
    }
}