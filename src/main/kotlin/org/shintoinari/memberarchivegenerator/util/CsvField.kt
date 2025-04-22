package org.shintoinari.memberarchivegenerator.util

import org.apache.commons.csv.CSVRecord

interface CsvField<T> {
    val name: String
    fun get(record: CSVRecord): T

    class OptionalSourceValue<T>(
        override val name: String,
        private val getter: (String?) -> T
    ) : CsvField<T> {
        override fun get(record: CSVRecord): T =
            Result.runCatching {
                getter(record.get(name))
            }.mapFailure { th ->
                IllegalArgumentException("Error extracting value for field $name: $th", th)
            }.getOrThrow()
    }

    class RequiredSourceValue<T>(
        override val name: String,
        private val getter: (String) -> T
    ) : CsvField<T> {
        override fun get(record: CSVRecord): T =
            Result.runCatching {
                record.get(name)?.let { getter(it) }
                    ?: throw IllegalArgumentException("Expected field $name to contain non-null value. record = $record")
            }.mapFailure { th ->
                IllegalArgumentException("Error extracting value for field $name: $th", th)
            }.getOrThrow()
    }

}

operator fun <T> CSVRecord.get(field: CsvField<T>): T = field.get(this)