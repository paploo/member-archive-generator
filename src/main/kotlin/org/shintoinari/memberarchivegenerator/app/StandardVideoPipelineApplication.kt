package org.shintoinari.memberarchivegenerator.app

import org.shintoinari.memberarchivegenerator.pipeline.StandardVideoPipeline
import org.shintoinari.memberarchivegenerator.pipeline.VideoPipeline
import org.shintoinari.memberarchivegenerator.prcoessor.DefaultVideoGrouper
import org.shintoinari.memberarchivegenerator.reader.GoogleSheetReader
import org.shintoinari.memberarchivegenerator.reader.VideosReader
import org.shintoinari.memberarchivegenerator.util.logger
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.io.FileWriter
import java.io.OutputStreamWriter
import kotlin.io.path.extension

/**
 * Invokes a `StandardVideoPipeline`, bridging the gap between the application configuration and the pipeline.
 */
object StandardVideoPipelineApplication : Application {

    /**
     * The main application pipeline.
     */
    override suspend fun invoke(args: Application.Arguments): Result<Unit> =
        pipeline(args).invoke(args.toVideoPipelineInput())

    /**
     * Constructs the appropriate pipeline for the application configuration.
     */
    private fun pipeline(args: Application.Arguments): VideoPipeline<StandardVideoPipeline.Input> = StandardVideoPipeline(
        reader = GoogleSheetReader(),
        writer = TemplatedVideoGroupsWriter(TemplatedVideoGroupsWriter.Format.Simple),
        grouper = DefaultVideoGrouper(),
        videoFilter = { it.isActive },
        groupFilter = { it.year in args.years }
    )

    /**
     * Constructs the appropriate input for the configured video pipeline.
     */
    private fun Application.Arguments.toVideoPipelineInput(): StandardVideoPipeline.Input =
        StandardVideoPipeline.Input(
            readerContext = toReadContext(),
            writerContext = toWriteContext()
        )

    private fun Application.Arguments.toReadContext(): VideosReader.Context =
        VideosReader.Context(
            inputFile = inputFile
        )

    private fun Application.Arguments.toWriteContext(): VideoGroupsWriter.Context =
        VideoGroupsWriter.Context(
            years = years,
            mode = outputMode,
            ioWriter = toOutputWriter()
        )

    private fun Application.Arguments.toOutputWriter(): java.io.Writer =
        when(useStdOut) {
           true -> OutputStreamWriter(System.out)
           else -> {
               if (outputFile.extension.lowercase() != outputFormat.fileExtension.lowercase()) {
                   logger.warn("Output file extension \"${outputFile.extension}\" does not match recommended extension \"${outputFormat.fileExtension}\"; consider changing output file extension.")
               }

               FileWriter(outputFile.toFile()).also {
                   logger.info("Writing to file: \"${outputFile.toAbsolutePath()}\"")
               }
           }
        }

}