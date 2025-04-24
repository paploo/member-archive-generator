package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate
import java.time.Year

data class VideoGroup(
    val year: Year,
    val rows: List<Row>
) {

    init {
        rows.forEach {
            check(it.date.year == year.value) {
                "Expected rows to have year ${year.value}, but got ${it.date.year}: $it"
            }
        }
    }

    data class Row(
        val date: LocalDate,
        val saiVideo: Video?,
        val closingVideo: Video?
    ) {

        init {
            assert(saiVideo != null || closingVideo != null) {
                "Both columns cannot be empty."
            }

            assert(saiVideo?.let { it.serviceDate == date } ?: true ) {
                "Main column video's date must $date but got ${saiVideo?.serviceDate}."
            }

            assert(closingVideo?.let { it.serviceDate == date } ?: true ) {
                "Closing column video's date must $date but got ${closingVideo?.serviceDate}."
            }
        }

    }

}