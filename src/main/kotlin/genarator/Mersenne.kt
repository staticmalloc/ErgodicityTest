package genarator

import org.apache.commons.math3.random.MersenneTwister

class Mersenne: PseudoRandomGenerator(Generator.MERSENNE) {
    private val generator = MersenneTwister(DEFAULT_SEED.hashCode())

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