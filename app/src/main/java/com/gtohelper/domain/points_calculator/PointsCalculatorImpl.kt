package com.gtohelper.domain.points_calculator

import JsonParser
import com.gtohelper.domain.models.Competitor
import java.time.LocalTime
import java.util.SortedMap

class PointsCalculatorImpl(
    private val normJson: String
) : PointsCalculator {

    // TODO убрать?
    /*fun getPointForStringResult(competitor: Competitor, sport:String, resultString:String):Int{
        if (":" in resultString){
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss[.SSS]")
            return getPoint(competitor, sport, LocalTime.parse(resultString, formatter))
        }
        return getPoint(competitor, sport, resultString.toDouble())
    }*/

    private val dictionaryWithStandards by lazy {
        JsonParser().getDictionaryWithStandards(normJson)
    }

    override fun <T : Comparable<T>> getPoints(
        competitor: Competitor,
        sport: String,
        result: T,
    ): Int {

        if (!(result is Double || result is LocalTime)) {
            throw Exception("Only Double and LocalTime")
        }

        val officialSport = mapNormDisciplineToOfficialDisciplines[sport] ?: sport

        val degree = competitor.degree
        val gender = competitor.gender.string.lowercase()

        if (officialSport !in dictionaryWithStandards) {
            throw Exception("Sport is incorrectly specified")
        }

        val sportStandards = dictionaryWithStandards[officialSport] ?: return 0
        val sportsDegreeStandards = sportStandards[degree] ?: return 0
        val sportDegreeGenderStandards = sportsDegreeStandards[gender] ?: return 0

        val sortedNormative = sportDegreeGenderStandards.toSortedMap(
            compareBy { key -> (key as Comparable<*>?) })

        val maxNormative = sortedNormative.keys.toList().last() as T
        val minNormative = sortedNormative.keys.toList().first() as T
        val valuesToCompare = listOf(
            when (result) {
                is LocalTime -> 1
                else -> -1
            }, 0
        )

        if (result !in minNormative..maxNormative) {
            if (comparisonNormative(
                    maxNormative, result, listOf(
                        when {
                            maxNormative is LocalTime -> 1
                            else -> -1
                        }
                    )
                )
            ) {
                return 100
            }

            if (comparisonNormative(
                    maxNormative, result, listOf(
                        when {
                            maxNormative is LocalTime -> -1
                            else -> 1
                        }
                    )
                )
            ) {
                return 0
            }
        }

        return when (result) {
            is LocalTime -> findPoint(
                valuesToCompare = valuesToCompare,
                sortedNormative = sortedNormative,
                result = result,
                sortedNormativeKeys = sortedNormative.keys.toList()
            )

            else -> findPoint(
                valuesToCompare = valuesToCompare,
                sortedNormative = sortedNormative,
                result = result,
                sortedNormativeKeys = sortedNormative.keys.toList().reversed()
            )
        }
    }

    private fun <T : Comparable<T>> comparisonNormative(
        normative: T,
        result: T,
        valuesToCompare: List<Int>
    ): Boolean {
        return normative.compareTo(result) in valuesToCompare
    }

    private fun <T : Comparable<T>> findPoint(
        valuesToCompare: List<Int>,
        sortedNormative: SortedMap<Any, Int>,
        result: T,
        sortedNormativeKeys: List<Any>
    ): Int {

        for (normative in sortedNormativeKeys) {
            if (comparisonNormative(normative as T, result, valuesToCompare)) {
                return sortedNormative[normative] ?: 0
            }
        }

        return 100
    }
}

val mapNormDisciplineToOfficialDisciplines = mapOf(
    "Бег на 1000 м" to "Бег на 1000 м (мин, с)",
    "Бег на 1 км" to "Бег на 1000 м (мин, с)",
    "Бег на 1500 м" to "Бег на 1500 м (мин, с)",
    "Бег на 1.5 км" to "Бег на 1500 м (мин, с)",
    "Бег на 2000 м" to "Бег на 2000 м (мин, с)",
    "Бег на 2 км" to "Бег на 2000 м (мин, с)",
    "Бег на 3000 м" to "Бег на 3000 м (мин, с)",
    "Бег на 3 км" to "Бег на 3000 м (мин, с)",
    "Бег на 30 м" to "Бег на 30 м (с)",
    "Бег на 60 м" to "Бег на 60 м (с)",
    "Бег на 100 м" to "Бег на 100 м (с)",
    "Кросс на 2000 м по пересеченной местности" to "Кросс на 2000 м по пересеченной местности (мин, с)",
    "Кросс на 2000 м по пересечённой местности" to "Кросс на 2000 м по пересеченной местности (мин, с)",
    "Кросс на 3000 м по пересеченной местности" to "Кросс на 3000 м по пересеченной местности (мин, с)",
    "Кросс на 3000 м по пересечённой местности" to "Кросс на 3000 м по пересеченной местности (мин, с)",
    "Кросс на 5000 м по пересеченной местности" to "Кросс на 5000 м по пересеченной местности (мин, с)",
    "Кросс на 5000 м по пересечённой местности" to "Кросс на 5000 м по пересеченной местности (мин, с)",
    "Смешанное передвижение на 1000 м" to "Смешанное передвижение на 1000 м (мин, с)",
    "Смешанное передвижение на 2000 м" to "Смешанное передвижение на 2000 м (мин, с)",
    "Передвижение на 1000 м по пересеченной местности" to "Смешанное передвижение по пересеченной местности на 1000 м (мин, с)",
    "Передвижение на 2000 м по пересеченной местности" to "Смешанное передвижение по пересеченной местности на 2000 м (мин, с)",
    "Передвижение на 3000 м по пересеченной местности" to "Смешанное передвижение по пересеченной местности на 3000 м (мин, с)",
    "Челночный бег 3х10 м" to "Челночный бег 3х10 м (с)",
    "Скандинавская ходьба на 3000 м" to "Скандинавская ходьба на 3000 м (мин, с)",
    "Бег на лыжах на 1000 м" to "Бег на лыжах на 1000 м (мин, с)",
    "Бег на лыжах на 2000 м" to "Бег на лыжах на 2000 м (мин, с)",
    "Бег на лыжах на 3000 м" to "Бег на лыжах на 3000 м (мин, с)",
    "Бег на лыжах на 5000 м" to "Бег на лыжах на 5000 м (мин, с)",
    "Передвижение на лыжах на 2000 м" to "Передвижение на лыжах на 2000 м (мин, с)",
    "Передвижение на лыжах на 3000 м" to "Передвижение на лыжах на 3000 м (мин, с)",
    "Подтягивание из виса на высокой перекладине" to "Подтягивание из виса на высокой перекладине (количество раз)",
    "Подтягивание из виса лежа на низкой перекладине" to "Подтягивание из виса лежа на низкой перекладине 90 см (количество раз)",
    "Сгибание и разгибание рук в упоре о сиденье стула" to "Сгибание и разгибание рук в упоре о сиденье стула (количество раз)",
    "Сгибание и разгибание рук в упоре о скамью" to "Сгибание и разгибание рук в упоре о гимнастическую скамью (количество раз)",
    "Сгибание и разгибание рук в упоре лежа на полу" to "Сгибание и разгибание рук в упоре лежа на полу (количество раз)",
    "Наклон вперед" to "Наклон вперед из положения стоя на гимнастической скамье (от уровня скамьи - см)",
    "Прыжок в длину с места" to "Прыжок в длину с места толчком двумя ногами (см)",
    "Поднимание туловища из положения лежа на спине" to "Поднимание туловища из положения лежа на спине (количество раз за 1 мин)",
    "Плавание 25 м" to "Плавание 25 м (мин, с)",
    "Плавание 50 м" to "Плавание 50 м (мин, с)",
    "Стрельба из винтовки с открытым прицелом" to "Стрельба из положения сидя или стоя с опорой локтей о стол или стойку, дистанция –10 м (очки): из пневматической винтовки с открытым прицелом",
    "Стрельба из винтовки с диоптрическим прицелом" to "Стрельба из положения сидя или стоя с опорой локтей о стол или стойку, дистанция –10 м (очки): из пневматической винтовки с диоптрическим прицелом, либо «электронного оружия»",
    "Рывок гири" to "Рывок гири 16 кг (количество раз)",
    "Метание мяча весом 150 г" to "Метание мяча весом 150 г (м)",
    "Метание спортивного снаряда весом 500 г" to "Метание спортивного снаряда весом 500 г (м)",
    "Метание спортивного снаряда весом 700 г" to "Метание спортивного снаряда весом 700 г (м)",
    "Метание мяча в цель с дистанции 6 м" to "Метание мяча в цель, дистанция 6 м (кол-во попаданий)",
)