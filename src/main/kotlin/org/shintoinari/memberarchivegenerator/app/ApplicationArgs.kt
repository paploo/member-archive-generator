package org.shintoinari.memberarchivegenerator.app

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import org.shintoinari.memberarchivegenerator.util.valueOfCaseInsensitive
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.time.Year
import kotlin.io.path.Path

class ApplicationArgs(parser: ArgParser) {

    val inputLocation by parser.storing("-f", "--file",
        help = "Input file path. Default = \"${Application.Config.defaultInputLocation}\""
    ) {
        Path(this)
    }.default(Application.Config.defaultInputLocation)

    val years by parser.adding("-y", "--year",
        help = "Year(s) to output (may be supplied multiple times. Default = ${Application.Config.defaultYears}"
    ) {
        Year.of(this.toInt())
    }

    val outputMode by parser.storing("-m", "--mode",
        help = "Output mode ${VideoGroupsWriter.OutputMode.entries}. Default = ${Application.Config.defaultOutputMode}"
    ) {
        valueOfCaseInsensitive<VideoGroupsWriter.OutputMode>(this)
    }.default(Application.Config.defaultOutputMode)


    val outputFormat by parser.storing("--format",
        help = "Output format ${TemplatedVideoGroupsWriter.Format.entries}. Default = ${Application.Config.defaultOutputFormat}"
    ) {
        valueOfCaseInsensitive<TemplatedVideoGroupsWriter.Format>(this)
    }.default(Application.Config.defaultOutputFormat)

    fun toConfig() = Application.Config(
        inputLocation = inputLocation,
        years = years.toSet().ifEmpty { Application.Config.defaultYears },
        outputMode = outputMode,
        outputFormat = outputFormat,
    )

    companion object {
        fun parse(args: Array<String>): Result<Application.Config> =
            Result.runCatching {
                ArgParser(args).parseInto(::ApplicationArgs).toConfig()
            }
    }

}