package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup
import kotlin.collections.forEach

class DebugWriter : VideoGroupsWriter {

    override suspend fun invoke(videos: List<VideoGroup>): Result<Unit> {
        videos.forEach { group ->
            println(group.year)
            group.rows.forEach { row ->
                println("\t${row.date}")
                println("\t\t${row.saiColumn}")
                println("\t\t${row.closingColumn}")
            }
        }

        return Result.success(Unit)
    }
}