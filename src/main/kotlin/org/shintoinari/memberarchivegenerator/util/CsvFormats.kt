package org.shintoinari.memberarchivegenerator.util

import org.apache.commons.csv.CSVFormat

object CsvFormats {

    val default: CSVFormat = CSVFormat.Builder
        .create(CSVFormat.RFC4180)
        .setHeader() // Gets the header; only works on parse.
        .setNullString("") // Empty strings between delimiters are null records.
        .setCommentMarker('#') //Enables comments in a way that works with Anki output.
        .setIgnoreEmptyLines(true) //Ignore empty lines
        .setTrim(true) //Trim leading/trailing whitespace. This is normally what we want.
        .setRecordSeparator('\n') //This sets the line endings to UNIX ones, breaking RFC4180 but matching the Ruby reference impl.
        .setSkipHeaderRecord(true) // On output don't write a header record; instead we usually manage this ourselves since we have simple collections at that point.
        .get()

    val googleSheet: CSVFormat = CSVFormat.Builder
        .create(default)
        .setAllowMissingColumnNames(true) //We have separator columns; usually I'd prefer to yell.
        .get()

}