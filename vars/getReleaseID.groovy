import fr.brulemat.ReleaseIDGenerator

def call() {
    def pom = readFile("pom.xml")
    def generator = new ReleaseIDGenerator(this)
    return generator.generate(pom)
}

def getProjectVersion() {
    def file = readFile('pom.xml')
    def project = new XmlSlurper().parseText(file)
    return project.version.text()
}