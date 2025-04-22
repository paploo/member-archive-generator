package org.shintoinari.memberarchivegenerator.app

import java.nio.file.Path
import java.time.Year
import kotlin.io.path.Path

interface Application{

    val config: Config

    suspend fun run(): Result<Unit>

    data class Config(
        val inputLocation: Path = defaultInputLocation,
        val years: Set<Year> = defaultYears,
     ) {
        companion object {
            operator fun invoke(block: (DSL) -> Unit): Config =
                DSL().apply(block).toConfig()

            /** We set a default path based on where things usually are downloaded in MacOS/Windows */
            private val defaultInputLocation: Path = Path(System.getProperty("user.home"), "Downloads", "Video sheet - YouTube Member Videos.csv")

            /** We set this to all the years we currently actively have data for */
            private val defaultYears: Set<Year> = (2021 .. Year.now().value).map { Year.of(it) }.toSet()
        }

        data class DSL(
            var inputLocation: Path = defaultInputLocation,
            var years: Set<Year> = defaultYears,
        ) {
            fun toConfig() = Config(
                inputLocation = inputLocation,
                years = years,
            )
        }

    }
}
