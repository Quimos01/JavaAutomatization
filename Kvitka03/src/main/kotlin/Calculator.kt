/**
 * Test class 1
 * Simple calculator
 */
class Calculator {

    // Add
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    // Substract
    fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    // Multiply
    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    // Divede
    fun divide(a: Int, b: Int): Double {
        if (b == 0) {
            throw IllegalArgumentException()
        }
        return a.toDouble() / b
    }
}
