package org.shintoinari.memberarchivegenerator

import com.xenomachina.argparser.ShowHelpException
import org.shintoinari.memberarchivegenerator.app.ApplicationArgs
import org.shintoinari.memberarchivegenerator.app.VideosGroupApplication
import org.shintoinari.memberarchivegenerator.util.flatMap
import org.shintoinari.memberarchivegenerator.util.logger
import java.io.StringWriter

suspend fun main(args: Array<String>): Unit =
    Main.main(args)

object Main {

    suspend fun main(args: Array<String>): Unit =
        ApplicationArgs.parse(args).map { config ->
            VideosGroupApplication(config)
        }.flatMap { application ->
            application.run()
        }.recover { th ->
            when (th) {
                is ShowHelpException -> println(th.helpMessage())
                else -> logger.error("APPLICATION FAILED TO RUN: error = $th", th)
            }
        }.getOrThrow()

    private fun ShowHelpException.helpMessage(): String =
        StringWriter().use { writer ->
            printUserMessage(writer, commandLineName, terminalColumns)
            writer.toString()
        }

    val commandLineName = "member-archive-generator"

    val terminalColumns = System.getenv("COLUMNS")?.toInt() ?: 120

}