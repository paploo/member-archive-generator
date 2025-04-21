package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate

data class Video(
    val youTubeId: String,
    val serviceDate: LocalDate,
    val category: Category,
    val titleEn: String,
    val titleJp: String,
    val isActive: Boolean
) {

    enum class Category {
        UNCATEGORIZED,
        TSUKINAMI_SAI,
        TSUKINAMI_CLOSING,
        OTHER_SAI,
        OTHER_CLOSING;
    }

}

