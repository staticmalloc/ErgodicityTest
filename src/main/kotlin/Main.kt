import genarator.*
import kotlinx.coroutines.*
import statistic.Ergodicity
import java.io.File
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis


fun main() {

    // Generates pseudo random sequences within each Generator and writes them to files
    // By default it will generate 4GB files for by each Generator
    PseudoRandomGeneratorFactory.buildGenerators(DEFAULT_SEED, DEFAULT_SIZE_MB)
        .forEach { gen ->
            gen.writeToFile()
        }

    val pool = Executors.newFixedThreadPool(Generator.entries.size)
    val dispatcher = pool.asCoroutineDispatcher()
    val coroutines = runBlocking {
        Generator.entries
            .map { type ->
               async(dispatcher) {
                    calculateErgodicity(type)
                }
            }
    }

    runBlocking {
        coroutines.awaitAll()
    }.forEach {
        val erg = it.second
        println()
        println("*********************************************")
        println("Results for <${it.first.name}> generator:")
        println("AVG erg. Period: ${erg.getAvgPeriod()} bytes.")
        println("MIN erg. Period: ${erg.minPeriod} bytes.")
        println("MAX erg. Period: ${erg.maxPeriod} bytes.")
        println("*********************************************")
        println()
    }

    pool.shutdown()
}

suspend fun calculateErgodicity(type: Generator): Pair<Generator, Ergodicity> {
    println("${type.name} calculations started...")
    val file = File(type.name + SUFFIX)
    val iStream = file.inputStream()
    val erg = Ergodicity()

    measureTimeMillis {
        var buffer = iStream.readNBytes(MB)
        while (buffer.isNotEmpty()) {
            buffer.forEach { byte ->
                erg.append(byte.toUByte())
            }
            buffer = iStream.readNBytes(MB)
        }
    }.let { println("${type.name} spent: ${it / 1000} s.") }


    iStream.close()
    return type to erg
}
