package com.gtohelper.domain

import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.points_calculator.PointsCalculator
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.OutputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun main() {
//    val maleSevenDegree = Competitor(0,"Глеб Москалев",0,
//        Gender.MALE, "Абакан", 12, 7)
//    val maleEightDegree = Competitor(0,"Егор Мартынов",0,
//        Gender.MALE, "Михайловск", 13, 8)
//    val maleNineDegree = Competitor(0,"Владимир Бойко",0,
//        Gender.MALE, "Челябинск", 14, 9)
//    val maleTenDegree = Competitor(0,"Иван Федоров",0,
//        Gender.MALE, "Санкт-Петербург", 16, 10)
//    val femaleTwoDegree = Competitor(0,"Маша Иванова", Gender.FEMALE,0,
//        "Школа 12", 5, 2)
//
//
//    val jsonParser = JsonParser()
//
//    val res = mapOf(
//        "Бег на 3000 м (мин, с)" to mapOf(
//            maleSevenDegree to jsonParser.convertTime("12:34.55"),
//            maleEightDegree to jsonParser.convertTime("13:17.44"),
//            maleNineDegree to jsonParser.convertTime("13:56.32"),
//            maleTenDegree to jsonParser.convertTime("09:50.40")
//        ),
//        "Бег на 30 м (с)" to mapOf(
//            femaleTwoDegree to jsonParser.convertRunTime("6.6")
//        ),
//        "Бег на 60 м (с)" to mapOf(
//            maleSevenDegree to jsonParser.convertRunTime("6.5"),
//            maleEightDegree to jsonParser.convertRunTime("7.3"),
//            maleNineDegree to jsonParser.convertRunTime("6.4"),
//            maleTenDegree to jsonParser.convertRunTime("8.5"),
//        ),
//        "Поднимание туловища из положения лежа на спине (количество раз за 1 мин)" to mapOf(
//            maleSevenDegree to 56.0,
//            maleEightDegree to 66.0,
//            maleNineDegree to 69.0,
//            maleTenDegree to 77.0,
//            femaleTwoDegree to 35.0
//        )
//    )
//    val filePath = "/Users/glebmoskalev/Downloads/newtable.xlsx"
//    val listCompetitor = listOf(maleSevenDegree, maleEightDegree, maleTenDegree, maleNineDegree,
//        femaleTwoDegree)
//
//    val s: MutableMap<Competitor, MutableMap<String, MutableMap<Any, Int>>> = mutableMapOf()
//    s[maleEightDegree] = mutableMapOf("подтягивание" to mutableMapOf(35.0 to 12))
//    WriteFinalExcelTable().createFinalTable(filePath, "Соревнования по ГТО", listCompetitor, res)
}

class FinalExcelTableWriter(
    private val pointsCalculator: PointsCalculator
) {

    fun createFinalTable(
        nameTable: String,
        fileOutputStream: OutputStream,
        listCompetitor: List<Competitor>, // Список участников из бд
        sportResults: Map<String, Map<Competitor, Any>> // Вид спорта -> Competitor -> Результат(double/LOcalTime)
    ) {

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Результаты")
        val dictSportColumn: MutableMap<String, Int> = mutableMapOf()

        val (dictCompetitorSportResPoint, sortedDictCompetitorForSumPoints) = getTwoDictionary(
            sportResults,
            listCompetitor
        )

        addCellName(
            workbook = workbook,
            sheet = sheet,
            nameTable = nameTable,
            sportResults = sportResults,
            dictSportColumn = dictSportColumn
        )

        addCompetitorResult(
            workBook = workbook,
            sheet = sheet,
            sortedCompetitorForSumPoint = sortedDictCompetitorForSumPoints,
            dictCompetitorSportResPoint = dictCompetitorSportResPoint,
            dictSportColumn = dictSportColumn
        )

        workbook.write(fileOutputStream)
    }

    private fun getTwoDictionary(
        sportToCompetitorToResults: Map<String, Map<Competitor, Any>>,
        listCompetitor: List<Competitor>

    ): Pair<MutableMap<Competitor, MutableMap<String, MutableMap<Any, Int>>>,
            Map<Competitor, Double>> {

        val dictCompetitorSportResPoint: MutableMap<Competitor, MutableMap<String, MutableMap<Any, Int>>> =
            mutableMapOf()

        val competitorTotalPoints: MutableMap<Competitor, Double> = mutableMapOf()

        for (competitor in listCompetitor) {

            var totalPoints = 0

            for (sport in sportToCompetitorToResults.keys) {

                val competitorsToResults = sportToCompetitorToResults[sport] ?: continue
                if (competitor !in competitorsToResults.keys) continue
                val competitorResults = competitorsToResults[competitor] ?: continue

                val point = when (competitorResults) {
                    is Double -> pointsCalculator.getPoints(
                        competitor = competitor,
                        sport = sport,
                        result = competitorResults,
                    )

                    is LocalTime -> pointsCalculator.getPoints(
                        competitor = competitor,
                        sport = sport,
                        result = competitorResults,
                    )

                    else -> 0
                }

                if (competitor in dictCompetitorSportResPoint) {

                    dictCompetitorSportResPoint[competitor]!![sport] = mutableMapOf(
                        competitorResults to point
                    )
                } else {
                    dictCompetitorSportResPoint[competitor] = mutableMapOf(
                        sport to mutableMapOf(competitorResults to point)
                    )
                }

                totalPoints += point
            }

            competitorTotalPoints[competitor] = totalPoints.toDouble()
        }

        val sortedCompetitorSumPoints = competitorTotalPoints
            .toList()
            .sortedByDescending { (_, value) ->
                value
            }
            .toMap()

        return Pair(dictCompetitorSportResPoint, sortedCompetitorSumPoints)
    }

    private fun addCompetitorResult(
        workBook: XSSFWorkbook, sheet: XSSFSheet,
        sortedCompetitorForSumPoint: Map<Competitor, Double>,
        dictCompetitorSportResPoint: MutableMap<Competitor,
                MutableMap<String, MutableMap<Any, Int>>>,
        dictSportColumn: MutableMap<String, Int>
    ) {
        val style = workBook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        style.verticalAlignment = VerticalAlignment.CENTER

        val stylePoint = workBook.createCellStyle()
        stylePoint.fillForegroundColor = IndexedColors.YELLOW.getIndex()
        stylePoint.fillPattern = FillPatternType.SOLID_FOREGROUND
        stylePoint.alignment = HorizontalAlignment.CENTER

        val styleSumPoint = workBook.createCellStyle()
        styleSumPoint.fillForegroundColor = IndexedColors.GREEN.getIndex()
        styleSumPoint.fillPattern = FillPatternType.SOLID_FOREGROUND
        styleSumPoint.alignment = HorizontalAlignment.CENTER

        val lastColumnSport = dictSportColumn.values.maxOrNull() ?: return

        var rowCompetitor = 4

        for (competitor in sortedCompetitorForSumPoint.keys) {

            val cellId = sheet.createRow(rowCompetitor).createCell(0)
            cellId.cellStyle = style
            cellId.setCellValue(competitor.number.toDouble())

            val cellName = sheet.getRow(rowCompetitor).createCell(1)
            cellName.cellStyle = style
            cellName.setCellValue(competitor.name)
            //        sheet.autoSizeColumn(1)

            val cellGender = sheet.getRow(rowCompetitor).createCell(2)
            cellGender.cellStyle = style
            cellGender.setCellValue(if (competitor.gender == Gender.MALE) "М" else "Ж")

            val cellDegree = sheet.getRow(rowCompetitor).createCell(3)
            cellDegree.cellStyle = style
            cellDegree.setCellValue(competitor.degree.toDouble())

            val cellNameTeam = sheet.getRow(rowCompetitor).createCell(4)
            cellNameTeam.cellStyle = style
            cellNameTeam.setCellValue(competitor.teamName)
            //         sheet.autoSizeColumn(4)
            val competitorToSportToResultToPoints =
                dictCompetitorSportResPoint[competitor]

            if (competitorToSportToResultToPoints == null) {
                rowCompetitor += 1
                continue
            }

            for (sport in competitorToSportToResultToPoints.keys) {

                val resultToPoints = competitorToSportToResultToPoints[sport] ?: continue

                val result = resultToPoints.keys.toList()[0]

                val point = resultToPoints[result]?.toDouble() ?: continue

                val cellRes = sheet.getRow(rowCompetitor).createCell(dictSportColumn[sport]!!)
                cellRes.cellStyle = style

                if (result is LocalTime) {
                    if (sport == "Бег на 30 м (с)" || sport == "Челночный бег 3х10 м (с)"
                        || sport == "Бег на 60 м (с)" || sport == "Бег на 100 м (с)"
                    ) {
                        val formatter = DateTimeFormatter.ofPattern("ss.SS")
                        cellRes.setCellValue(result.format(formatter).toDouble())
                    } else {
                        val formatter = DateTimeFormatter.ofPattern("mm:ss.SS")
                        cellRes.setCellValue(result.format(formatter).toString())
                    }
                } else if (result is Double) {
                    cellRes.setCellValue(result)
                }

                val cellPoint =
                    sheet.getRow(rowCompetitor).createCell(dictSportColumn[sport]!! + 1)

                cellPoint.cellStyle = stylePoint
                cellPoint.setCellValue(point)

            }
            val cellSummaPoint = sheet.getRow(rowCompetitor).createCell(lastColumnSport + 2)
            cellSummaPoint.cellStyle = styleSumPoint
            cellSummaPoint.setCellValue(sortedCompetitorForSumPoint[competitor]!!.toDouble())

            rowCompetitor += 1
        }
    }

    private fun addCellName(
        workbook: XSSFWorkbook,
        sheet: XSSFSheet,
        nameTable: String,
        sportResults: Map<String, Map<Competitor, Any>>,
        dictSportColumn: MutableMap<String, Int>
    ) {
        // Add cell: id, name competitor, degree, name team, sports, summa point, place

        val style = workbook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        style.verticalAlignment = VerticalAlignment.CENTER

        val cellId = sheet.createRow(1).createCell(0)
        cellId.cellStyle = style
        cellId.setCellValue("№ участника")
        //    sheet.autoSizeColumn(0)
        sheet.addMergedRegion(CellRangeAddress(1, 3, 0, 0))

        val cellNameCompetitor = sheet.getRow(1).createCell(1)
        cellNameCompetitor.cellStyle = style
        cellNameCompetitor.setCellValue("ФИО")
        sheet.addMergedRegion(CellRangeAddress(1, 3, 1, 1))

        val cellGender = sheet.getRow(1).createCell(2)
        cellGender.cellStyle = style
        cellGender.setCellValue("Пол")
        sheet.addMergedRegion(CellRangeAddress(1, 3, 2, 2))

        val cellDegree = sheet.getRow(1).createCell(3)
        cellDegree.cellStyle = style
        cellDegree.setCellValue("Ступень")
        sheet.addMergedRegion(CellRangeAddress(1, 3, 3, 3))

        val cellNameTeam = sheet.getRow(1).createCell(4)
        cellNameTeam.cellStyle = style
        cellNameTeam.setCellValue("Команда")
        sheet.addMergedRegion(CellRangeAddress(1, 3, 4, 4))

        style.wrapText = true

        val columnFirstSport = 5
        for (numSport in 0 until sportResults.keys.size) {

            dictSportColumn[sportResults.keys.toList()[numSport]] =
                columnFirstSport + numSport * 2
            val sportCell = sheet.getRow(1).createCell(columnFirstSport + numSport * 2)
            sportCell.cellStyle = style
            sportCell.setCellValue(sportResults.keys.toList()[numSport])

            //        sheet.autoSizeColumn(columnFirstSport + numSport * 2)

            val widthColumn = (sheet.getColumnWidth(columnFirstSport + numSport * 2) / 2.0)
                .toInt()

            sheet.addMergedRegion(
                CellRangeAddress(
                    1, 2, columnFirstSport + numSport * 2,
                    columnFirstSport + numSport * 2 + 1
                )
            )

            sheet.setColumnWidth(columnFirstSport + numSport * 2, widthColumn)
            sheet.setColumnWidth(columnFirstSport + numSport * 2 + 1, widthColumn)

            var resWidth = 0

            if (numSport == 0) {
                val cellRes = sheet.createRow(3).createCell(columnFirstSport)
                cellRes.cellStyle = style
                cellRes.setCellValue("Результат")
                //        sheet.autoSizeColumn(columnFirstSport)
                resWidth = sheet.getColumnWidth(columnFirstSport)
                val cellPoint = sheet.getRow(3).createCell(columnFirstSport + 1)
                cellPoint.cellStyle = style
                cellPoint.setCellValue("Очки")
            } else {
                val cellRes = sheet.getRow(3).createCell(columnFirstSport + numSport * 2)
                cellRes.cellStyle = style
                cellRes.setCellValue("Результат")
                //        sheet.autoSizeColumn(columnFirstSport + numSport * 2)
                resWidth = sheet.getColumnWidth(columnFirstSport + numSport * 2)
                val cellPoint = sheet.getRow(3).createCell(columnFirstSport + numSport * 2 + 1)
                cellPoint.cellStyle = style
                cellPoint.setCellValue("Очки")
            }
            if (widthColumn > resWidth) {
                sheet.setColumnWidth(columnFirstSport + numSport * 2, widthColumn)
            }
        }

        val columnBeforeSport = columnFirstSport + sportResults.keys.size * 2
        val cellSumPoint = sheet.getRow(1).createCell(columnBeforeSport)
        cellSumPoint.cellStyle = style
        cellSumPoint.setCellValue("Сумма")

        sheet.addMergedRegion(
            CellRangeAddress(
                1, 3, columnBeforeSport,
                columnBeforeSport
            )
        )

        val cellNameCompetition = sheet.createRow(0).createCell(0)
        cellNameCompetition.cellStyle = style
        cellNameCompetition.setCellValue(nameTable)
        sheet.addMergedRegion(
            CellRangeAddress(
                0, 0, 0,
                columnBeforeSport
            )
        )
    }
}