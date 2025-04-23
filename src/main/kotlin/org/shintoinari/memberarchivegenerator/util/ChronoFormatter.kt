package org.shintoinari.memberarchivegenerator.util

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

interface ChronoFormatter {
    fun format(temporal: TemporalAccessor): String

    companion object {
        operator fun invoke(formatter: DateTimeFormatter) =
            object : ChronoFormatter {
                override fun format(temporal: TemporalAccessor) = formatter.format(temporal)
            }

        operator fun invoke(formatter: (TemporalAccessor) -> String) =
            object : ChronoFormatter {
                override fun format(temporal: TemporalAccessor) = formatter(temporal)
            }

        operator fun invoke(formatter: DateTimeFormatter, transform: (String) -> String) =
            invoke { temporal -> formatter.format(temporal).let(transform) }

        fun ofPattern(pattern: String) =
            invoke(DateTimeFormatter.ofPattern(pattern))

        fun ofPattern(pattern: String, transform: (String) -> String) =
            invoke(DateTimeFormatter.ofPattern(pattern), transform)
    }

}

fun TemporalAccessor.format(formatter: ChronoFormatter) = formatter.format(this)

val ChronoFormatter.Companion.en: ChronoFormatter get() =
    ChronoFormatter.ofPattern("LLLL d, yyyy")

val ChronoFormatter.Companion.jp: ChronoFormatter get() =
    ChronoFormatter.ofPattern("yyyy年M月d日") {
        it.toFullWidthNumeric()
    }