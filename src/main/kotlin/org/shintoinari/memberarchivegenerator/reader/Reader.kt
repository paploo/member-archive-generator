package org.shintoinari.memberarchivegenerator.reader

import org.shintoinari.memberarchivegenerator.data.Video
import java.nio.file.Path

/**
 * Generic interface for all processing pipeline readers.
 */
interface Reader<in C, out R> {
    suspend fun read(context: C): Result<R>
}

/**
 * Common interface for all readers of videos.
 */
interface VideosReader : Reader<VideosReader.Context, List<Video>> {

    data class Context(
        val inputFile: Path
    )

}