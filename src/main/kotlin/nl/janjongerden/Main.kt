package nl.janjongerden

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger {}

class Main : CliktCommand() {
    private val year: Int by option().int().default(LocalDate.now().year).help("The year of the calendar. Default is the current year.")
    private val month: Int? by option().int().help("The month of the calendar. When left empty, calendars for every month of the year are generated.")
    private val language: String by option().default("en").help("The display language to use, default is 'en'.")
    private val country: String by option().default("US").help("The country for which to show public holidays, default is 'US'.")
    private val output: String by option().default("/tmp").help("The output directory where the images will be saved.")

    override fun run() {
        val months = month?.let { listOf(it) } ?: (1..12).toList()

        for (m in months) {
            val currentMonth = Month(year, m)
            logger.info { "Generating calendar for ${currentMonth.name()} $year in directory '$output'" }
            Calendar(currentMonth, Locale.forLanguageTag(language), country).generate(output)
        }
    }
}

fun main(args: Array<String>) = Main().main(args)
