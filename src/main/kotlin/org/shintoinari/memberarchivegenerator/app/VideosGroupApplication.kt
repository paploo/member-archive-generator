package org.shintoinari.memberarchivegenerator.app

import org.shintoinari.memberarchivegenerator.prcoessor.DefaultVideoGrouper
import org.shintoinari.memberarchivegenerator.prcoessor.VideoGrouper
import org.shintoinari.memberarchivegenerator.reader.GoogleSheetReader
import org.shintoinari.memberarchivegenerator.reader.VideosReader
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.templates.WordPressSourceTemplate
import java.io.OutputStreamWriter

class VideosGroupApplication(
    override val config: Application.Config,
) : Application {

    /**
     * The main application workflow:
     * 1. Read into the domain models,
     * 2. Write the domain models out.
     */
    override suspend fun run(): Result<Unit> =
        Result.success(config.inputLocation).flatMap { path ->
            reader.read(config.toReadContext())
        }.map { videos ->
            videoGrouper(videos)
        }.map { groups ->
            groups.filter { it.year in config.years }
        }.flatMap { groups ->
            writer.write(config.toWriteContext(), groups)
        }

    private val videoGrouper: VideoGrouper = DefaultVideoGrouper()

    private val reader: VideosReader = GoogleSheetReader()

    private val writer: VideoGroupsWriter = TemplatedVideoGroupsWriter(config.outputFormat)

    private fun Application.Config.toReadContext(): VideosReader.Context =
        VideosReader.Context(
            inputLocation = inputLocation
        )

    private fun Application.Config.toWriteContext(): VideoGroupsWriter.Context =
        VideoGroupsWriter.Context(
            years = config.years,
            mode = config.outputMode,
            ioWriter = OutputStreamWriter(System.out)
        )

}