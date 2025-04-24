package org.shintoinari.memberarchivegenerator.app

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import org.shintoinari.memberarchivegenerator.app.Application.Config.Companion.defaultUseStdOut
import org.shintoinari.memberarchivegenerator.util.valueOfCaseInsensitive
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.time.Year
import kotlin.io.path.Path

class ApplicationArgs(parser: ArgParser) {

    val inputFile by parser.storing("-i", "--input",
        help = "Input file path. Default = \"${Application.Config.defaultInputFile}\""
    ) {
        Path(this)
    }.default(Application.Config.defaultInputFile)

    val outputFile by parser.storing("-o", "--output",
        help = "Output file path. Default = \"${Application.Config.defaultOutputFile}\""
    ) {
        Path(this)
    }.default(Application.Config.defaultOutputFile)

    val useStdOut by parser.flagging("--stdout",
        help = "Output to stdout instead of file. Default = ${Application.Config.defaultUseStdOut}"
    ).default(defaultUseStdOut)

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


    val outputFormat by parser.storing("-f", "--format",
        help = "Output format ${TemplatedVideoGroupsWriter.Format.entries}. Default = ${Application.Config.defaultOutputFormat}"
    ) {
        valueOfCaseInsensitive<TemplatedVideoGroupsWriter.Format>(this)
    }.default(Application.Config.defaultOutputFormat)

    fun toConfig() = Application.Config(
        inputFile = inputFile,
        outputFile = outputFile,
        useStdOut = useStdOut,
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