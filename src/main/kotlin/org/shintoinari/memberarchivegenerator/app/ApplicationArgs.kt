package org.shintoinari.memberarchivegenerator.app

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
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

    val yearBlocksOnly by parser.flagging("--year-blocks", "--year-blocks-only",
        help = "Output the year blocks only. Default = ${Application.Config.defaultOutputMode}"
    )

    val formatter by parser.storing("--format",
        help = "Output format ${TemplatedVideoGroupsWriter.Format.entries}. Default = ${Application.Config.defaultOutputFormat}"
    ) {
        TemplatedVideoGroupsWriter.Format.valueOf(this)
    }.default(null)

    fun toConfig() = Application.Config { dsl ->
        filePath?.let { dsl.inputLocation = it }

        if(years.isNotEmpty()) { dsl.years = years.toSet() }

        dsl.mode = when {
            fullPage -> VideoGroupsWriter.OutputMode.FullPage
            yearBlocksOnly -> VideoGroupsWriter.OutputMode.YearBlocksOnly
            else -> dsl.mode
        }

        formatter?.let { dsl.outputFormat = it }
    }

    companion object {
        fun parse(args: Array<String>): Result<Application.Config> =
            Result.runCatching {
                ArgParser(args).parseInto(::ApplicationArgs).toConfig()
            }
    }

}