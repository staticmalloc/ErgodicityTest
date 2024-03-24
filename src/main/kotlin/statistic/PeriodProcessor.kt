package statistic

interface PeriodProcessor {
    fun processNewPeriod(period: Long)

    fun stop()
}