package nl.janjongerden

import svglib.RenderMode
import svglib.elements.Container
import svglib.elements.SVG
import java.io.FileWriter
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class Calendar(
    private val month: Month,
    private val displayLanguageLocale: Locale = Locale.forLanguageTag("en"),
    countryCode: String = "NL"
        ) {

    private var events: Events

    private val pageWidth = 720
    private val pageHeight = 510
    private val xMin = 45
    private val xMax = pageWidth - 22
    private val yMin = 30
    private val yMax = pageHeight - 10

    private var dayWidth: Int
    private val weekHeight: Int
    private val weekCount: Int = month.weeksInMonth()

    init {
        weekHeight = (yMax - yMin) / weekCount
        dayWidth = (xMax - xMin) / 7
        events = Events(month.year, countryCode, displayLanguageLocale)
    }

    fun generate(outputDirectory: String) {

        val svg = SVG.svg(true) {
            height = "$pageHeight"
            width = "$pageWidth"
            style {
                body = """
                 svg .black-stroke { stroke: black; stroke-width: 1; }
                 """.trimIndent()
            }
            monthLabel()
            weekNumbers()
            dayNumbers()
            dayNames()
            dayGrid()
        }

        FileWriter(getFileName(outputDirectory)).use {
            svg.render(it, RenderMode.FILE)
        }
    }

    private fun getFileName(outputDirectory: String): String {
        val paddedMonth = DecimalFormat("00").format(month.month)
        val dir = outputDirectory.dropLastWhile{it == '/'}

        return "${dir}/cal-${paddedMonth}-${month.year}.svg"
    }

    private fun Container.weekNumbers() {
        var yPos = yMin + weekHeight / 2
        for (weekNumber in month.weekNumbers()) {
            text {
                x = "${pageWidth - 14}"
                y = "$yPos"
                body = "$weekNumber"
                fontFamily = "Arial, sans-serif"
                fontSize = "10px"
                fontWeight = "bold"
                fill = "gray"
            }
            yPos += weekHeight
        }
    }

    private fun Container.dayNumbers() {
        var yPos = yMin + 18
        var currentDay = month.firstMonday
        for (week in 1..month.weeksInMonth()) {
            for (day in 0..6) {
                val xPos = xMin + 5 + dayWidth * day
                val dayColor = if (currentDay.monthValue == month.month) "black" else "lightgray"
                text {
                    x = "$xPos"
                    y = "$yPos"
                    body = "${currentDay.dayOfMonth}"
                    fontFamily = "Arial, sans-serif"
                    fontSize = "13px"
                    fontWeight = "bold"
                    fill = dayColor
                }
                val events = events.get(currentDay)
                if (events.isNotEmpty()) {
                    val eventsY = yPos + weekHeight - 24
                    val eventString = events.joinToString()
                    val eventColor = if (currentDay.monthValue == month.month) "red" else "gray"
                    text {
                        x = "$xPos"
                        y = "$eventsY"
                        body = eventString
                        fontFamily = "Arial, sans-serif"
                        fontSize = "8px"
                        fontWeight = "bold"
                        fill = eventColor
                    }
                }
                currentDay = currentDay.plusDays(1)
            }
            yPos += weekHeight
        }
    }

    private fun Container.dayNames() {
        var xPos = xMin + dayWidth / 2 - 10

        // names are returned with first element empty, second sunday
        val dayNames = DateFormatSymbols(displayLanguageLocale).weekdays
        val sorted = ArrayList<String>()
        for (i in 2..7) {
            sorted.add(dayNames[i])
        }
        sorted.add(dayNames[1])

        for (day in sorted) {
            val dayName = day.substring(0, 2).uppercase()
            text {
                x = "$xPos"
                y = "22"
                body = dayName
                fontFamily = "Arial, sans-serif"
                fontSize = "13px"
                fontWeight = "bold"
                fill = "black"
            }
            xPos += dayWidth
        }
    }

    private fun Container.monthLabel() {
        val labelX = 40
        val labelY = pageHeight - 30
        text {
            x = "$labelX"
            y = "$labelY"
            body = "${month.name(displayLanguageLocale)} ${month.year}"
            fontFamily = "Arial, sans-serif"
            fontSize = "36px"
            transform = "rotate(-90, $labelX, $labelY)"
            fontWeight = "bold"
            fill = "gray"
        }
    }

    private fun Container.dayGrid() {
        for (day in 0..< 7) {
            val x = (xMin + dayWidth * day).toString()
            verticalLine(x)
        }
        verticalLine("$xMax")

        for (week in 0..< weekCount) {
            val y = (yMin + weekHeight * week).toString()
            horizontalLine(y)
        }
        horizontalLine("$yMax")
    }

    private fun Container.verticalLine(x: String) {
        line {
            x1 = x
            y1 = "$yMin"
            x2 = x
            y2 = "$yMax"
            cssClass = "black-stroke"
        }
    }

    private fun Container.horizontalLine(y: String) {
        line {
            x1 = "$xMin"
            y1 = y
            x2 = "$xMax"
            y2 = y
            cssClass = "black-stroke"
        }
    }
}
