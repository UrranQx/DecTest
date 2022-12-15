package pName

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Math.ulp
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
        assertEquals(13.0, feeRatio("20000$ = 0%, 40000$ = 5%, 60000$ = 10%, other = 25%", 100000))
        assertApproxEquals(3.3333333,
            feeRatio("20000$ = 0%, 40000$ = 5%, 60000$ = 10%, other = 25%", 45000),
            delta = 1e-5)



        assertThrows(IllegalArgumentException::class.java) {

            feeRatio(
                "20_000% = 0%, 40_000$ = 5%, 60_000$ = 10%, other = 25%",
                100000
            )
        }
    }

}