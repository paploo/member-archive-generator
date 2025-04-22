package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup

interface Writer<in T> {
    suspend fun write(values: T): Result<Unit>
}

typealias VideoGroupsWriter = Writer<List<VideoGroup>>