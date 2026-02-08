package org.shintoinari.memberarchivegenerator.reader
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.shintoinari.memberarchivegenerator.data.Video
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate

class GoogleSheetReaderTest : DescribeSpec({

    val reader = GoogleSheetReader()

    describe("read") {

        describe("youTubeId") {

            it("should parse the YouTubeID") {
                val file = testResourceFile("youtube-id/parse-youtube-id.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 1
                    videos[0].youTubeId shouldBe "test001abc"
                }
            }

            it("should interpret a missing value as null") {
                val file = testResourceFile("youtube-id/missing-value-as-null.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 1
                    videos[0].youTubeId shouldBe null
                }
            }

            it("should interpret a single space as null") {
                val file = testResourceFile("youtube-id/single-space-as-null.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 1
                    videos[0].youTubeId shouldBe null
                }
            }

        }

        describe("serviceDate") {

            it("should parse an ISO date") {
                val file = testResourceFile("service-date/parse-iso-date.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 1
                    videos[0].serviceDate shouldBe LocalDate.of(2025, 1, 1)
                }
            }

            it("should parse a US formatted date") {
                val file = testResourceFile("service-date/parse-us-dates.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 4
                    videos[0].serviceDate shouldBe LocalDate.of(2025, 1, 1)
                    videos[1].serviceDate shouldBe LocalDate.of(2025, 12, 31)
                    videos[2].serviceDate shouldBe LocalDate.of(2025, 3, 5)
                    videos[3].serviceDate shouldBe LocalDate.of(2025, 11, 9)
                }
            }

            it("should error on an unrecognized date format") {
                val file = testResourceFile("service-date/error-unrecognized-format.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeFailure()
            }

            it("should error if the date is missing") {
                val file = testResourceFile("service-date/error-missing-date.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeFailure()
            }

        }

        describe("category") {

            it("should be able to parse legal Video.Category values") {
                val file = testResourceFile("category/parse-legal-categories.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 4
                    videos[0].category shouldBe Video.Category.TsukinamiSai
                    videos[1].category shouldBe Video.Category.TsukinamiClosing
                    videos[2].category shouldBe Video.Category.OtherSai
                    videos[3].category shouldBe Video.Category.OtherClosing
                }
            }

            it("should error for an unknown category value") {
                val file = testResourceFile("category/error-unknown-category.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeFailure()
            }

            it("should error if there is no category") {
                val file = testResourceFile("category/error-missing-category.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeFailure()
            }

        }

        describe("isSkipped") {

            it("should parse a variety of inputs into a boolean") {
                val file = testResourceFile("is-skipped/parse-variety-of-inputs.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 8
                    videos[0].isSkipped shouldBe true  // Y
                    videos[1].isSkipped shouldBe true  // y
                    videos[2].isSkipped shouldBe true  // Yes
                    videos[3].isSkipped shouldBe true  // T
                    videos[4].isSkipped shouldBe false // N
                    videos[5].isSkipped shouldBe false // n
                    videos[6].isSkipped shouldBe false // No
                    videos[7].isSkipped shouldBe false // F
                }
            }

            it("should treat no value as false") {
                val file = testResourceFile("is-skipped/no-value-as-false.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeSuccess().also { videos ->
                    videos shouldHaveSize 1
                    videos[0].isSkipped shouldBe false
                }
            }

            it("should error on an illegal input") {
                val file = testResourceFile("is-skipped/error-illegal-input.csv")
                val context = VideosReader.Context(inputFile = file)

                val result = runBlocking { reader.read(context) }

                result.shouldBeFailure()
            }

        }

    }

}) {

    /**
     * Reads the CSV file from the test/resources/google-sheet-reader directory.
     */
    companion object {
        private fun testResourceFile(relativePath: String): Path {
            val resourcePath = "/google-sheet-reader/$relativePath"
            val url = GoogleSheetReaderTest::class.java.getResource(resourcePath)
                ?: error("Test resource not found: $resourcePath")
            return Paths.get(url.toURI())
        }
    }

}
