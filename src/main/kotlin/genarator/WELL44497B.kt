package genarator

import org.apache.commons.math3.random.Well44497b


class WELL44497B: PseudoRandomGenerator(Generator.WELL44497B) {
    private val generator = Well44497b()

    init {
        generator.setSeed(DEFAULT_SEED.hashCode())
    }

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