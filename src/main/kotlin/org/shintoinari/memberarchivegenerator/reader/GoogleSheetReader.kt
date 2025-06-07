package org.shintoinari.memberarchivegenerator.reader

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.util.CsvField
import org.shintoinari.memberarchivegenerator.util.CsvFormats
import org.shintoinari.memberarchivegenerator.util.get
import org.shintoinari.memberarchivegenerator.util.logger
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GoogleSheetReader: VideosReader {

    private val format: CSVFormat = CsvFormats.googleSheet

    /**
     * Reads the google sheet document output.
     *
     * 1. Parse the file into a CSV object.
     * 2. For each row, convert to a [Video] object.
     *
     * The conversion has some trickiness
     */
    override suspend fun read(context: VideosReader.Context): Result<List<Video>> =
        Result.runCatching {
            CSVParser.parse(context.inputFile, StandardCharsets.UTF_8, format)
        }.mapCatching { parser ->
            parser.map { it.toVideo() }
        }

    private fun CSVRecord.toVideo(): Video =
        Video(
            youTubeId = get(CsvFields.youTubeIdField),
            serviceDate = get(CsvFields.serviceDateField),
            category = get(CsvFields.categoryField),
            titleEn = get(CsvFields.titleEnField),
            titleJp = get(CsvFields.titleJpField),
            isActive = get(CsvFields.isActiveField),
        ).also {
            logger.debug(it.toString())
        }

    private object CsvFields {

        val youTubeIdField: CsvField<String> = CsvField.RequiredSourceValue("ID") {
            it
        }

        val serviceDateField: CsvField<LocalDate> = CsvField.RequiredSourceValue("Service Date") {
            val isoRegex = Regex("""^\d{4}-\d{2}-\d{2}$""") // Matches yyyy-MM-dd
            val usRegex = Regex("""^(\d{1,2})/(\d{1,2})/(\d{4})$""") // Matches M/D/YYYY

            when {
                isoRegex.matches(it)  ->
                    LocalDate.parse(it)
                usRegex.matches(it) ->
                    LocalDate.parse(it, DateTimeFormatter.ofPattern("M/d/yyyy"))
                else ->
                    throw IllegalArgumentException("Unrecognized date formate for service date: $it")
            }
        }

        val categoryField: CsvField<Video.Category> = CsvField.OptionalSourceValue("Category") {
            when (it?.lowercase()?.trim()) {
                "tsukinami-sai" -> Video.Category.TsukinamiSai
                "tsukinami-closing" -> Video.Category.TsukinamiClosing
                "other-sai" -> Video.Category.OtherSai
                "other-closing" -> Video.Category.OtherCLosing
                null -> throw IllegalArgumentException("Category is required")
                else -> throw IllegalArgumentException("Unknown category: $it")
            }
        }

        val isActiveField: CsvField<Boolean> = CsvField.OptionalSourceValue("Skip") {
            when(it?.lowercase()?.trim()?.firstOrNull()) {
                // Skip
                't' -> true
                'y' -> true

                //Don't Skip
                'f' -> false
                'n' -> false
                null -> false //If we have no value, we take it as being active

                //Other
                else -> throw IllegalArgumentException("Unknown value for skip: $it")
            }.let { shouldSkip ->
                !shouldSkip
            }
        }

        val titleEnField: CsvField<String?> = CsvField.OptionalSourceValue("Title EN") {
            it
        }

        val titleJpField: CsvField<String?> = CsvField.OptionalSourceValue("Title JP") {
            it
        }

    }

}
