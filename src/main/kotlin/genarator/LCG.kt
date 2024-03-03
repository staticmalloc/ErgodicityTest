package genarator

import kotlin.random.Random

class LCG : PseudoRandomGenerator(Generator.LCG) {
    private val generator = Random(seed.hashCode())

    override fun generateMB(): ByteArray {
        return generator.nextBytes(MB)
    }
}