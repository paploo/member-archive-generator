package org.shintoinari.memberarchivegenerator.util

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

/**
 * Interface for string formatters of temporal objects.
 */
interface ChronoFormatter {

    /**
     * Formats the given temporal object into a string.
     */
    fun format(temporal: TemporalAccessor): String

    companion object {

        /**
         * Constructs a ChronoFormatter from a DateTimeFormatter.
         */
        operator fun invoke(formatter: DateTimeFormatter) =
            object : ChronoFormatter {
                override fun format(temporal: TemporalAccessor) = formatter.format(temporal)
            }

        /**
         * Constructs a ChronoFormatter from a lambda function.
         */
        operator fun invoke(formatter: (TemporalAccessor) -> String) =
            object : ChronoFormatter {
                override fun format(temporal: TemporalAccessor) = formatter(temporal)
            }

        /**
         * Constructs a ChronoFormatter from a DateTimeFormatter and an after-formatting lambda function.
         */
        operator fun invoke(formatter: DateTimeFormatter, transform: (String) -> String) =
            invoke { temporal -> formatter.format(temporal).let(transform) }

        /**
         * Constructs a ChronoFormatter for a given formatting pattern.
         */
        fun ofPattern(pattern: String) =
            invoke(DateTimeFormatter.ofPattern(pattern))

        /**
         * Constructs a ChronoFormatter for a given formatting pattern and an after-formatting lambda function.
         */
        fun ofPattern(pattern: String, transform: (String) -> String) =
            invoke(DateTimeFormatter.ofPattern(pattern), transform)
    }

}

/**
 * Formats the given temporal object using the provided ChronoFormatter.
 */
fun TemporalAccessor.format(formatter: ChronoFormatter) = formatter.format(this)

/**
 * Constructs a ChronoFormatter for North American date formatting.
 */
val ChronoFormatter.Companion.en: ChronoFormatter get() =
    ChronoFormatter.ofPattern("LLLL d, yyyy")

/**
 * Constructs a ChronoFormatter for Japanese date formatting with full-width numeric characters.
 */
val ChronoFormatter.Companion.jp: ChronoFormatter get() =
    ChronoFormatter.ofPattern("yyyy年M月d日") {
        it.toFullWidthNumeric()
    }