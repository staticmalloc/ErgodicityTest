package genarator

import org.apache.commons.math3.random.ISAACRandom

class ISAAC: PseudoRandomGenerator(Generator.ISAAC) {
    private val generator = ISAACRandom()

    override fun setSeed(seed: String): PseudoRandomGenerator {
        generator.setSeed(seed.hashCode())
        return this
    }

    override fun generateMB(): ByteArray {
        val array = ByteArray(MB)
        generator.nextBytes(array)
        return array
    }

}