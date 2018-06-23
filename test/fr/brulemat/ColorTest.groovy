package fr.brulemat

import fr.brulemat.util.Color

class ColorTest extends GroovyTestCase {

    void testTextIsRed() {
        def expectedText = "\u001B[31mMy Test\u001B[0m"
        def returnedText = new Color().red("My Test")

        assert expectedText == returnedText
    }

    void testTextIsBlue() {
        def expectedText = "\u001B[34mMy Test\u001B[0m"
        def returnedText = new Color().blue("My Test")

        assert expectedText == returnedText
    }
}