package org.shintoinari.memberarchivegenerator.reader

import org.shintoinari.memberarchivegenerator.data.Video

interface Reader<out T> {
    suspend fun read(): Result<T>
}

typealias VideosReader = Reader<List<Video>>