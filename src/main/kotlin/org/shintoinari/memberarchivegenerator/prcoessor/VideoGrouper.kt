package org.shintoinari.memberarchivegenerator.prcoessor

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.util.zipFull
import java.time.LocalDate
import java.time.Year

interface VideoGrouper : (List<Video>) -> List<VideoGroup>

class DefaultVideoGrouper : VideoGrouper {

    override fun invoke(videos: List<Video>): List<VideoGroup> =
        videos.filter {
            it.isActive && it.category != Video.Category.UNCATEGORIZED
        }.let {
            toGroups(it)
        }

    private fun toGroups(videos: List<Video>): List<VideoGroup> =
        videos.groupBy { Year.of(it.serviceDate.year) }.map { (year, videosInYear) ->
            toGroup(year, videosInYear)
        }


    private fun toGroup(year: Year, videosInYear: List<Video>): VideoGroup =
        videosInYear.groupBy { it.serviceDate }.flatMap { (serviceDate, videosOnDate) ->
            toRows(serviceDate, videosOnDate)
        }.let { rows ->
            VideoGroup(year, rows)
        }

    /**
     * This is the meat of the class, grouping together the videos on a date into rows.
     *
     * This produces a list of well-ordered rows, such that:
     * 1. The first is a row(s) are the tsukinami_sai and tsukinami_closing videos, if any.
     * 2. Pairing-up of remaining other_sai with other_closing, until there is nothing left.
     * This does have corner cases, but if we want to do better we hae to change the input file format.
     */
    private fun toRows(date: LocalDate, vidoesOnDate: List<Video>): List<VideoGroup.Row> {
        // Filter videos by category
        val videosByCategory = vidoesOnDate.groupBy { it.category }

        val tsukinamiSai = vidoesOnDate.filter { it.category == Video.Category.TSUKINAMI_SAI }
        val tsukinamiClosing = vidoesOnDate.filter { it.category == Video.Category.TSUKINAMI_CLOSING }
        val otherSai = vidoesOnDate.filter { it.category == Video.Category.OTHER_SAI }
        val otherClosing = vidoesOnDate.filter { it.category == Video.Category.OTHER_CLOSING }

        // 1. First row with tsukinami_sai and tsukinami_closing, if any
        val tsukinamiRows = buildRows(date, tsukinamiSai, tsukinamiClosing)

        // 2. Pair up other_sai with other_closing until nothing is left
        val otherRows = buildRows(date, otherSai, otherClosing)

        return tsukinamiRows + otherRows
    }

    private fun buildRows(date: LocalDate, sai: List<Video>, closing: List<Video>): List<VideoGroup.Row> =
        (sai zipFull closing).map { (mainVideo, closingVideo) ->
            VideoGroup.Row(date, mainVideo, closingVideo)
        }



}
