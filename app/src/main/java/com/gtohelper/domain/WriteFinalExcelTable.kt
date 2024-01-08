package com.gtohelper.domain

import JsonParser
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun main(){
    val maleSevenDegree = Competitor("Глеб Москалев",
        Gender.MALE.string, "Абакан", 12, 7)
    val maleEightDegree = Competitor("Егор Мартынов",
        Gender.MALE.string, "Михайловск", 13, 8)
    val maleNineDegree = Competitor("Владимир Бойко",
        Gender.MALE.string, "Челябинск", 14, 9)
    val maleTenDegree = Competitor("Иван Федоров",
        Gender.MALE.string, "Санкт-Петербург", 16, 10)
    val femaleTwoDegree = Competitor("Маша Иванова", Gender.FEMALE.string,
        "Школа 12", 5, 2)


    val jsonParser = JsonParser()

    val res = mapOf(
        "Бег на 3000 м (мин, с)" to mapOf(
            maleSevenDegree to jsonParser.convertTime("12:34.55"),
            maleEightDegree to jsonParser.convertTime("13:17.44"),
            maleNineDegree to jsonParser.convertTime("13:56.32"),
            maleTenDegree to jsonParser.convertTime("09:50.40")
        ),
        "Бег на 30 м (с)" to mapOf(
            femaleTwoDegree to jsonParser.convertRunTime("6.6")
        ),
        "Бег на 60 м (с)" to mapOf(
            maleSevenDegree to jsonParser.convertRunTime("6.5"),
            maleEightDegree to jsonParser.convertRunTime("7.3"),
            maleNineDegree to jsonParser.convertRunTime("6.4"),
            maleTenDegree to jsonParser.convertRunTime("8.5"),
        ),
        "Поднимание туловища из положения лежа на спине (количество раз за 1 мин)" to mapOf(
            maleSevenDegree to 56.0,
            maleEightDegree to 66.0,
            maleNineDegree to 69.0,
            maleTenDegree to 77.0,
            femaleTwoDegree to 35.0
        )
    )
    val filePath = "/Users/glebmoskalev/Downloads/newtable.xlsx"
    val listCompetitor = listOf(maleSevenDegree, maleEightDegree, maleTenDegree, maleNineDegree,
        femaleTwoDegree)

    val s: MutableMap<Competitor, MutableMap<String, MutableMap<Any, Int>>> = mutableMapOf()
    s[maleEightDegree] = mutableMapOf("подтягивание" to mutableMapOf(35.0 to 12))
    WriteFinalExcelTable().createFinalTable(filePath, "Соревнования по ГТО", listCompetitor, res)
}

class WriteFinalExcelTable {
    fun createFinalTable(filePath:String, nameTable: String, listCompetitor:List<Competitor>,
                         sportResults: Map<String, Map<Competitor, Any>>,){
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Результаты")
        val dictSportColumn: MutableMap<String, Int> = mutableMapOf()

        val (dictCompetitorSportResPoint, sortedDictCompetitorForSumPoints) = getTwoDictionary(sportResults,
            listCompetitor)


        addCellName(workbook, sheet, nameTable,  sportResults ,dictSportColumn)
        addCompetitorResult(workbook, sheet, sortedDictCompetitorForSumPoints, dictCompetitorSportResPoint,
            dictSportColumn)

        val fileOut = FileOutputStream(filePath)
        workbook.write(fileOut)
        fileOut.close()
    }
    private fun getTwoDictionary
                (sportResults: Map<String, Map<Competitor, Any>>,
                 listCompetitor: List<Competitor>):
            Pair< MutableMap<Competitor, MutableMap<String, MutableMap<Any, Int>>>,
                    Map<Competitor,  Double>> {
        val dictCompetitorSportResPoint:
                MutableMap<Competitor, MutableMap<String, MutableMap<Any, Int>>> = mutableMapOf()

        val competitorSummaPoint: MutableMap<Competitor,  Double> = mutableMapOf()
        for (competitor in listCompetitor) {
            var summaPoints = 0
            for (sport in sportResults.keys) {
                if (competitor in sportResults[sport]!!.keys) {
                    val res = sportResults[sport]!![competitor]
                    val point = PointsCalculator().getPoint(competitor, sport, res as Comparable<Any>)
                    if (competitor !in dictCompetitorSportResPoint){
                        dictCompetitorSportResPoint[competitor] =
                            mutableMapOf(sport to mutableMapOf(res to point))
                    }
                    else{
                        dictCompetitorSportResPoint[competitor]!![sport] = mutableMapOf(res to point)
                    }
                    summaPoints += point
                }
            }
            competitorSummaPoint[competitor] = summaPoints.toDouble()
        }
        val sortedCompetitorSumPoints = competitorSummaPoint.toList().sortedByDescending { (_, value) -> value }.toMap()
        return Pair(dictCompetitorSportResPoint, sortedCompetitorSumPoints)
    }

    private fun addCompetitorResult(workBook:XSSFWorkbook, sheet: XSSFSheet,
                                    sortedCompetitorForSumPoint:Map<Competitor,  Double>,
                                    dictCompetitorSportResPoint:MutableMap<Competitor,
                                             MutableMap<String, MutableMap<Any, Int>>>,
                                    dictSportColumn: MutableMap<String, Int>){
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

        val lastColumnSport = dictSportColumn.values.max()
        var rowCompetitor = 4

        for (competitor in sortedCompetitorForSumPoint.keys){
            val cellId = sheet.createRow(rowCompetitor).createCell(0)
            cellId.cellStyle = style
            cellId.setCellValue(competitor.participantNumber.toDouble())

            val cellName = sheet.getRow(rowCompetitor).createCell(1)
            cellName.cellStyle = style
            cellName.setCellValue(competitor.nameCompetitor)
            sheet.autoSizeColumn(1)

            val cellGender = sheet.getRow(rowCompetitor).createCell(2)
            cellGender.cellStyle = style
            cellGender.setCellValue(if (competitor.gender == "male") "М" else "Ж")

            val cellDegree = sheet.getRow(rowCompetitor).createCell(3)
            cellDegree.cellStyle = style
            cellDegree.setCellValue(competitor.degree.toDouble())

            val cellNameTeam = sheet.getRow(rowCompetitor).createCell(4)
            cellNameTeam.cellStyle = style
            cellNameTeam.setCellValue(competitor.nameTeam)
            sheet.autoSizeColumn(4)
            for (sport in dictCompetitorSportResPoint[competitor]!!.keys){
                val res = dictCompetitorSportResPoint[competitor]!![sport]!!.keys.toList()[0]
                val point = dictCompetitorSportResPoint[competitor]!![sport]!![res]!!.toDouble()

                val cellRes = sheet.getRow(rowCompetitor).createCell(dictSportColumn[sport]!!)
                cellRes.cellStyle = style

                if (res is LocalTime){
                    if (sport == "Бег на 30 м (с)" || sport == "Челночный бег 3х10 м (с)"
                        || sport == "Бег на 60 м (с)" || sport == "Бег на 100 м (с)")
                    {
                        val formatter = DateTimeFormatter.ofPattern("ss.SS")
                        cellRes.setCellValue(res.format(formatter).toDouble())
                    }
                    else
                    {
                        val formatter = DateTimeFormatter.ofPattern("mm:ss.SS")
                        cellRes.setCellValue(res.format(formatter).toString())
                    }
                }
                else if (res is Double){
                    cellRes.setCellValue(res)
                }

                val cellPoint =  sheet.getRow(rowCompetitor).createCell(dictSportColumn[sport]!! + 1)
                cellPoint.cellStyle = stylePoint
                cellPoint.setCellValue(point)

            }
            val cellSummaPoint = sheet.getRow(rowCompetitor).createCell(lastColumnSport + 2)
            cellSummaPoint.cellStyle = styleSumPoint
            cellSummaPoint.setCellValue(sortedCompetitorForSumPoint[competitor]!!.toDouble())

            rowCompetitor += 1
        }

    }

    private fun addCellName(workbook:XSSFWorkbook ,sheet: XSSFSheet, nameTable: String,
                    sportResults: Map<String, Map<Competitor, Any>>, dictSportColumn: MutableMap<String, Int>){
        // Add cell: id, name competitor, degree, name team, sports, summa point, place

        val style = workbook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        style.verticalAlignment = VerticalAlignment.CENTER

        val cellId = sheet.createRow(1).createCell(0)
        cellId.cellStyle = style
        cellId.setCellValue("№ участника")
        sheet.autoSizeColumn(0)
        sheet.addMergedRegion(CellRangeAddress(1,3, 0, 0))

        val cellNameCompetitor = sheet.getRow(1).createCell(1)
        cellNameCompetitor.cellStyle = style
        cellNameCompetitor.setCellValue("ФИО")
        sheet.addMergedRegion(CellRangeAddress(1,3, 1, 1))

        val cellGender = sheet.getRow(1).createCell(2)
        cellGender.cellStyle = style
        cellGender.setCellValue("Пол")
        sheet.addMergedRegion(CellRangeAddress(1,3, 2, 2))

        val cellDegree = sheet.getRow(1).createCell(3)
        cellDegree.cellStyle = style
        cellDegree.setCellValue("Ступень")
        sheet.addMergedRegion(CellRangeAddress(1,3, 3,  3))

        val cellNameTeam = sheet.getRow(1).createCell(4)
        cellNameTeam.cellStyle = style
        cellNameTeam.setCellValue("Команда")
        sheet.addMergedRegion(CellRangeAddress(1,3, 4,  4))

        style.wrapText = true
        val columnFirstSport = 5
        for (numSport in 0 until sportResults.keys.size){
            dictSportColumn[sportResults.keys.toList()[numSport]] = columnFirstSport + numSport * 2
            val sportCell = sheet.getRow(1).createCell(columnFirstSport + numSport * 2)
            sportCell.cellStyle = style
            sportCell.setCellValue(sportResults.keys.toList()[numSport])

            sheet.autoSizeColumn(columnFirstSport + numSport * 2)
            val widthColumn = (sheet.getColumnWidth(columnFirstSport + numSport * 2) / 2.0).toInt()

            sheet.addMergedRegion(CellRangeAddress(1,2, columnFirstSport + numSport * 2,
                columnFirstSport + numSport * 2 + 1))

            sheet.setColumnWidth(columnFirstSport + numSport * 2, widthColumn)
            sheet.setColumnWidth(columnFirstSport + numSport * 2 + 1, widthColumn)
            var resWidth = 0
            if (numSport == 0){
                val cellRes = sheet.createRow(3).createCell(columnFirstSport)
                cellRes.cellStyle = style
                cellRes.setCellValue("Результат")
                sheet.autoSizeColumn(columnFirstSport)
                resWidth = sheet.getColumnWidth(columnFirstSport)
                val cellPoint = sheet.getRow(3).createCell(columnFirstSport + 1)
                cellPoint.cellStyle = style
                cellPoint.setCellValue("Очки")
            }
            else{
                val cellRes = sheet.getRow(3).createCell(columnFirstSport + numSport * 2)
                cellRes.cellStyle = style
                cellRes.setCellValue("Результат")
                sheet.autoSizeColumn(columnFirstSport + numSport * 2)
                resWidth = sheet.getColumnWidth(columnFirstSport + numSport * 2)
                val cellPoint = sheet.getRow(3).createCell(columnFirstSport + numSport * 2 + 1)
                cellPoint.cellStyle = style
                cellPoint.setCellValue("Очки")
            }
            if (widthColumn > resWidth){
                sheet.setColumnWidth(columnFirstSport + numSport * 2, widthColumn)
            }
        }

        val columnBeforeSport = columnFirstSport + sportResults.keys.size * 2
        val cellSumPoint = sheet.getRow(1).createCell(columnBeforeSport)
        cellSumPoint.cellStyle = style
        cellSumPoint.setCellValue("Сумма")
        sheet.addMergedRegion(CellRangeAddress(1,3, columnBeforeSport,
            columnBeforeSport))


        val cellNameCompetition = sheet.createRow(0).createCell(0)
        cellNameCompetition.cellStyle = style
        cellNameCompetition.setCellValue(nameTable)
        sheet.addMergedRegion(CellRangeAddress(0,0, 0,
            columnBeforeSport))
    }
}