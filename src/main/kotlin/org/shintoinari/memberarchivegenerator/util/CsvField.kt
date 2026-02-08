package org.shintoinari.memberarchivegenerator.util

import org.apache.commons.csv.CSVRecord

/**
 * Class for encapsulating the logic of extracting values from CSV records.
 */
interface CsvField<T> {

    /**
     * Extracts the value of this field from the given CSV record.
     *
     * Callers may prefer the ``CSVRecord` extension method `get` when parsing a record.
     */
    fun getFrom(record: CSVRecord): T

    /**
     * Extracts the value of this field from the given CSV record, allowing processing of nulls.
     *
     * Note that the return type `T` may or may not be nullable, allowing for custom handling of null values into
     * non-nullable types (for example turning a null into a default value or treating it as false on a boolean column).
     *
     * @param columnName The name of the column in the CSV file to extract from.
     * @param getter A function that extracts the value from the CSV record.
     */
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

    /**
     * Extracts the value of this field from the given CSV record, or throws an exception if the field is missing.
     */
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

/**
 * Extension function to retrieve the value of a CSV field from a CSV record.
 */
operator fun <T> CSVRecord.get(field: CsvField<T>): T = field.getFrom(this)