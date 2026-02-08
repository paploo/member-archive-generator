package org.shintoinari.memberarchivegenerator.util

import kotlin.enums.enumEntries

/**
 * Extension to `Enum` for the case-insensitive resolution of field values.
 */
inline fun <reified E: Enum<E>> valueOfCaseInsensitive(value: String): E =
    enumEntries<E>().find {
        it.name.lowercase() == value.lowercase()
    } ?: throw NoSuchElementException("Could not find value of \"$value\" in enumeration; must be one of: ${enumEntries<E>()}")