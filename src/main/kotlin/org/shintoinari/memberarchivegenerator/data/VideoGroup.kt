package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate
import java.time.Year

@ConsistentCopyVisibility
data class VideoGroup(
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

    data class Row(
        val date: LocalDate,
        val saiColumn: Video?,
        val closingColumn: Video?
    ) {

        init {
            assert(saiColumn != null || closingColumn != null) {
                "Both columns cannot be empty."
            }

            assert(saiColumn?.let { it.serviceDate == date } ?: true ) {
                "Main column video's date must $date but got ${saiColumn?.serviceDate}."
            }

            assert(closingColumn?.let { it.serviceDate == date } ?: true ) {
                "Closing column video's date must $date but got ${closingColumn?.serviceDate}."
            }
        }

    }

}