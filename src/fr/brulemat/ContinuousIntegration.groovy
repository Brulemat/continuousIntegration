package fr.brulemat

class ContinuousIntegration {
    def config

    ContinuousIntegration(Map config) {
        this.config = config

        if (config['BRANCH'] == null) {
            config['BRANCH'] = 'master';
        }
        if (config['RELEASE'] == null) {
            config['RELEASE'] = false;
        }
    }

    String go() {

        node() {
            stage('clean') {
                deleteDir()
            }
        }
        return "ok"
    }
}
