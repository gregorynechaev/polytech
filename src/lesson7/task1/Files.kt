@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import ru.spbstu.wheels.NullableMonad.map
import ru.spbstu.wheels.out
import ru.spbstu.wheels.toMap
import java.io.File
import java.lang.StringBuilder

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
        } else if (line[0] != '_') {
            writer.write(line)
            writer.newLine()
        }
    }
    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    var s = ""
    var str = ""
    var count = 0
    val list = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        str = line.lowercase()
        list.add(str)
    }
    for (word in substrings) {
        count = 0
        for (line in list) {
            str = line
            for (a in 0 until str.length) {
                s = ""
                for (b in a until str.length) {
                    s += str[b]
                    if (s == word.lowercase()) count++
                }
            }
            res[word] = count
        }
    }
    return res.toMap()
}


/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var previous = ' '
    for (line in File(inputName).readLines()) {
        previous = ' '
        for (i in line) {
            if (previous == 'ж' || previous == 'ч' || previous == 'ш' || previous == 'щ') {
                if (i.lowercaseChar() == i) {
                    when (i) {
                        'ы' -> writer.write("и")
                        'я' -> writer.write("а")
                        'ю' -> writer.write("у")
                        else -> writer.write(i.toString().lowercase())
                    }
                } else {
                    when (i) {
                        'Ы' -> writer.write("И")
                        'Я' -> writer.write("А")
                        'Ю' -> writer.write("У")
                        else -> writer.write(i.toString())
                    }
                }

            } else writer.write(i.toString())

            previous = i.lowercaseChar()
        }
        writer.newLine()

    }
    writer.close()
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    TODO()
}
//{
//    File(outputName).bufferedWriter().use {
//        var maxLength = 0
//        File(inputName).forEachLine { line -> maxLength = maxOf(maxLength, line.trim().length) }
//        File(inputName).useLines { lineSequence ->
//            var spaces: String
//            for (line in lineSequence) {
//                spaces = " ".repeat((maxLength - line.trim().length) / 2)
//                it.write("$spaces${line.trim()}\n")
//            }
//        }
//    }
//}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var maxLen = Int.MIN_VALUE
    var spaces = 0
    var sp = 0
    var ost = 0
    var lineee = ""
    for (line in File(inputName).readLines()) {
        lineee = line
        while ("  " in lineee) {
            lineee = lineee.replace("  ", " ")
        }
        val splited = lineee.split(" ")
        val newSplit = splited.filter { it != "" }
        val lineLength = newSplit.sumOf { it.length } + (newSplit.size - 1)
        maxLen = maxOf(lineLength, maxLen)
    }
    for (line in File(inputName).readLines()) {
        lineee = line
        while ("  " in lineee) {
            lineee = lineee.replace("  ", " ")
        }
        val splited = lineee.split(" ")
        val newSplit = splited.filter { it != "" }
        val count = newSplit.sumOf { it.length }
        spaces = maxLen - count
        if (line.length == maxLen) {
            writer.write(line)
        } else {
            if (newSplit.size == 0) {
                writer.write("")
            } else if (newSplit.size == 1) {
                writer.write(newSplit[0])
            } else {
                sp = spaces / (newSplit.size - 1)
                ost = spaces % (newSplit.size - 1)
                for (i in 0..newSplit.size - 2) {
                    writer.write(newSplit[i])
                    if (i + 1 <= ost) {
                        writer.write(" ")
                    }
                    for (j in 1..sp) {
                        writer.write(" ")
                    }
                }
                writer.write(newSplit[newSplit.size - 1])
            }
        }
        writer.newLine()
    }
    writer.close()
}


/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()
//{
//    var res = mutableMapOf<String, Int>()
//    var result = mutableMapOf<String, Int>()
//    for (line in File(inputName).readLines()) {
//        var words = line.split(" ")
//        for (word in words) {
//            var countMap = countSubstrings(inputName, listOf(word))
//            res[word] = countMap[word] ?: 0
//        }
//    }
//    var count = 0
//    res.entries.sortedBy { it.value }
//    for ((key, value) in res) {
//        count++
//        if (count < 20) {
//            result[key] = value
//        }
//    }
//    return result.toMap()
//}

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}
//    val writer = File(outputName).bufferedWriter()
//    for (line in File(inputName).readLines()) {
//        for (a in line) {
//            if (dictionary.containsKey(a.lowercaseChar()) || dictionary.containsKey(a.uppercaseChar())) {
//                for ((key, value) in dictionary) {
//                    if (a.lowercaseChar() == key.lowercaseChar()) {
//                        if (Regex("""([А-Я A-Z])+[a-я]*""").matches(a.toString())) {
//                            writer.write(value[0].uppercase())
//                            for (i in 1 until value.length) {
//                                writer.write(value[i].lowercase())
//                            }
//                        } else {
//                            if (value == "") {
//                                writer.write("")
//                            } else writer.write(value.lowercase())
//                        }
//                    }
//                }
//            } else writer.write(a.toString())
//        }
//        writer.newLine()
//    }
//    writer.close()
//}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    writer.write("<html>\n<body>")
    var c = 0
    var iFlag = false
    var bFlag = false
    var sFlag = false
    val file = File(inputName).readLines()
    var ccc = ""
    for (line in file) {
        ccc += line + "\n"
    }
    ccc = ccc.replace("\n \n", "\n\n")
    ccc = ccc.replace("\n\t\n", "\n\n")
    while ("\n\n\n" in ccc) {
        ccc = ccc.replace("\n\n\n", "\n\n")
    }
    val list = ccc.split("\n\n")
    if (list.isNotEmpty()) {
        for (line in list) {
            if (line == list[0]) {
                if (!line.isEmpty()) {
                    writer.write("<p>")
                }
            } else if (line == list[list.size - 1]) {
                val aaa = line.replace(" ", "")
                if (aaa.isNotEmpty()) {
                    writer.write("<p>")
                }
            } else writer.write("<p>")

            var str = line.replace("**", "<b>")
            str = str.replace("*", "<i>")
            str = str.replace("~~", "<s>")
            var replaced = ""
            var str2 = ""
            val i = str.split("<i>")
            if (i.size > 1) {
                for (k in 0..i.size - 2) {
                    replaced += i[k]
                    if (iFlag) {
                        replaced += ("</i>")
                        iFlag = false
                    } else {
                        replaced += ("<i>")
                        iFlag = true
                    }
                    c = k + 1
                }
            } else replaced = str
            if (i.size > 1) {
                replaced += i[c]
            }
            val b = replaced.split("<b>")
            if (b.size > 1) {
                for (k in 0..b.size - 2) {
                    str2 += (b[k])
                    if (bFlag) {
                        str2 += ("</b>")
                        bFlag = false
                    } else {
                        str2 += ("<b>")
                        bFlag = true
                    }
                    c = k + 1
                }
            } else str2 = replaced
            if (b.size > 1) {
                str2 += b[c]
            }
            str2 = str2.replace("\t", "")
            val s = str2.split("<s>")
            if (s.size > 1) {
                for (k in 0..s.size - 2) {
                    writer.write(s[k])
                    if (sFlag) {
                        writer.write("</s>")
                        sFlag = false
                    } else {
                        writer.write("<s>")
                        sFlag = true
                    }
                    c = k + 1
                }
            } else {
                writer.write(str2)
            }
            if (s.size > 1) {
                writer.write(s[c])
            }
            if (line == list[0]) {
                if (line.isNotEmpty()) {
                    writer.write("</p>")
                }
            } else if (line == list[list.size - 1]) {
                val aaa = line.replace(" ", "")
                if (aaa.isNotEmpty()) {
                    writer.write("</p>")
                }
            } else writer.write("</p>")
        }
        writer.write("</body>\n</html>")
    } else writer.write("<p>\n</p>\n</body>\n</html>")
    writer.close()
}
//{
//    val writer = File(outputName).bufferedWriter()
//    var str = listOf<String>()
//    writer.write("<html>\n<body>\n<p>")
//    for (line in File(inputName).readLines()) {
//        if (line.isEmpty()) {
//            writer.write("</p>\n<p>")
//        } else {
//            str = line.split(" ")
//            for (i in str) {
//                var replaced = i
//                if (i.length > 2) {
//                    if (Regex("""(<[A-я]+>)*\*\*[A-я]*(</[A-я]+>)*,*.*""").matches(replaced)) replaced =
//                        replaced.replaceFirst("**", "<b>")
//                    if (Regex("""(<[A-я]+>)*\*[A-я]*(</[A-я]+>)*,*.*""").matches(replaced)) replaced =
//                        replaced.replaceFirst("*", "<i>")
//                    if (Regex("""(<[A-я]+>)*~~[A-я]*(</[A-я]+>)*,*.*""").matches(replaced)) replaced =
//                        replaced.replaceFirst("~~", "<s>")
//                    if (Regex("""(<[A-я]+>)*[A-я]*(</[A-я]+>)*\*\*,*.*""").matches(replaced)) replaced =
//                        replaced.replaceFirst("**", "</b>")
//                    if (Regex("""(<[A-я]+>)*[A-я]*(</[A-я]+>)*\*,*.*""").matches(replaced)) replaced =
//                        replaced.replaceFirst("*", "</i>")
//                    if (Regex("""(<[A-я]+>)*[A-я]*(</[A-я]+>)*~~,*.*""").matches(replaced)) replaced =
//                        replaced.replaceFirst("~~", "</s>")
//                }
//                writer.write("$replaced ")
//            }
//        }
//        writer.newLine()
//    }
//    writer.write("</p>\n</body>\n</html>")
//    writer.close()
//}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun ost(digitsRhv: Map<Int, Int>, minus: Int): Int {
    val fs = digitNumber(minus)
    var res = ""
    for ((key, value) in digitsRhv) {
        if (key <= fs) {
            res += value.toString()
        }
    }
    return (res.toInt() - minus)
}

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}
//{
//    var x = lhv
//    val writer = File(outputName).bufferedWriter()
//    val div = lhv / rhv
//    var y = div
//    val digitsLhv = mutableMapOf<Int, Int>()
//    val digitsDiv = mutableMapOf<Int, Int>()
//    var c = digitNumber(x)
//    while (x > 0) {
//        digitsLhv[c] = x % 10
//        c--
//        x /= 10
//    }
//    c = digitNumber(y)
//    while (y > 0) {
//        digitsDiv[c] = y % 10
//        c--
//        y /= 10
//    }
//    var newDigitsLhv = digitsLhv.entries.sortedBy { it.key }
//    var newDigitsDiv = digitsDiv.entries.sortedBy { it.key }
//    for ((key, value) in newDigitsDiv) {
//        var minus = rhv * value
//        var osttt = ost(newDigitsLhv.toMap(), minus)
//
//    }
//
//
//}


