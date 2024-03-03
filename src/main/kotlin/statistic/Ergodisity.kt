package statistic

class Ergodicity {
    private val set = mutableSetOf<UByte>()
    private var period: Long = 0L

    private val foundPeriods = mutableListOf<Long>()
    fun getPeriods(): List<Long> = foundPeriods

    fun append(byte: UByte) {
        set.add(byte)
        period++
        if (set.size == 256) {
            refresh()
        }
    }

    private fun refresh() {
        foundPeriods.add(period)
        set.clear()
        period = 0L
    }

}