package org.shintoinari.memberarchivegenerator.app

import org.shintoinari.memberarchivegenerator.prcoessor.DefaultVideoGrouper
import org.shintoinari.memberarchivegenerator.prcoessor.VideoGrouper
import org.shintoinari.memberarchivegenerator.reader.GoogleSheetReader
import org.shintoinari.memberarchivegenerator.reader.VideosReader
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.shintoinari.memberarchivegenerator.writer.DebugWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter

class VideosGroupApplication(
    override val config: Application.Config
) : Application {

    /**
     * The main application workflow:
     * 1. Read into the domain models,
     * 2. Write the domain models out.
     */
    override suspend fun run(): Result<Unit> =
        Result.success(config.inputLocation).flatMap { path ->
            reader(path)
        }.map { videos ->
            videoGrouper(videos)
        }.flatMap { groups ->
            writer(groups)
        }


    private val reader: VideosReader = GoogleSheetReader()

    private val writer: VideoGroupsWriter = DebugWriter()

    private val videoGrouper: VideoGrouper = DefaultVideoGrouper()

}