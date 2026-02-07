package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate

/**
 * Data structure representing a video entry.
 */
data class Video(
    val youTubeId: String?,
    val serviceDate: LocalDate,
    val category: Category,
    val titleEn: String?,
    val titleJp: String?,
    val isSkipped: Boolean,
) {

    /**
     * Determines if the video is "active" based on its data.
     *
     * The primary use-case for this is in filtering what entries are
     * shown on the page.
     */
    val isActive: Boolean = !isSkipped && youTubeId != null

    /**
     * Convenience property for filtering out inactive entries.
     */
    val isNotActive: Boolean = !isActive

    /**
     * Enum representing the different categories of videos.
     */
    enum class Category {
        /** For tsukinami-sai videos */
        TsukinamiSai,
        /** For closing talks that go with a tsukinami-sai on the same date */
        TsukinamiClosing,
        /** For various other ceremonies, such as setsubun */
        OtherSai,
        /** For closing talks related to `OtherSai` entries */
        OtherClosing;
    }

}

