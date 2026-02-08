package org.shintoinari.memberarchivegenerator.app

import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.nio.file.Path
import java.time.Year
import kotlin.io.path.Path

/**
 * The core Application interface, serving as the entry point for actually doing work.
 *
 * Applications work on a model of:
 * 1. Creating an application `Arguments` object, which contains the state for running the application, and
 * 2. Invoking the application via `invoke`, which executes the logic against the config.
 */
interface Application : suspend (Application.Arguments) -> Result<Unit> {

    data class Arguments(
        val inputFile: Path,
        val outputFile: Path,
        val useStdOut: Boolean,
        val years: Set<Year>,
        val outputMode: VideoGroupsWriter.OutputMode,
        val outputFormat: TemplatedVideoGroupsWriter.Format,
     ) {

        companion object {
            /** We set a default path based on where things usually are downloaded from the sheet in MacOS/Windows */
            val defaultInputFile: Path = Path(System.getProperty("user.home"), "Downloads", "Livestream and Archive Video Links  - Member Video Archive.csv")

            /** We set a default to be next to the existing */
            val defaultOutputFile: Path = Path(System.getProperty("user.home"), "Downloads", "Members Archive - Output.html")

            /** By default we write to a file */
            val defaultUseStdOut: Boolean = false

            /** We set this to all the years we currently actively have data for */
            val defaultYears: Set<Year> = (2020 .. Year.now().value).map { Year.of(it) }.toSet()

            /** By default we usually just replace the full page contents */
            val defaultOutputMode: VideoGroupsWriter.OutputMode = VideoGroupsWriter.OutputMode.FullPage

            /** By default we want the WordPress format */
            val defaultOutputFormat: TemplatedVideoGroupsWriter.Format = TemplatedVideoGroupsWriter.Format.WordPress
        }

    }
}
