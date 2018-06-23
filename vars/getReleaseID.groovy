import fr.brulemat.ReleaseIDGenerator

import static java.io.File.separator

def call() {
    def pom = readFile(env.WORKSPACE + separator + "pom.xml")
    def generator = new ReleaseIDGenerator(this)
    return generator.generate(pom)
}