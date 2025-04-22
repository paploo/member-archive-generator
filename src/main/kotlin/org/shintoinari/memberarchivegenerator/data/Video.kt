package org.shintoinari.memberarchivegenerator.data

import java.time.LocalDate

data class Video(
    val youTubeId: String,
    val serviceDate: LocalDate,
    val category: Category,
    val titleEn: String?,
    val titleJp: String?,
    val isActive: Boolean
) {

    val isNotActive: Boolean = !isActive

    enum class Category {
        TsukinamiSai,
        TsukinamiClosing,
        OtherSai,
        OtherCLosing;
    }

}

