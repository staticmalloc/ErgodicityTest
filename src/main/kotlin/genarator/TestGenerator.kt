package genarator

/**
 * Implementation of testing generator:
 * it generates bytes by repeat values from 0 to 255
 */
class TestGenerator: PseudoRandomGenerator(Generator.TEST) {

    override fun setSeed(seed: String) = this
    override fun generateMB(): ByteArray {
        return ByteArray(MB) { ind -> (ind % 256).toByte() }
    }
}