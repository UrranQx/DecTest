package pName

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.math.abs

class Tests {
    private fun approxEquals(expected: Double, actual: Double, delta: Double): Boolean =
        abs(expected - actual) <= delta

    private fun assertApproxEquals(expected: Double, actual: Double, delta: Double = Math.ulp(10.0)) {
        assertTrue(approxEquals(expected, actual, delta))
    }

    @Test
    fun feeRatio() {
        assertApproxEquals(
            13.0, feeRatio("20000$ = 0%, 40000$ = 5%, 60000$ = 10%, other = 25%", 100000),
            delta = 1e-4
        )
        assertApproxEquals(
            3.3333333,
            feeRatio("20000$ = 0%, 40000$ = 5%, 60000$ = 10%, other = 25%", 45000),
            delta = 1e-5
        )


        assertThrows(IllegalArgumentException::class.java) {
            feeRatio(
                "20000 = 0%, 400000 = 5%, 60000 = 10, other = 25%",
                100000
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            feeRatio(
                "20_000% = 0%, 40_000$ = 5%, 60_000$ = 10%, other = 25%",
                100000
            )
        }
    }

    @Test
    fun taxesFirst() {
        assertEquals(
            listOf("ООО Горняк", "Поликек", "Бебра"), taxesFirst(
                mapOf("Банковское дело" to 4, "Горнодобывающая промышленность" to 15, "Образование" to 10),
                "Поликек - Образование - 10000\nООО Горняк - Горнодобывающая промышленность - 1000000000\nБебра - Торговля - 5000"
            )
        )

    }

}