package org.shintoinari.memberarchivegenerator.util

/**
 * Like [zip] but keeps running until both lists are exhausted, using `null` for values if a list runs out.
 *
 * As a side-effect, this allows for null values within lists.
 */
infix fun <T> List<T>.zipFull(other: List<T>): List<Pair<T?, T?>> =
    (0 until maxOf(this.size, other.size)).map { index ->
        this.getOrNull(index) to other.getOrNull(index)
    }