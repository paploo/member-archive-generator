package org.shintoinari.memberarchivegenerator.app

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.time.Year
import kotlin.io.path.Path

class ApplicationArgs(parser: ArgParser) {

    val filePath by parser.storing("-f", "--file", help = "Input file path") {
        Path(this)
    }.default(null)

    val years by parser.adding("-y", "--year", help = "Year to output") {
        Year.of(this.toInt())
    }

    fun toConfig() = Application.Config { dsl ->
        filePath?.let { dsl.inputLocation = it }
        if(years.isNotEmpty()) { dsl.years = years.toSet() }
    }

    companion object {
        fun parse(args: Array<String>): Result<Application.Config> =
            Result.runCatching {
                ArgParser(args).parseInto(::ApplicationArgs).toConfig()
            }
    }

}