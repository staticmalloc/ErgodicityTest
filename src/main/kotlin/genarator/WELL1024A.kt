package genarator

import org.apache.commons.math3.random.Well1024a

class WELL1024A: PseudoRandomGenerator(Generator.WELL1024A) {
    private val generator = Well1024a()

    init {
        generator.setSeed(DEFAULT_SEED.hashCode())
    }

    override fun generateMB(): ByteArray {
        val array = ByteArray(MB)
        generator.nextBytes(array)
        return array
    }

}