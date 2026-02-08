package org.shintoinari.memberarchivegenerator.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ChronoFormatterTest : DescribeSpec({

    describe("instantiation") {

        describe("invoke(DateTimeFormatter)") {

            it("should create a ChronoFormatter that uses the provided DateTimeFormatter") {
                val formatter = ChronoFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                val date = LocalDate.of(2024, 6, 15)

                val result = formatter.format(date)

                result shouldBe "2024-06-15"
            }

        }

        describe("invoke with lambda") {

            it("should create a ChronoFormatter that uses the provided lambda function") {
                val formatter = ChronoFormatter { temporal ->
                    DateTimeFormatter.ISO_LOCAL_DATE.format(temporal)
                }
                val date = LocalDate.of(2024, 6, 15)

                val result = formatter.format(date)

                result shouldBe "2024-06-15"
            }

        }

        describe("invoke with DateTimeFormatter and transform") {

            it("should create a ChronoFormatter that uses the formatter and applies the transform") {
                val formatter = ChronoFormatter(DateTimeFormatter.ISO_LOCAL_DATE) { "{$it}" }
                val date = LocalDate.of(2024, 6, 15)

                val result = formatter.format(date)

                result shouldBe "{2024-06-15}"
            }

        }

        describe("ofPattern(String)") {

            it("should create a ChronoFormatter from the provided pattern") {
                val formatter = ChronoFormatter.ofPattern("yyyy-MM-dd")
                val date = LocalDate.of(2024, 6, 15)

                val result = formatter.format(date)

                result shouldBe "2024-06-15"
            }

        }

        describe("ofPattern(String, transform)") {

            it("should create a ChronoFormatter from the pattern and apply the transform") {
                val formatter = ChronoFormatter.ofPattern("yyyy-MM-dd") { "{$it}" }
                val date = LocalDate.of(2024, 6, 15)

                val result = formatter.format(date)

                result shouldBe "{2024-06-15}"
            }

        }

    }

    describe("TemporalAccessor.format") {

        it("should format a LocalDate") {
            val date = LocalDate.of(2024, 6, 15)
            val formatter = ChronoFormatter(DateTimeFormatter.ISO_LOCAL_DATE)

            val result = date.format(formatter)

            result shouldBe "2024-06-15"
        }

        it("should format a LocalTime") {
            val time = LocalTime.of(14, 30, 45)
            val formatter = ChronoFormatter(DateTimeFormatter.ISO_LOCAL_TIME)

            val result = time.format(formatter)

            result shouldBe "14:30:45"
        }

        it("should format a LocalDateTime") {
            val dateTime = LocalDateTime.of(2024, 6, 15, 14, 30, 45)
            val formatter = ChronoFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            val result = dateTime.format(formatter)

            result shouldBe "2024-06-15T14:30:45"
        }

        it("should format an OffsetDateTime") {
            val offsetDateTime = OffsetDateTime.of(2024, 6, 15, 14, 30, 45, 0, ZoneOffset.UTC)
            val formatter = ChronoFormatter(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            val result = offsetDateTime.format(formatter)

            result shouldBe "2024-06-15T14:30:45Z"
        }

    }

    describe("ChronoFormatter.en") {

        it("should format a LocalDate in North American format") {
            val date = LocalDate.of(2024, 3, 7)

            val result = date.format(ChronoFormatter.en)

            result shouldBe "March 7, 2024"
        }

        it("should format a LocalDateTime in North American format") {
            val dateTime = LocalDateTime.of(2024, 3, 7, 14, 30, 45)

            val result = dateTime.format(ChronoFormatter.en)

            result shouldBe "March 7, 2024"
        }

        it("should format an OffsetDateTime in North American format") {
            val offsetDateTime = OffsetDateTime.of(2024, 3, 7, 14, 30, 45, 0, ZoneOffset.UTC)

            val result = offsetDateTime.format(ChronoFormatter.en)

            result shouldBe "March 7, 2024"
        }

    }

    describe("ChronoFormatter.jp") {

        it("should format a LocalDate in Japanese format with full-width numerics") {
            val date = LocalDate.of(2024, 3, 7)

            val result = date.format(ChronoFormatter.jp)

            result shouldBe "２０２４年３月７日"
        }

        it("should format a LocalDateTime in Japanese format with full-width numerics") {
            val dateTime = LocalDateTime.of(2024, 3, 7, 14, 30, 45)

            val result = dateTime.format(ChronoFormatter.jp)

            result shouldBe "２０２４年３月７日"
        }

        it("should format an OffsetDateTime in Japanese format with full-width numerics") {
            val offsetDateTime = OffsetDateTime.of(2024, 3, 7, 14, 30, 45, 0, ZoneOffset.UTC)

            val result = offsetDateTime.format(ChronoFormatter.jp)

            result shouldBe "２０２４年３月７日"
        }

    }

})
