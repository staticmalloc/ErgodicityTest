package genarator

import org.apache.commons.math3.random.ISAACRandom

class ISAAC: PseudoRandomGenerator(Generator.ISAAC) {
    private val generator = ISAACRandom()

    init {
        generator.setSeed(DEFAULT_SEED.hashCode())
    }

    override fun generateMB(): ByteArray {
        val array = ByteArray(MB)
        generator.nextBytes(array)
        return array
    }

}