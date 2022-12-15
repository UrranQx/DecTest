@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package pName

import java.lang.NumberFormatException

//Налоги 2
/**
 * Во входной строке `taxes` содержится информация о прогрессивном
 * налогообложении для физических лиц в зависимости от их годового
 * дохода в следующем формате:
 *
 * 20_000$ = 0%, 40_000$ = 5%, 60_000$ = 10%, other = 25%
 *
 * Данный список значит, что доход, полученный человеком
 * облагается налогом по следующей формуле:
 * - доход 0..20_000$ облагается налогом 0%
 * - доход 20_001..40_000$ облагается налогом 5%
 * - доход 40_001..60_000$ облагается налогом 10%
 * - доход 60_001 и выше облагается налогом 25%
 *
 * Например, если человек заработал за год 100_000$, то
 * его налог будет составляться следующим образом:
 * 20000 * 0% (первые 20000 не облагаются налогом)
 * + 20000 * 5% (следующие 20000 облагаются налогом 5%)
 * + 20000 * 10% (следующие 20000 облагаются налогом 10%)
 * + 40000 * 25% (весь остальной доход облагается налогом 25%)
 * = 13000
 *сумма разбивается на 20_000 + (40_000 - 20_000) + (60_000 -  40_000) + (year_revenue - 60_000)
 * when(initailSum){
 *  in k[0],[1] -> ans+= процент*сумму
 *  }
 * Все границы дохода во входной строке расположены в
 * порядке возрастания.
 *
 * Также на вход подается число `money` - заработанная человеком
 * за год сумма. Вам необходимо посчитать какой процент от своего
 * дохода человеку придется заплатить в качестве налога. Для
 * приведенного примера ответ составит 13%: сумма налога 13000
 * составляет 13% от общего дохода 100000.
 * При нарушении формата входных данных следует выбросить
 * IllegalArgumentException.
 *
 * Имя функции и тип результата функции предложить самостоятельно;
 * в задании указан тип Any, то есть объект произвольного типа,
 * можно (и нужно) изменить его на более подходящий тип.
 *
 * Кроме функции, следует написать тесты,
 * подтверждающие её работоспособность.
 */

fun feeRatio(taxes: String, money: Int): Double {
    if (!taxes.matches(Regex("""(\d+\$\s=\s\d+%, )+(([А-яA-z]+\s=\s\d+%)$)?"""))) {
        throw IllegalArgumentException()
    }
    val prices = taxes.split(", ")
    /*val differences = mutableListOf<Int>()*/
    //val sumToPercent = mutableMapOf<String, Int>()
    val sums = mutableListOf(0)
    val percents = mutableListOf(0)
    //var initialSum = 0
    for (i in prices) {
        //i - строка формата сумма$ = \d+%
        // распарсим
        val str = i.split(Regex("""(\$?\s=\s)"""))
        //println(str)
        //sumToPercent[str[0]] = str[1].filter { it.isDigit() }.toInt()

        sums.add(str[0].toIntOrNull() ?: money)// Последнее число может быть меньше пред ласта
        percents.add(str[1].filter { it.isDigit() }.toInt())
    }

    var ans = 0
    var tempM = money
    for (i in 1 until sums.size) {
        if (tempM > (sums[i] - sums[i - 1])) {
            //плати налог
            ans += (sums[i] - sums[i - 1]) * percents[i] / 100
            tempM -= (sums[i] - sums[i - 1])
        } else {
            ans += (tempM) * percents[i] / 100
            //tempM = 0
            break //без брейка сработает на след итерации if т.к там ласт число << пре ласта (я сам так сделал)
        }
    }
    //println(ans)
    //return "${ans*100/money}"
    return ans.toDouble() * 100 / money
}

/**
 * задается таблица с процентами на налог:
 * map = [Банковское дело = 4, Горнодобывающая промышленность = 15, Образование = 10]
 * также задается строка с предприятиями в формате
 * Название - отрасль - прибль
 * Поликек - Образование - 10000
 * ООО Горняк - Горнодобывающая промышленность - 1000000000
 * Бебра - Торговля - 5000
 * Для неверного формата вывести ошибку NumberFormatException()
 * Для каждого предприятия найти налог, если он не задан в таблице - default = 13%
 * Вывести список предприятий отсортированный по налогу...
 * название фунции и тесты придумать самостоятельно
 * Collection<Any> поменять на тот тип, который удобнее или че то такое
 *
 */
fun taxesFirst(table: Map<String, Int>, text: String): Collection<Any> {
    val input = text.split("\n")
    //println(input)
    val ans = mutableMapOf<String, Int>()
    for (line in input) {
        //println(line)
        if (!line.matches(Regex("""([A-яA-z]\s?)+\s-\s([A-яA-z]\s?)+\s-\s\d+$"""))) {
            throw NumberFormatException()
        }
        val factory = line.split(" - ")
        val name = factory[0]
        val sphere = factory[1]
        val revenue = factory[2].toInt()
        ans[name] = revenue * (table[sphere] ?: 13) / 100


    }
    val out = mutableListOf<String>()
    val tempNames = ans.keys.toMutableSet()
    val tempFins = ans.values.toList().sorted()
    for (i in tempFins) {
        for (key in tempNames) {
            if (ans[key] == i) {
                out.add(key)
                ans.remove(key, i)
            }
        }

    }
    return out.reversed()
}
