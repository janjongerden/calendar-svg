package nl.janjongerden

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList


class Month(val year: Int, val month: Int) {

    private var secondLastWeekNumber: Int
    private val firstWeekNumber: Int
    private val lastWeekNumber: Int
    private val weekOfYear = WeekFields.ISO.weekOfWeekBasedYear()
    private val lastDay: LocalDate
    private val firstDay: LocalDate
    var firstMonday: LocalDate
    private val monthObject: YearMonth = YearMonth.of(year, month)

    init {
        firstDay = monthObject.atDay(1)
        lastDay = monthObject.atEndOfMonth()
        firstWeekNumber = firstDay.get(weekOfYear)
        lastWeekNumber = lastDay.get(weekOfYear)
        secondLastWeekNumber = lastDay.minusDays(7).get(weekOfYear)
        firstMonday = firstDay
        while (firstMonday.dayOfWeek != DayOfWeek.MONDAY) {
            firstMonday = firstMonday.minusDays(1)
        }
    }

    fun name(locale: Locale = Locale.US): String {
        return monthObject.month.getDisplayName(TextStyle.FULL, locale).uppercase()
    }

    fun weeksInMonth(): Int {
        // account for 1st of january being in last year's week in some cases
        if (lastWeekNumber == 1) {
            return secondLastWeekNumber - firstWeekNumber + 2
        }
        return if (firstWeekNumber > 50) lastWeekNumber + 1 else lastWeekNumber - firstWeekNumber + 1
    }

    fun weekNumbers(): List<Int> {
        val weeks = ArrayList<Int>()
        var weekNumber = firstWeekNumber
        weeks.add(weekNumber)
        if (weekNumber > 50) {
            weekNumber = 1
        } else {
            weekNumber += 1
        }
        while (weekNumber <= secondLastWeekNumber) {
            weeks.add(weekNumber)
            weekNumber++
        }
        // adding the 'last week' separately ensures correct week numbers in case December 31st is in week 1
        weeks.add(lastWeekNumber)
        return weeks
    }
}
