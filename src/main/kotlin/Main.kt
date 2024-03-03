import genarator.Generator
import genarator.MB
import genarator.SUFFIX
import kotlinx.coroutines.*
import statistic.Ergodicity
import java.io.File
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis


fun main() {
//    PseudoRandomGeneratorFactory.buildGenerators()
//        .forEach { gen ->
//            gen.writeToFile()
//        }

    val pool = Executors.newFixedThreadPool(Generator.entries.size)
//    val futures = Generator.entries.map {
//        pool.submit(Callable { calculateErgodicity(it) })
//    }
//    while (futures.any { !it.isDone })
//    futures.forEach { it.get().let {
//        println("AVG Period for ${it.first.name}: ${it.second} bytes.")
//    } }
//    pool.shutdown()

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
        println("AVG Period for ${it.first.name}: ${it.second} bytes.")
    }

    pool.shutdown()
}


suspend fun calculateErgodicity(type: Generator): Pair<Generator, Long> {
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


    val avg = erg.getPeriods().average().toLong()
    return type to avg
}
