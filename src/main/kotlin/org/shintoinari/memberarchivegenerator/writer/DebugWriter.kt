package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup
import kotlin.collections.forEach

class DebugWriter : VideoGroupsWriter {

    override suspend fun write(
        context: VideoGroupsWriter.Context,
        argument: List<VideoGroup>
    ): Result<Unit> {
        argument.filter { it.year in context.years }.forEach { group ->
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