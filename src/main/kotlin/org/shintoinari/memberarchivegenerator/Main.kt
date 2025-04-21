package org.shintoinari.memberarchivegenerator

import com.xenomachina.argparser.ArgParser
import org.shintoinari.memberarchivegenerator.app.ApplicationArgs
import org.shintoinari.memberarchivegenerator.app.DefaultApplication

suspend fun main(args: Array<String>) {
    val config = ArgParser(args).parseInto(::ApplicationArgs).toConfig()
    println(config)
    val application = DefaultApplication(config)

    application.run()
}