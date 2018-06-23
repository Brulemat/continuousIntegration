import fr.brulemat.ReleaseIDGenerator

def call() {
    def generator = new ReleaseIDGenerator(this)
    return generator.generate()
}