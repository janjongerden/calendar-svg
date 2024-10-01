package nl.janjongerden

import de.focus_shift.jollyday.core.HolidayCalendar
import de.focus_shift.jollyday.core.HolidayCalendar.UNITED_STATES
import de.focus_shift.jollyday.core.HolidayManager
import de.focus_shift.jollyday.core.ManagerParameters
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

class Events(private val year: Int, countryCode: String, private val locale: Locale) {

    private val events = HashMap<String, MutableSet<String>>()

    init {
        val holidayManager: HolidayManager = HolidayManager.getInstance(ManagerParameters.create(getCountry(countryCode)))
        val holidays = holidayManager.getHolidays(year)
        for (holiday in holidays) {
            val translatedHolidayName = holiday.getDescription(locale)
            val eventNames = events.getOrPut(keyFromDate(holiday.date)) { HashSet() }
            eventNames.add(translatedHolidayName)
        }
        addMothersDay()
        addFathersDay()
    }

    private fun getCountry(countryCode: String): HolidayCalendar {
        for (country in HolidayCalendar.entries) {
            if (country.id == countryCode) {
                return country
            }
        }
        return UNITED_STATES
    }

    private fun keyFromDate(day: LocalDate): String {
        return "${day.dayOfMonth}-${day.monthValue}"
    }

    private fun addMothersDay() {
        // in most countries celebrated on the 2nd sunday in May
        val day = getNthSundayOfMonth(2, 5)
        val eventNames = events.getOrPut(keyFromDate(day)) { HashSet() }

        if (locale == Locale.forLanguageTag("nl")) {
            eventNames.add("Moederdag")
        } else {
            eventNames.add("Mother's Day")
        }
    }

    private fun addFathersDay() {
        // in most countries celebrated on the 3rd sunday in June
        val day = getNthSundayOfMonth(3, 6)
        val eventNames = events.getOrPut(keyFromDate(day)) { HashSet() }

        if (locale == Locale.forLanguageTag("nl")) {
            eventNames.add("Vaderdag")
        } else {
            eventNames.add("Father's Day")
        }
    }

    private fun getNthSundayOfMonth(n: Int, month: Int): LocalDate {
        var day = LocalDate.of(year, month, 1)
        var sundays = 0
        do {
            if (day.dayOfWeek == DayOfWeek.SUNDAY) sundays++
            if (sundays == n) return day
            day = day.plusDays(1)
        } while (true)
    }

    fun get(day: LocalDate): Set<String> {
        return events[keyFromDate(day)].orEmpty()
    }
}
