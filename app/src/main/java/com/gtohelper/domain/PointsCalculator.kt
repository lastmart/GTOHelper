package com.gtohelper.domain

import JsonParser
import com.gtohelper.domain.models.Competitor
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.SortedMap

class PointsCalculator {
    fun getPointForStringResult(competitor: Competitor, sport:String, resultString:String):Int{
        if (":" in resultString){
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss[.SSS]")
            return getPoint(competitor, sport, LocalTime.parse(resultString, formatter))
        }
        return getPoint(competitor, sport, resultString.toDouble())
    }

    fun <T: Comparable<T>> getPoint(competitor: Competitor, sport:String, result:T):Int{
        if (!(result is Double || result is LocalTime)){
            throw Exception("Only Double and LocalTime")
        }
        val dictionaryWithStandards = JsonParser().getDictionaryWithStandards()
        if (sport !in dictionaryWithStandards){
            throw Exception("Sport is incorrectly specified")
        }
        val sortedNormative = dictionaryWithStandards[sport]!![competitor.degree]!![competitor.gender]!!.toSortedMap(compareBy { key -> (key as Comparable<*>?)})
        val maxNormative = sortedNormative.keys.toList().last() as T
        val minNormative = sortedNormative.keys.toList()[0] as T
        val valuesToCompare = listOf(when{
            result is LocalTime -> 1
            else -> -1 }, 0)
        if (result !in minNormative..maxNormative)
        {
            if (comparisonNormative(maxNormative, result, listOf(when{
                maxNormative is LocalTime -> 1  else -> -1})))
            {
                return 100
            }
            if (comparisonNormative(maxNormative, result, listOf(when{
                    maxNormative is LocalTime -> -1  else -> 1}))){
                return 0
            }
        }
        return when{
            result is LocalTime -> findPoint(valuesToCompare, sortedNormative, result,
                sortedNormative.keys.toList())
            else -> findPoint(valuesToCompare, sortedNormative, result,
                sortedNormative.keys.toList().reversed())
        }
    }

    private fun <T: Comparable<T>> comparisonNormative (normative:T, result: T, valuesToCompare:List<Int>): Boolean{
        if (normative.compareTo(result) in valuesToCompare){
            return true
        }
        return false
    }

    private fun <T: Comparable<T>> findPoint(valuesToCompare: List<Int>, sortedNormative: SortedMap<Any, Int>,
                          result: T, sortedNormativeKeys: List<Any>): Int{
        for (normative in sortedNormativeKeys)
        {
            if (comparisonNormative(normative as T, result, valuesToCompare)){
                return sortedNormative[normative]!!.toInt()
            }
        }
        return 100
    }
}
