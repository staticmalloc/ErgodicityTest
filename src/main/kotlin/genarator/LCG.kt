package genarator

import kotlin.random.Random

class LCG : PseudoRandomGenerator(Generator.LCG) {
    private var generator: Random = Random(DEFAULT_SEED.hashCode())

    override fun setSeed(seed: String): PseudoRandomGenerator {
        generator = Random(seed.hashCode())
        return this
    }

    override fun generateMB(): ByteArray {
        return generator.nextBytes(MB)
    }
}