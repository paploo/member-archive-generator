package org.shintoinari.memberarchivegenerator.pipeline

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.prcoessor.VideoGrouper
import org.shintoinari.memberarchivegenerator.reader.VideosReader
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.shintoinari.memberarchivegenerator.util.logger
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter

/**
 * The main pipeline:
 * 1. Reads the videos from a source,
 * 2. Filters the videos,
 * 3. Groups the videos,
 * 4. Filters the groups, and
 * 5. Writes the groups/videos to an output source.
 */
class StandardVideoPipeline(
    val reader: VideosReader,
    val writer: VideoGroupsWriter,
    val grouper: VideoGrouper,
    val videoFilter: (Video) -> Boolean,
    val groupFilter: (VideoGroup) -> Boolean,
) : VideoPipeline<StandardVideoPipeline.Input> {

    data class Input(
        val readerContext: VideosReader.Context,
        val writerContext: VideoGroupsWriter.Context
    )

    override suspend fun invoke(input: Input): Result<Unit> =
        Result.success(true).flatMap { path ->
            reader.read(input.readerContext)
        }.map { videos ->
            logger.info("Found ${videos.size} total videos")
            videos.filter(videoFilter)
        }.map { filteredVideos ->
            logger.info("Using ${filteredVideos.size} active videos")
            grouper(filteredVideos)
        }.map { groups ->
            logger.info("Collated into ${groups.size} video groups")
            groups.filter(groupFilter)
        }.flatMap { groups ->
            writer.write(input.writerContext, groups)
        }
}