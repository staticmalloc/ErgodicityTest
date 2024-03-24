package genarator

import java.security.SecureRandom

class SHA1: PseudoRandomGenerator(Generator.SHA1) {

    private val generator = SecureRandom.getInstance("SHA1PRNG")

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