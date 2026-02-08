package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup
import java.time.Year

/**
 * Generic interface for all processing pipeline writers.
 */
interface Writer<in C, in A, out R> {
    suspend fun write(context: C, argument: A): Result<R>
}

/**
 * Generic interface for all writers of video groups.
 */
interface VideoGroupsWriter : Writer<VideoGroupsWriter.Context, List<VideoGroup>, Unit> {

    data class Context(
        val years: Set<Year>,
        val mode: OutputMode,
        val ioWriter: java.io.Writer
    )

    enum class OutputMode {
        YearBlocksOnly,
        FullPage,
    }

}