package org.shintoinari.memberarchivegenerator.app

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.prcoessor.DefaultVideoGrouper
import org.shintoinari.memberarchivegenerator.prcoessor.VideoGrouper
import org.shintoinari.memberarchivegenerator.reader.VideosReader
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class VideosGroupApplication(
    override val config: Application.Config
) : Application {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * The main application workflow:
     * 1. Read into the domain models,
     * 2. Write the domain models out.
     */
    override suspend fun run(): Result<Unit> =
        reader.read().map { videos ->
            videoGrouper(videos)
        }.flatMap { groups ->
            writer.write(groups)
        }


    private val reader: VideosReader by lazy {
        object : VideosReader {
            override suspend fun read(): Result<List<Video>> = Result.runCatching { TODO() }
        }
    }

    private val writer: VideoGroupsWriter by lazy {
        object : VideoGroupsWriter {
            override suspend fun write(values: List<VideoGroup>): Result<Unit> = Result.success(Unit)
        }
    }

    private val videoGrouper: VideoGrouper = DefaultVideoGrouper()

}