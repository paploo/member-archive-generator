package org.shintoinari.memberarchivegenerator.app

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.time.Year
import kotlin.io.path.Path

class ApplicationArgs(parser: ArgParser) {

    val filePath by parser.storing("-f", "--file",
        help = "Input file path. Default = ${Application.Config.defaultInputLocation}"
    ) {
        Path(this)
    }.default(null)

    val years by parser.adding("-y", "--year",
        help = "Year(s) to output (may be supplied multiple times. Default = ${Application.Config.defaultYears}"
    ) {
        Year.of(this.toInt())
    }

    val fullPage by parser.flagging("--full", "--full-page",
        help = "Output the full page (i.e. headers and footers). Default = ${Application.Config.defaultOutputMode}"
    )

    fun toConfig() = Application.Config { dsl ->
        filePath?.let { dsl.inputLocation = it }

        if(years.isNotEmpty()) { dsl.years = years.toSet() }

        dsl.mode = when(fullPage) {
            true -> VideoGroupsWriter.OutputMode.FullPage
            false -> VideoGroupsWriter.OutputMode.YearBlocksOnly
        }
    }

    companion object {
        fun parse(args: Array<String>): Result<Application.Config> =
            Result.runCatching {
                ArgParser(args).parseInto(::ApplicationArgs).toConfig()
            }
    }

}