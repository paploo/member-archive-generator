package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate
import java.time.Year

@ConsistentCopyVisibility
data class VideoGroup private constructor (
    val year: Year,
    val rows: List<Row>
) {

    init {
        rows.forEach {
            assert(it.date.year == year.value) {
                "Expected rows to have year ${year.value}, but got ${it.date.year}: $it"
            }
        }
    }

    companion object {
        operator fun invoke(year: Year, rows: List<Row>) : VideoGroup = VideoGroup(year, rows.sortedBy { it.date })
    }

    data class Row(
        val date: LocalDate,
        val mainColumn: Video?,
        val closingColumn: Video?
    ) {

        init {
            assert(mainColumn != null || closingColumn != null) {
                "Both columns cannot be empty."
            }

            assert(mainColumn?.let { it.serviceDate == date } ?: true ) {
                "Main column video's date must $date but got ${mainColumn?.serviceDate}."
            }

            assert(closingColumn?.let { it.serviceDate == date } ?: true ) {
                "Closing column video's date must $date but got ${closingColumn?.serviceDate}."
            }
        }

    }

}