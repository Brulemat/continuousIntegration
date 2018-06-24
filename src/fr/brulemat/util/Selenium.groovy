package fr.brulemat.util;

class Selenium implements Serializable {
    def script

    Selenium(script) {
        this.script = script
    }

    def go(version) {
        script.sh "echo ${version}"
    }
}