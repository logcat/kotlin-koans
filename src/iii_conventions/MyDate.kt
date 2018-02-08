package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(date2: MyDate): Int {
        if (year < date2.year) {
            return -1
        } else if (year > date2.year) {
            return 1
        } else {

            if (month < date2.month) {
                return -1
            } else if (month > date2.month) {
                return 1
            } else {

                if (dayOfMonth < date2.dayOfMonth) {
                    return -1
                } else if (dayOfMonth > date2.dayOfMonth) {
                    return 1
                } else {
                    return 0
                }

            }

        }
    }
}

operator fun MyDate.plus(interval: TimeInterval): MyDate {
    return this.addTimeIntervals(interval, 1)
}

operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(interval.ti, interval.n)
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(times: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, times)
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(date: MyDate): Boolean {
        return start <= date && date <= endInclusive
    }
}

operator fun DateRange.iterator(): Iterator<MyDate> {
    val start = this.start
    val endInclusive = this.endInclusive
    return object: Iterator<MyDate> {

        var state: MyDate? = start

        override operator fun next(): MyDate {
            val result = state!!
            state = state!!.nextDay()
            return result
        }

        override operator fun hasNext(): Boolean {
            return state!! <= endInclusive
        }
    }
}
