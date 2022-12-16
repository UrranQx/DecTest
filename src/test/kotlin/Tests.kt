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
                """
                    |Поликек - Образование - 10000
                    |ООО Горняк - Горнодобывающая промышленность - 1000000000
                    |Бебра - Торговля - 5000""".trimMargin()
            )
        )


    }
    val input = listOf(
        "SuperCats: кот - 100000",
        "FastAndCheap: кот - 25000, собака - 30000, шиншилла - 5000",
        "Lux: кот - 1000000, собака - 1000000, крыса - 1000000, корова - 1000000, бегемот - 1000000"
    )
    val pets1 = listOf("кот", "собака")
    val pets2 = listOf("кот")
    val pets3 = listOf("бегемот")
    val mn1 = 20000000
    val mn2 = 25000
    val mn3 = 500000
    @Test
    fun zoo(){
        assertEquals(
            setOf("FastAndCheap", "Lux"),zoo(input,pets1,mn1)
            )
        assertEquals(
            setOf("FastAndCheap"),zoo(input,pets2,mn2)
        )
        assertEquals(
            setOf<String>(),zoo(input,pets3,mn3)
        )
    }

}