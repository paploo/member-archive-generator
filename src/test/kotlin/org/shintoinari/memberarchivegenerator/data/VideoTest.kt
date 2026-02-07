package org.shintoinari.memberarchivegenerator.data

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class VideoTest : DescribeSpec({

    describe("youTubeId") {

        it("should allow a null youTubeId") {
            val video = TestBuilders.buildTestVideo(youTubeId = null)

            video.youTubeId shouldBe null
        }

    }

    describe("isActive") {

        it("should be inactive if skipped") {
            val video = TestBuilders.buildTestVideo(youTubeId = "abc123", isSkipped = true)

            video.isActive.shouldBeFalse()
        }

        it("should be inactive if the youTubeId is missing") {
            val video = TestBuilders.buildTestVideo(youTubeId = null, isSkipped = false)

            video.isActive.shouldBeFalse()
        }

        it("should be active if both the youTubeId is present, and it is not skipped") {
            val video = TestBuilders.buildTestVideo(youTubeId = "abc123", isSkipped = false)

            video.isActive.shouldBeTrue()
        }

    }

}) {

    object TestBuilders {

        internal fun buildTestVideo(
            youTubeId: String?,
            isSkipped: Boolean = false
        ): Video = Video(
            youTubeId = youTubeId,
            serviceDate = LocalDate.of(2024, 1, 1),
            category = Video.Category.TsukinamiSai,
            titleEn = "Test Title",
            titleJp = "テストタイトル",
            isSkipped = isSkipped
        )

    }

}
