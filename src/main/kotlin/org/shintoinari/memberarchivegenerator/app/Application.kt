package org.shintoinari.memberarchivegenerator.app

import java.nio.file.Path
import java.time.Year
import kotlin.io.path.Path

interface Application{

    val config: Config

    suspend fun run()

    data class Config(
        val inputLocation: Path = defaultInputLocation,
        val years: Set<Year> = defaultYears,
     ) {
        companion object {
            private val defaultInputLocation: Path = Path(System.getProperty("user.dir"), "Downloads", "Video sheet - YouTube Member Videos.csv")
            private val defaultYears: Set<Year> = setOf(Year.now())
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

    companion object {
        operator fun invoke(block: Config.DSL.() -> Unit): Application =
            invoke(Config.DSL().apply(block).toConfig())

        operator fun invoke(config: Config): Application =
            DefaultApplication(config)
    }
}

