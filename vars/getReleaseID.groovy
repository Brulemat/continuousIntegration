import fr.brulemat.ReleaseIDGenerator

import static java.io.File.separator

def call() {
    def generator = new ReleaseIDGenerator(this)
    return generator.generate(readFile(env.WORKSPACE + separator + "pom.xml"))
}