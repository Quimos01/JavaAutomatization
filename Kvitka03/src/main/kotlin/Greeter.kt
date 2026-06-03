/**
 * Test class 2
 */
class Greeter(private val name: String) {

    // Returns simple greetings
    fun greet(): String {
        return "Привіт, $name!"
    }

    // Returns greeting in caps
    fun greetLoud(): String {
        return greet().uppercase()
    }
}
