package org.shintoinari.memberarchivegenerator.reader

import org.shintoinari.memberarchivegenerator.data.Video
import java.nio.file.Path

interface Reader<in C, out R> {
    suspend fun read(context: C): Result<R>
}

interface VideosReader : Reader<VideosReader.Context, List<Video>> {

    data class Context(
        val inputFile: Path
    )

}