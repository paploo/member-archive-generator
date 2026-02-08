package org.shintoinari.memberarchivegenerator.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class StringTest : DescribeSpec({

    describe("String.toFullWidthNumeric") {

        it("should convert a string of all the digits into their full width equivalents") {
            val result = "0123456789".toFullWidthNumeric()
            result shouldBe "０１２３４５６７８９"
        }

        it("should not modify non-numeric characters") {
            val result = "shinto 神道".toFullWidthNumeric()
            result shouldBe "shinto 神道"
        }

        it("should not modify full width numeric characters") {
            val result = "１２３４５".toFullWidthNumeric()
            result shouldBe "１２３４５"
        }

    }

})
