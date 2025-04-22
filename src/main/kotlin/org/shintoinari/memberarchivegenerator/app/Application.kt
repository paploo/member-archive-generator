package org.shintoinari.memberarchivegenerator.app

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
     ) {

        companion object {
            operator fun invoke(block: (DSL) -> Unit): Config =
                DSL().apply(block).toConfig()

            /** We set a default path based on where things usually are downloaded in MacOS/Windows */
            val defaultInputLocation: Path = Path(System.getProperty("user.home"), "Downloads", "Video sheet - YouTube Member Videos.csv")

            /** We set this to all the years we currently actively have data for */
            val defaultYears: Set<Year> = (2021 .. Year.now().value).map { Year.of(it) }.toSet()

            /** Most use cases need the year blocks only */
            val defaultOutputMode: VideoGroupsWriter.OutputMode = VideoGroupsWriter.OutputMode.YearBlocksOnly
        }

        data class DSL(
            var inputLocation: Path = defaultInputLocation,
            var years: Set<Year> = defaultYears,
            var mode: VideoGroupsWriter.OutputMode = defaultOutputMode,
        ) {
            fun toConfig() = Config(
                inputLocation = inputLocation,
                years = years,
                outputMode = mode,
            )
        }

    }
}
