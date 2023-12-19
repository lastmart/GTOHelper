package com.gtohelper.domain

import JsonParser
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import org.apache.poi.xssf.usermodel.XSSFRichTextString
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.absolutePathString


//fun main(){
//
//    val time1 = "00:00:10.300"
//    val time2 = "00:00:14"
//
//    val maleSevenDegree = Competitor("Майкл Фелпс", 19,
//        Gender.MALE.string, "Балтимор", 12)
//
//    println(PointsCalculator().getPoint(maleSevenDegree,
//        "Плавание 50 м (мин, с)", JsonParser().convertTime("00:28.00")))
//    println(PointsCalculator().getPointForStringResult(maleSevenDegree,
//        "Плавание 50 м (мин, с)", "00:00:28"))
//}
