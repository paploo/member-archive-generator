package org.shintoinari.memberarchivegenerator

import org.shintoinari.memberarchivegenerator.app.ApplicationArgs
import org.shintoinari.memberarchivegenerator.app.VideosGroupApplication
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory

suspend fun main(args: Array<String>): Unit =
    ApplicationArgs.parse(args).map { config ->
        VideosGroupApplication(config)
    }.flatMap { application ->
        application.run()
    }.recover { th ->
        logger.error("Application failed to run: $th", th)
        Unit
    }.getOrThrow()

private val logger: Logger = LoggerFactory.getLogger("Main")