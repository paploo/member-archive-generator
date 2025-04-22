package org.shintoinari.memberarchivegenerator

import org.shintoinari.memberarchivegenerator.app.ApplicationArgs
import org.shintoinari.memberarchivegenerator.app.VideosGroupApplication
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.shintoinari.memberarchivegenerator.util.logger

suspend fun main(args: Array<String>): Unit =
    Main.main(args)

object Main {

    suspend fun main(args: Array<String>): Unit =
        ApplicationArgs.parse(args).map { config ->
            Main.logger.debug("config: {}", config)
            VideosGroupApplication(config)
        }.flatMap { application ->
            application.run()
        }.recover { th ->
            Main.logger.error("APPLICATION FAILED TO RUN: error = $th", th)
            Unit
        }.getOrThrow()

}