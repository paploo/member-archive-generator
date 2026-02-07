package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate
import java.time.Year

/**
 * Safely groups videos by year.
 *
 * On creation, it throws an exception if any row cannot legally exist in the group.
 */
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

    /**
     * Safely groups videos for a single row in the overall video group.
     *
     * On creation, it throws an exception if any of the videos cannot legally exist in the row.
     */
    data class Row(
        val date: LocalDate,
        val saiVideo: Video?,
        val closingVideo: Video?
    ) {

        init {
            check(saiVideo != null || closingVideo != null) {
                "Both columns cannot be empty."
            }

            check(saiVideo?.let { it.serviceDate == date } ?: true ) {
                "Main column video's date must be $date but got ${saiVideo?.serviceDate}."
            }

            check(closingVideo?.let { it.serviceDate == date } ?: true ) {
                "Closing column video's date must be $date but got ${closingVideo?.serviceDate}."
            }
        }

    }

}