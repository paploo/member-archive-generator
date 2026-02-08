package org.shintoinari.memberarchivegenerator.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class EnumTest : DescribeSpec({

    describe("valueOfCaseInsensitive") {

        it("should find an entry from PascalCase (exact match)") {
            val result = valueOfCaseInsensitive<TestCategory>("TsukinamiSai")
            result shouldBe TestCategory.TsukinamiSai
        }

        it("should find an entry from camelCase") {
            val result = valueOfCaseInsensitive<TestCategory>("tsukinamiSai")
            result shouldBe TestCategory.TsukinamiSai
        }

        it("should find an entry from lowercase") {
            val result = valueOfCaseInsensitive<TestCategory>("tsukinamisai")
            result shouldBe TestCategory.TsukinamiSai
        }

        it("should find an entry from UPPERCASE") {
            val result = valueOfCaseInsensitive<TestCategory>("TSUKINAMISAI")
            result shouldBe TestCategory.TsukinamiSai
        }

        it("should find an entry from MiXeDcAsE") {
            val result = valueOfCaseInsensitive<TestCategory>("tSuKiNaMiSaI")
            result shouldBe TestCategory.TsukinamiSai
        }

        it("should raise an exception for an unmatched entry") {
            shouldThrow<NoSuchElementException> {
                valueOfCaseInsensitive<TestCategory>("NonExistentValue")
            }
        }

    }

}) {

    internal enum class TestCategory {
        TsukinamiSai,
        TsukinamiClosing,
        OtherSai,
        OtherClosing;
    }

}
