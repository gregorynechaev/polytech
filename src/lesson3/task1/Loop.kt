@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.*

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    if (n == 0) return 1
    var count = 0
    var i = n
    while (i != 0) {
        count += 1
        i /= 10
    }
    return count

}

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var pred = 1
    var predpred = 1
    var fibo = 0
    if (n in 1..2) {
        return 1
    }
    for (i in 3..n) {
        fibo = pred + predpred
        predpred = pred
        pred = fibo

    }

    return fibo
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var sqRoot = sqrt(n.toDouble())
    var result = n
//    while (del == 0) {
//        if (n % count == 0) {
//            break
//        } else count += 1
//    }
    for (i in 2..sqRoot.toInt()) {
        if (n % i == 0) {
            result = i
            break
        }

    }
    return result
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var del = 0
    var count = n - 1
    while (del == 0) {
        if (n % count == 0) {
            break
        } else count -= 1
    }
    return count
}


/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var count = 0
    var i = x
    while (i != 1) {
        when {
            i % 2 == 0 -> i /= 2
            else -> i = 3 * i + 1
        }
        count += 1
    }
    return count
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */

fun gcd(m: Int, n: Int): Int {
    var newM = m
    var newN = n
    while (newM != newN) {
        if (newN > newM) newN -= newM
        else newM -= newN
    }
    return newM
}

fun lcm(m: Int, n: Int): Int {
    var flag = true
    var max = 0
    var result = 0
    return m * n / gcd(m, n)
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */


fun isCoPrime(m: Int, n: Int): Boolean = gcd(m, n) == 1


/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var result = 0
    var g = n
    while (g > 0) {
        result = result * 10 + g % 10
        g /= 10

    }
    return result

}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var i = n
    var c = i % 10
    var flag = false
    while (i != 0) {
        if (c != i % 10) {
            flag = true
            break
        }
        i /= 10
    }
    return flag
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double = TODO()
//    var flag = true
//    var res = 0.0
//    var count = 1
//    var i = x
//    var sin = x
//    var fact = 1
//    var newEl = 0.0
//    while (flag) {
//        newEl = i * i.pow(2.0) / (fact + 1) / (fact + 2)
//        if (count % 2 == 0) {
//            sin -= newEl
//        } else {
//            sin += newEl
//        }
//        fact += 2
//        i = i * i.pow(2.0)
//        count++
//        if (newEl < eps) {
//            res = sin
//            flag = false
//        }
//
//    }
//    return res

    //}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double = TODO()

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var resultDigits = 0
    var digitNumber = 0
    var sqr = 0
    var iCount = 0
    var newI = 0
    var res = 0
    var difference = 0
    for (i in 1..n) {
        sqr = i * i
        digitNumber = digitNumber(sqr)
        resultDigits += digitNumber
        if (n <= resultDigits) {
            difference = resultDigits - n
            newI = i * i
            iCount = 0
            while (newI != 0) {
                if (iCount == difference) {
                    res = newI % 10
                }
                newI /= 10
                iCount++
            }
        }
    }

    return res
}

/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var resultDigits = 0
    var digitNumber = 0
    var sqr = 0
    var iCount = 0
    var newI = 0
    var res = 0
    var difference = 0
    for (i in 1..n) {
        sqr = fib(i)
        digitNumber = digitNumber(sqr)
        resultDigits += digitNumber
        if (n <= resultDigits) {
            difference = resultDigits - n
            newI = fib(i)
            iCount = 0
            while (newI != 0) {
                if (iCount == difference) {
                    res = newI % 10
                }
                newI /= 10
                iCount++
            }
        }
    }

    return res
}
