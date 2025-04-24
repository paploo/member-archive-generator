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
        val inputLocation: Path = defaultInputLocation,
        val years: Set<Year> = defaultYears,
        val outputMode: VideoGroupsWriter.OutputMode = defaultOutputMode,
        val outputFormat: TemplatedVideoGroupsWriter.Format = defaultOutputFormat,
     ) {

        companion object {
            /** We set a default path based on where things usually are downloaded in MacOS/Windows */
            val defaultInputLocation: Path = Path(System.getProperty("user.home"), "Downloads", "Video sheet - YouTube Member Videos.csv")

            /** We set this to all the years we currently actively have data for */
            val defaultYears: Set<Year> = (2021 .. Year.now().value).map { Year.of(it) }.toSet()

            /** By default we usually just replace the full page contents */
            val defaultOutputMode: VideoGroupsWriter.OutputMode = VideoGroupsWriter.OutputMode.FullPage

            /** By default we want the page contents to replace */
            val defaultOutputFormat: TemplatedVideoGroupsWriter.Format = TemplatedVideoGroupsWriter.Format.Html
        }

    }
}
