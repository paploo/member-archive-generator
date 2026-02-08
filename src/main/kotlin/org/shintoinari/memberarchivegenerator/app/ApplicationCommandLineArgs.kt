package org.shintoinari.memberarchivegenerator.app

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import org.shintoinari.memberarchivegenerator.app.Application.Arguments.Companion.defaultUseStdOut
import org.shintoinari.memberarchivegenerator.util.valueOfCaseInsensitive
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.time.Year
import kotlin.io.path.Path

/**
 * Defines and parses the command line arguments for the Application.
 */
class ApplicationCommandLineArgs(parser: ArgParser) {

    val inputFile by parser.storing("-i", "--input",
        help = "Input file path. Default = \"${Application.Arguments.defaultInputFile}\""
    ) {
        Path(this)
    }.default(Application.Arguments.defaultInputFile)

    val outputFile by parser.storing("-o", "--output",
        help = "Output file path. Default = \"${Application.Arguments.defaultOutputFile}\""
    ) {
        Path(this)
    }.default(Application.Arguments.defaultOutputFile)

    val useStdOut by parser.flagging("--stdout",
        help = "Output to stdout instead of file. Default = ${Application.Arguments.defaultUseStdOut}"
    ).default(defaultUseStdOut)

    val years by parser.adding("-y", "--year",
        help = "Year(s) to output (may be supplied multiple times to output multiple years). Default = ${Application.Arguments.defaultYears}"
    ) {
        Year.of(this.toInt())
    }

    val outputMode by parser.storing("-m", "--mode",
        help = "Output mode ${VideoGroupsWriter.OutputMode.entries}. Default = ${Application.Arguments.defaultOutputMode}"
    ) {
        valueOfCaseInsensitive<VideoGroupsWriter.OutputMode>(this)
    }.default(Application.Arguments.defaultOutputMode)


    val outputFormat by parser.storing("-f", "--format",
        help = "Output format ${TemplatedVideoGroupsWriter.Format.entries}. Default = ${Application.Arguments.defaultOutputFormat}"
    ) {
        valueOfCaseInsensitive<TemplatedVideoGroupsWriter.Format>(this)
    }.default(Application.Arguments.defaultOutputFormat)

    fun toConfig() = Application.Arguments(
        inputFile = inputFile,
        outputFile = outputFile,
        useStdOut = useStdOut,
        years = years.toSet().ifEmpty { Application.Arguments.defaultYears },
        outputMode = outputMode,
        outputFormat = outputFormat,
    )

    companion object {
        fun parse(args: Array<String>): Result<Application.Arguments> =
            Result.runCatching {
                ArgParser(args).parseInto(::ApplicationCommandLineArgs).toConfig()
            }
    }

}