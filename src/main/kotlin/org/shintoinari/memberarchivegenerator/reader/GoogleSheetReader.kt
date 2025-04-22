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
    override suspend fun invoke(fileLocation: Path): Result<List<Video>> =
        Result.runCatching {
            CSVParser.parse(fileLocation, StandardCharsets.UTF_8, format)
        }.mapCatching { parser ->
            parser.map { it.toVideo() }
        }

    private fun CSVRecord.toVideo(): Video =
        Video(
            youTubeId = get(youTubeIdField),
            serviceDate = get(serviceDateField),
            category = get(categoryField),
            titleEn = get(titleEnField),
            titleJp = get(titleJpField),
            isActive = get(isActiveField),
        ).also {
            logger.debug(it.toString())
        }

    val youTubeIdField: CsvField<String> = CsvField.NonNullableSourceValue("ID") {
        it
    }

    val serviceDateField: CsvField<LocalDate> = CsvField.NonNullableSourceValue("Service Date") {
        LocalDate.parse(it)
    }

    val categoryField: CsvField<Video.Category> = CsvField.NullableSourceValue("Category") {
        when(it?.lowercase()?.trim()) {
            "tsukinami-sai" -> Video.Category.TSUKINAMI_SAI
            "tsukinami-closing" -> Video.Category.TSUKINAMI_CLOSING
            "other-sai" -> Video.Category.OTHER_SAI
            "other-closing" -> Video.Category.OTHER_CLOSING
            "skip" -> Video.Category.OTHER_SAI //TODO: Make this its own column in sheet.
            null -> Video.Category.OTHER_SAI //TODO: Fill these in directly in the sheet.
            else -> throw IllegalArgumentException("Unknown category: $it")
        }
    }

    val isActiveField: CsvField<Boolean> = CsvField.NullableSourceValue("Category") {
        when(it?.lowercase()?.trim()) {
            "skip" -> false
            else -> true
        }
    }

    val titleEnField: CsvField<String?> = CsvField.NullableSourceValue("Title EN") {
        it
    }

    val titleJpField: CsvField<String?> = CsvField.NullableSourceValue("Title JP") {
        it
    }

}
