package com.gtohelper.data

import com.gtohelper.data.models.Competitor
import com.gtohelper.data.models.CompetitorResults
import kotlin.random.Random

class FakeCompetitors {
    companion object {
        private val firstNames = listOf("Иван", "Егор", "Глеб", "Владимир", "Дмитрий")
        private val lastNames = listOf("Федоров", "Мартынов", "Москалев", "Бойко", "Хрусталев")
        private val teams = listOf("ФИИТ", "Кроссфит", "Радиофак", "Психбольница")
        private val degrees = listOf("VI", "VII", "IV", "III", "X")
        private val genders = listOf("M", "F", "F", "H", "M")
        private val points = listOf(300, 223, 12, 98, 15)

        fun generateCompetitors(amount: Int): List<Competitor> {
            return (1..amount).map {
                val firstName = firstNames[Random.nextInt(firstNames.size)]
                val lastName = lastNames[Random.nextInt(lastNames.size)]
                val team = teams[Random.nextInt(teams.size)]
                val degree = degrees[Random.nextInt(degrees.size)]
                val gender = genders[Random.nextInt(genders.size)]
                Competitor(it, "$firstName $lastName", team, degree, gender)
            }
        }

        fun generateCompetitorsWithResults(amount: Int): List<CompetitorResults> {
            return generateCompetitors(amount).map { competitor ->
                with(competitor) {
                    val points = points[Random.nextInt(points.size)]
                    CompetitorResults(id, name, team, degree, gender, points)
                }
            }
        }
    }

}