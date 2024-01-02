package com.gtohelper.domain_test
import JsonParser
import com.gtohelper.domain.PointsCalculator
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import org.junit.Test
import org.junit.Assert.*

class CalculatingPointsTest {
    private val calculatingPoints = PointsCalculator()
    private val jsonParser = JsonParser()

    @Test
    fun TestValuesThatInTable(){
        val maleSevenDegree = Competitor("Майкл Фелпс",
            Gender.MALE.string, "Балтимор", 12, 7)

        assertEquals(84, calculatingPoints.getPoint(maleSevenDegree,
            "Плавание 50 м (мин, с)", jsonParser.convertTime("00:28.00")))
        assertEquals(1, calculatingPoints.getPoint(maleSevenDegree,
            "Бег на 60 м (с)", jsonParser.convertRunTime("11.1")))
        assertEquals(48, calculatingPoints.getPoint(maleSevenDegree,
            "Рывок гири 16 кг (количество раз)", 21.0))

        val femaleTwoDegree = Competitor("Майкл Фелпс",
            Gender.FEMALE.string, "Москва", 2, 2)

        assertEquals(100, calculatingPoints.getPoint(femaleTwoDegree,
            "Плавание 25 м (мин, с)", jsonParser.convertTime("01:00.00")))
        assertEquals(64, calculatingPoints.getPoint(femaleTwoDegree,
            "Челночный бег 3х10 м (с)", jsonParser.convertRunTime("8.7")))
        assertEquals(60, calculatingPoints.getPoint(femaleTwoDegree,
            "Метание мяча в цель, дистанция 6 м (кол-во попаданий)", 3.0))

        val maleEighteenDegree = Competitor("Майкл Фелпс",
            Gender.MALE.string, "Вашинтгон", 90, 18)

        assertEquals(95, calculatingPoints.getPoint(maleEighteenDegree,
            "Скандинавская ходьба на 3000 м (мин, с)", jsonParser.convertTime("23:42.00")))
        assertEquals(25, calculatingPoints.getPoint(maleEighteenDegree,
            "Сгибание и разгибание рук в упоре о сиденье стула (количество раз)", 1.0))
    }

    @Test
    fun TestValuesThatNotInTable(){
        val maleNineDegree = Competitor("Майкл Фелпс",
            Gender.MALE.string, "Балтимор", 12, 9)

        assertEquals(86, calculatingPoints.getPoint(maleNineDegree,
            "Бег на 60 м (с)", jsonParser.convertRunTime("7.01")))
        assertEquals(59, calculatingPoints.getPoint(maleNineDegree,
            "Поднимание туловища из положения лежа на спине (количество раз за 1 мин)",
            46.0))
        assertEquals(98, calculatingPoints.getPoint(maleNineDegree,
            "Кросс на 5000 м по пересеченной местности (мин, с)",
            jsonParser.convertTime("14:48.00")))
        assertEquals(100, calculatingPoints.getPoint(maleNineDegree,
            "Прыжок в длину с места толчком двумя ногами (см)",
            361.0))
        assertEquals(0, calculatingPoints.getPoint(maleNineDegree,
            "Бег на 3000 м (мин, с)", jsonParser.convertTime("16:50.10")))

        val femaleSeventeenDegree =  Competitor("Майкл Фелпс",
            Gender.FEMALE.string, "Москва", 2, 17)

        assertEquals(98, calculatingPoints.getPoint(femaleSeventeenDegree,
            "Скандинавская ходьба на 3000 м (мин, с)", jsonParser.convertTime("24:00.00")))
        assertEquals(100, calculatingPoints.getPoint(femaleSeventeenDegree,
            "Плавание 25 м (мин, с)", jsonParser.convertTime("00:39.00")))
        assertEquals(0, calculatingPoints.getPoint(femaleSeventeenDegree,
            "Наклон вперед из положения стоя на гимнастической скамье (от уровня скамьи - см)",
            -13.00))

        val maleFourDegree = Competitor("Майкл Фелпс",
            Gender.MALE.string, "Вашинтгон", 90, 4)

        assertEquals(18, calculatingPoints.getPoint(maleFourDegree,
            "Бег на 60 м (с)", jsonParser.convertRunTime("11.44")))
        assertEquals(100, calculatingPoints.getPoint(maleFourDegree,
            "Бег на 1500 м (мин, с)", jsonParser.convertTime("04:49.00")))
        assertEquals(0, calculatingPoints.getPoint(maleFourDegree,
            "Прыжок в длину с места толчком двумя ногами (см)", 122.0))
    }
}