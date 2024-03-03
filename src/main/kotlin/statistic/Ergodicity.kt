package statistic

import java.math.BigDecimal
import java.math.BigInteger
import java.util.LinkedList
import kotlin.math.max
import kotlin.math.min

class Ergodicity {

    /**
     * Subsequence of origin sequence that participates in the calculation of the current period
     */
    private val sequenceOfCurrentPeriod: LinkedList<UByte> = LinkedList<UByte>()

    /**
     * An alphabet-sized array for counting the number of occurrences of each number in the current subsequence
     */
    private val countsOfNumbers: Array<Int> = Array(256) { 0 }

    private var period: Long = 0L
    var minPeriod: Long = Long.MAX_VALUE
        private set
    var maxPeriod: Long = Long.MIN_VALUE
        private set


    /**
     * Buffer for found periods
     */
    private val foundPeriods = mutableListOf<Long>()

    /**
     * In order to economize memory usage, we save avg period instead of list of all periods
     */
    private var avgPeriod: BigDecimal = 0.0.toBigDecimal()
    private var countInAvg: BigInteger = 0.toBigInteger()
    private fun recalculateAvg(){
        if (foundPeriods.size == 1024){
            val countBD = countInAvg.toBigDecimal()
            val newCountBD = foundPeriods.size.toBigDecimal()
            avgPeriod =
                (avgPeriod * countBD + newCountBD * foundPeriods.average().toBigDecimal()) / (countBD + newCountBD)

            countInAvg += foundPeriods.size.toBigInteger()
            foundPeriods.clear()
        }
    }


    /**
     * Checks that all values of alphabet is present in current subsequence
     */
    private fun checkAllValuesIsPresent(): Boolean {
        return countsOfNumbers.all { count -> count > 0 }
    }

    private fun savePeriod() {
        minPeriod = min(minPeriod, period)
        maxPeriod = max(maxPeriod, period)
        foundPeriods.add(period)
        recalculateAvg()
    }

    /**
     * Drops the oldest value in sequenceOfCurrentPeriod, decrement count of this value and decrement period
     * @return true if there is still another appearance of first value in sequenceOfCurrentPeriod.
     * false, if it was last appearance of first value sequenceOfCurrentPeriod
     */
    private fun dropOldest(): Boolean {
        val first = sequenceOfCurrentPeriod.removeFirst().toInt()
        val newCountsOfFirstValue = countsOfNumbers[first] - 1
        countsOfNumbers[first] = newCountsOfFirstValue
        period--
        return newCountsOfFirstValue != 0
    }

    /**
     * Checks each continuous subsequence, if it has all values of alphabet or not
     */
    private fun checkSubSequences() {
        if (sequenceOfCurrentPeriod.size < 256)
            return

        while (dropOldest() && sequenceOfCurrentPeriod.size > 256){
            savePeriod()
        }
    }
    fun getAvgPeriod(): BigDecimal = avgPeriod

    fun append(byte: UByte) {
        sequenceOfCurrentPeriod.add(byte)
        countsOfNumbers[byte.toInt()]++
        period++
        if (checkAllValuesIsPresent()) {
            savePeriod()
            checkSubSequences()
        }
    }

}