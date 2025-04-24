package org.shintoinari.memberarchivegenerator.app

import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.nio.file.Path
import java.time.Year
import kotlin.io.path.Path

interface Application{

    val config: Config

    suspend fun run(): Result<Unit>

    data class Config(
        val inputFile: Path,
        val outputFile: Path,
        val useStdOut: Boolean,
        val years: Set<Year>,
        val outputMode: VideoGroupsWriter.OutputMode,
        val outputFormat: TemplatedVideoGroupsWriter.Format,
     ) {

        companion object {
            /** We set a default path based on where things usually are downloaded from the sheet in MacOS/Windows */
            val defaultInputFile: Path = Path(System.getProperty("user.home"), "Downloads", "Members Archive - Videos.csv")

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
