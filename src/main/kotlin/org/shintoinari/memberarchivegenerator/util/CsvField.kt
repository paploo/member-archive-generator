package org.shintoinari.memberarchivegenerator.util

import org.apache.commons.csv.CSVRecord

interface CsvField<T> {
    fun getFrom(record: CSVRecord): T

    class OptionalSourceValue<T>(
        val columnName: String,
        private val getter: (String?) -> T
    ) : CsvField<T> {
        override fun getFrom(record: CSVRecord): T =
            Result.runCatching {
                getter(record.get(columnName))
            }.mapFailure { th ->
                IllegalArgumentException("Error extracting value for field $columnName: $th", th)
            }.getOrThrow()
    }

    class RequiredSourceValue<T>(
        val columnName: String,
        private val getter: (String) -> T
    ) : CsvField<T> {
        override fun getFrom(record: CSVRecord): T =
            Result.runCatching {
                record.get(columnName)?.let { getter(it) }
                    ?: throw IllegalArgumentException("Expected field $columnName to contain non-null value. record = $record")
            }.mapFailure { th ->
                IllegalArgumentException("Error extracting value for field $columnName: $th", th)
            }.getOrThrow()
    }

}

operator fun <T> CSVRecord.get(field: CsvField<T>): T = field.getFrom(this)