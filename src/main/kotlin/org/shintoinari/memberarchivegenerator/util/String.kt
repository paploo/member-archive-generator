package org.shintoinari.memberarchivegenerator.util

fun String.toFullWidthNumeric(): String =
    String(map { it.toFullWidthNumeric() }.toCharArray())

fun Char.toFullWidthNumeric(): Char = when {
    this in '0'..'9' -> {
        // The offset between ASCII digits and full-width digits in Unicode
        val offset = 0xFF10 - 0x30  // (U+FF10 - U+0030)
        (this.code + offset).toChar()
    }

    else -> this
}