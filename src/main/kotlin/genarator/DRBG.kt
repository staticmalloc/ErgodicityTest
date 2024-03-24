package genarator

import java.security.SecureRandom

class DRBG: PseudoRandomGenerator(Generator.DRBG) {
    private val generator = SecureRandom.getInstance("DRBG")
    override fun setSeed(seed: String): PseudoRandomGenerator {
        generator.setSeed(seed.hashCode().toLong())
        return this
    }
    override fun generateMB(): ByteArray {
        val array = ByteArray(MB)
        generator.nextBytes(array)
        return array
    }

}