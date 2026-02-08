package org.shintoinari.memberarchivegenerator.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Year

class VideoGroupTest : DescribeSpec({

    describe("instantiation") {

        it("should succeed to instantiate if all rows have the same year as the group") {
            val year = Year.of(2024)
            val rows = listOf(
                VideoGroup.Row(
                    date = LocalDate.of(2024, 1, 1),
                    saiVideo = TestBuilders.buildTestVideo(serviceDate = LocalDate.of(2024, 1, 1)),
                    closingVideo = null
                ),
                VideoGroup.Row(
                    date = LocalDate.of(2024, 6, 15),
                    saiVideo = TestBuilders.buildTestVideo(serviceDate = LocalDate.of(2024, 6, 15)),
                    closingVideo = null
                )
            )

            val videoGroup = VideoGroup(year = year, rows = rows)

            videoGroup.year shouldBe year
            videoGroup.rows shouldBe rows
        }

        it("should fail if the year on a row does not match that of the group") {
            val year = Year.of(2024)
            val rows = listOf(
                VideoGroup.Row(
                    date = LocalDate.of(2024, 1, 1),
                    saiVideo = TestBuilders.buildTestVideo(serviceDate = LocalDate.of(2024, 1, 1)),
                    closingVideo = null
                ),
                VideoGroup.Row(
                    date = LocalDate.of(2023, 6, 15),
                    saiVideo = TestBuilders.buildTestVideo(serviceDate = LocalDate.of(2023, 6, 15)),
                    closingVideo = null
                )
            )

            shouldThrow<IllegalStateException> {
                VideoGroup(year = year, rows = rows)
            }
        }

    }

    describe("Row") {

        describe("instantiation") {

            it("should not allow both the saiVideo and closingVideo to be missing") {
                shouldThrow<IllegalStateException> {
                    VideoGroup.Row(
                        date = LocalDate.of(2024, 1, 1),
                        saiVideo = null,
                        closingVideo = null
                    )
                }
            }

            it("should allow the saiVideo to be missing if the closingVideo is present") {
                val date = LocalDate.of(2024, 1, 1)
                val closingVideo = TestBuilders.buildTestVideo(serviceDate = date)

                val row = VideoGroup.Row(
                    date = date,
                    saiVideo = null,
                    closingVideo = closingVideo
                )

                row.saiVideo shouldBe null
                row.closingVideo shouldBe closingVideo
            }

            it("should allow the closingVideo to be missing if the saiVideo is present") {
                val date = LocalDate.of(2024, 1, 1)
                val saiVideo = TestBuilders.buildTestVideo(serviceDate = date)

                val row = VideoGroup.Row(
                    date = date,
                    saiVideo = saiVideo,
                    closingVideo = null
                )

                row.saiVideo shouldBe saiVideo
                row.closingVideo shouldBe null
            }

            it("should error if the saiVideo's date is not the same as the Row's date") {
                val rowDate = LocalDate.of(2024, 1, 1)
                val videoDate = LocalDate.of(2024, 1, 2)

                shouldThrow<IllegalStateException> {
                    VideoGroup.Row(
                        date = rowDate,
                        saiVideo = TestBuilders.buildTestVideo(serviceDate = videoDate),
                        closingVideo = null
                    )
                }
            }

            it("should error if the closingVideo's date is not the same as the Row's date") {
                val rowDate = LocalDate.of(2024, 1, 1)
                val videoDate = LocalDate.of(2024, 1, 2)

                shouldThrow<IllegalStateException> {
                    VideoGroup.Row(
                        date = rowDate,
                        saiVideo = null,
                        closingVideo = TestBuilders.buildTestVideo(serviceDate = videoDate)
                    )
                }
            }

        }

    }

}) {

    internal object TestBuilders {

        internal fun buildTestVideo(
            serviceDate: LocalDate,
            youTubeId: String? = "abc123",
            isSkipped: Boolean = false
        ): Video = Video(
            youTubeId = youTubeId,
            serviceDate = serviceDate,
            category = Video.Category.TsukinamiSai,
            titleEn = "Test Title",
            titleJp = "テストタイトル",
            isSkipped = isSkipped
        )

    }

}
