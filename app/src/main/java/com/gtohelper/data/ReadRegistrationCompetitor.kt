package com.gtohelper.data
import JsonParser
import com.gtohelper.domain.PointsCalculator
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun main(){
    val filePath = "/Users/glebmoskalev/Downloads/example — копия.xlsx"
    val readRegistrationCompetitor = ReadRegistrationCompetitor()
    val listCompetitor = readRegistrationCompetitor.getListCompetitor(filePath)
    val listColumnStartSport = mutableListOf(6, 8, 10)
    val listSport = mutableListOf("Плавание 50 м (мин, с)","Бег на 60 м (с)",
        "Наклон вперед из положения стоя на гимнастической скамье (от уровня скамьи - см)")

    readRegistrationCompetitor.ageChangeByStep(filePath, listCompetitor, 3)

    var numberColumnForSport = 6
    for (sport in listSport){
        readRegistrationCompetitor.addSport(sport, filePath, numberColumnForSport)
        numberColumnForSport += 2
    }
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertTime("00:28.01"),
        listCompetitor[0], listSport[0], listColumnStartSport[0])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertTime("00:29.10"),
        listCompetitor[1], listSport[0], listColumnStartSport[0])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertTime("00:30.10"),
        listCompetitor[2], listSport[0], listColumnStartSport[0])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertTime("00:31.10"),
        listCompetitor[3], listSport[0], listColumnStartSport[0])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertTime("00:34.10"),
        listCompetitor[4], listSport[0], listColumnStartSport[0])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertTime("00:31.10"),
        listCompetitor[5], listSport[0], listColumnStartSport[0])

    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertRunTime("11.1"),
        listCompetitor[0], listSport[1], listColumnStartSport[1])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertRunTime("10.0"),
        listCompetitor[1], listSport[1], listColumnStartSport[1])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertRunTime("9.2"),
        listCompetitor[2], listSport[1], listColumnStartSport[1])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertRunTime("7.54"),
        listCompetitor[3], listSport[1], listColumnStartSport[1])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertRunTime("6.34"),
        listCompetitor[4], listSport[1], listColumnStartSport[1])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, JsonParser().convertRunTime("7.54"),
        listCompetitor[5], listSport[1], listColumnStartSport[1])

    readRegistrationCompetitor.addNormativeAndPoint(filePath, 22.0, listCompetitor[0], listSport[2],
        listColumnStartSport[2])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, 20.0, listCompetitor[1], listSport[2],
        listColumnStartSport[2])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, 25.0, listCompetitor[2], listSport[2],
        listColumnStartSport[2])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, 17.0, listCompetitor[3], listSport[2],
        listColumnStartSport[2])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, 15.0, listCompetitor[4], listSport[2],
        listColumnStartSport[2])
    readRegistrationCompetitor.addNormativeAndPoint(filePath, 17.0, listCompetitor[5], listSport[2],
        listColumnStartSport[2])

    readRegistrationCompetitor.addCompetitionResults(filePath, 12, listColumnStartSport,
        6)
    readRegistrationCompetitor.addNameTable(filePath, "1 \"A\" Эстафета по кроссфиту",13)
}
class ReadRegistrationCompetitor {
    fun addNameTable(filePath: String, nameTable:String, lastColumn: Int)
    {
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(0)

        val numMergedRegions = sheet.numMergedRegions
        for (i in 0 until numMergedRegions) {
            val mergedRegion = sheet.getMergedRegion(i) // Получаем объединенную область
            if (mergedRegion.firstRow == 0 && mergedRegion.lastRow == 0)  {
                sheet.removeMergedRegion(i)
                break
            }
        }
        val style = workBook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        var nameRow = sheet.getRow(0)
        if (nameRow == null){
            nameRow = sheet.createRow(0)
        }
        var nameCell = nameRow.getCell(0)
        if (nameCell == null){
            nameCell = sheet.getRow(0).createCell(0)
        }
        nameCell.setCellValue(nameTable)
        nameCell.cellStyle = style
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0,lastColumn))
        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }
    fun addCompetitionResults(filePath: String, columnResult:Int, listColumnStartSport: List<Int>,
                              numbersCompetitor: Int){
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(0)

        val style = workBook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        style.verticalAlignment = VerticalAlignment.CENTER

        var sumCell = sheet.getRow(1).getCell(columnResult)
        if (sumCell == null){
            sumCell = sheet.getRow(1).createCell(columnResult)
        }
        sumCell.setCellValue("Сумма")
        sumCell.cellStyle = style
        sheet.addMergedRegion(CellRangeAddress(1, 3, columnResult,columnResult))

        val listResPoint = MutableList(numbersCompetitor) {0.0}
        for (columnStartSport in listColumnStartSport){
            var sumPoint = 0.0
            // The results are on the 4th line
            for (rowNumber in 0..sheet.lastRowNum - 4){
                val row = sheet.getRow(rowNumber + 4) ?: continue
                val cellPoint = row.getCell(columnStartSport + 1)
                if (cellPoint != null && cellPoint.cellType == CellType.NUMERIC){
                    sumPoint += cellPoint.numericCellValue
                    listResPoint[rowNumber] += cellPoint.numericCellValue
                }
            }
        }
        val styleSumPoint = workBook.createCellStyle()
        styleSumPoint.fillForegroundColor = IndexedColors.GREEN.getIndex()
        styleSumPoint.fillPattern = FillPatternType.SOLID_FOREGROUND
        styleSumPoint.alignment = HorizontalAlignment.CENTER

        for (rowNumber in 0 until  numbersCompetitor){
            var sumCellRes = sheet.getRow(rowNumber + 4).getCell(columnResult)
            if (sumCellRes == null){
                sumCellRes = sheet.getRow(rowNumber + 4).createCell(columnResult)
            }
            sumCellRes.setCellValue(listResPoint[rowNumber])
            sumCellRes.cellStyle = styleSumPoint
        }

        val sortedScores = listResPoint.sortedDescending().distinct()
        val ranks = mutableMapOf<Double, Int>()
        var rank = 1
        for (score in sortedScores) {
            ranks[score] = rank++
        }
        val dictPlace = listResPoint.mapIndexed { index, score -> index to ranks[score]!! }.toMap()

        var placeCell = sheet.getRow(2).getCell(columnResult + 1)
        if (placeCell == null){
            placeCell = sheet.getRow(1).createCell(columnResult + 1)
        }
        placeCell.setCellValue("Место")
        placeCell.cellStyle = style
        sheet.addMergedRegion(CellRangeAddress(1, 3, columnResult + 1,columnResult + 1))

        val stylePlace = workBook.createCellStyle()
        stylePlace.alignment = HorizontalAlignment.CENTER
        val stylePlaceOne = workBook.createCellStyle()
        stylePlaceOne.alignment = HorizontalAlignment.CENTER
        stylePlaceOne.fillForegroundColor = IndexedColors.GOLD.getIndex()
        stylePlaceOne.fillPattern = FillPatternType.SOLID_FOREGROUND
        val stylePlaceTwo = workBook.createCellStyle()
        stylePlaceTwo.alignment = HorizontalAlignment.CENTER
        stylePlaceTwo.fillForegroundColor = IndexedColors.GREY_25_PERCENT.getIndex()
        stylePlaceTwo.fillPattern = FillPatternType.SOLID_FOREGROUND
        val stylePlaceThird = workBook.createCellStyle()
        stylePlaceThird.alignment = HorizontalAlignment.CENTER
        stylePlaceThird.fillForegroundColor = IndexedColors.BROWN.getIndex()
        stylePlaceThird.fillPattern = FillPatternType.SOLID_FOREGROUND
        for (rowNumber in 0 until  numbersCompetitor){
            var placeCellRes = sheet.getRow(rowNumber + 4).getCell(columnResult + 1)
            if (placeCellRes  == null){
                placeCellRes  = sheet.getRow(rowNumber + 4).createCell(columnResult + 1)
            }
            when (val place = dictPlace[rowNumber]!!.toDouble()) {
                1.0 -> {
                    placeCellRes.setCellValue(place)
                    placeCellRes.cellStyle = stylePlaceOne
                }
                2.0 -> {
                    placeCellRes.setCellValue(place)
                    placeCellRes.cellStyle = stylePlaceTwo
                }
                3.0 -> {
                    placeCellRes.setCellValue(place)
                    placeCellRes.cellStyle = stylePlaceThird
                }
                else -> {
                    placeCellRes.setCellValue(place)
                    placeCellRes.cellStyle = stylePlace
                }
            }
        }
        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }
    fun addNormativeAndPoint(filePath: String, res:Any, competitor: Competitor, sport: String, sportColumnStart: Int){
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(0)
        val rowNumberCompetitor = findRow(competitor.nameCompetitor, filePath)
        val styleNormative = workBook.createCellStyle()
        styleNormative.alignment = HorizontalAlignment.CENTER
        if (rowNumberCompetitor == -1){
            throw Exception("Competitor not find")
        }
        val point = PointsCalculator().getPoint(competitor, sport, res as Comparable<Any>)
        val row = sheet.getRow(rowNumberCompetitor)
        var cellRes = row.getCell(sportColumnStart)
        if (cellRes == null){
            cellRes = sheet.getRow(rowNumberCompetitor).createCell(sportColumnStart)
        }
        if (res is LocalTime){
            var formatter = DateTimeFormatter.ofPattern("mm:ss.SS")
            if (sport == "Бег на 30 м (с)" || sport == "Челночный бег 3х10 м (с)"
                || sport == "Бег на 60 м (с)" || sport == "Бег на 100 м (с)")
            {
                formatter = DateTimeFormatter.ofPattern("ss.SS")
                cellRes.setCellValue(res.format(formatter).toDouble())
            }
            else{
                cellRes.setCellValue(res.format(formatter).toString())
            }
        }
        else if (res is Double){
            cellRes.setCellValue(res)
        }
        cellRes.cellStyle = styleNormative

        var cellPoint = row.getCell(sportColumnStart + 1)
        if (cellPoint == null){
            cellPoint = sheet.getRow(rowNumberCompetitor).createCell(sportColumnStart + 1)
        }

        val stylePoint = workBook.createCellStyle()
        stylePoint.fillForegroundColor = IndexedColors.YELLOW.getIndex()
        stylePoint.fillPattern = FillPatternType.SOLID_FOREGROUND
        stylePoint.alignment = HorizontalAlignment.CENTER

        cellPoint.setCellValue(point.toDouble())
        cellPoint.cellStyle = stylePoint

        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }

    fun findRow(valueToFind: String, filePath: String): Int{
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream).getSheetAt(0)
        for (row in 0..workBook.lastRowNum){
            val rowObj = workBook.getRow(row) ?: continue
            val cell = rowObj.getCell(1)
            if (cell.toString() == valueToFind){
                return row
            }
        }
        fileInputStream.close()
        return -1
    }
    fun addSport(sport:String, filePath: String, numberColumnForSport:Int){
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val style = workBook.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER
        style.verticalAlignment = VerticalAlignment.CENTER
        style.wrapText = true
        val sheet = workBook.getSheetAt(0)
        val row = sheet.getRow(1)
        var cell = row.getCell(numberColumnForSport)
        if (cell == null){
            cell = row.createCell(numberColumnForSport)
        }
        cell.cellStyle = style
        cell.setCellValue(sport)
        sheet.autoSizeColumn(numberColumnForSport)
        val widthColumn = (sheet.getColumnWidth(numberColumnForSport) / 2.0).toInt()
        sheet.addMergedRegion(CellRangeAddress(1,2, numberColumnForSport,
            numberColumnForSport + 1 ))
        sheet.setColumnWidth(numberColumnForSport, widthColumn)
        sheet.setColumnWidth(numberColumnForSport + 1, widthColumn)

        val rowRes = sheet.getRow(3)
        var cellRes = rowRes.getCell(numberColumnForSport)
        if (cellRes == null){
            cellRes = rowRes.createCell(numberColumnForSport)
        }
        cellRes.cellStyle = style
        cellRes.setCellValue("Результат")
        sheet.autoSizeColumn(numberColumnForSport)
        val widthColumnRes = (sheet.getColumnWidth(numberColumnForSport)).toInt()
        if (widthColumnRes >= widthColumn){
            sheet.setColumnWidth(numberColumnForSport, widthColumnRes)
        }
        else{
            sheet.setColumnWidth(numberColumnForSport, widthColumn)
        }
        val rowPoint = sheet.getRow(3)
        var cellPoint = rowPoint.getCell(numberColumnForSport + 1)
        if (cellPoint == null){
            cellPoint = rowPoint.createCell(numberColumnForSport + 1)
        }

        cellPoint.cellStyle = style
        cellPoint.setCellValue("Очки")
        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }
    fun ageChangeByStep(filePath: String, listCompetitor: List<Competitor>, columnAge:Int){
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(0)
        val row = sheet.getRow(1)
        val cell = row.getCell(columnAge)
        cell.setCellValue("Ступень")
        for (i in 0 until listCompetitor.size){
            val r = sheet.getRow(4 + i)
            val c = r.getCell(columnAge)
            c.setCellValue(listCompetitor[i].degree.toDouble())
        }
        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }
    fun deleteColumn(filePath: String, column: Int){
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(0)
        val rows = sheet.rowIterator()
        while (rows.hasNext()){
            val row = rows.next()
            row.removeCell(row.getCell(column))
        }
        val deleteMergeRegion = mutableListOf<Int>()
        for (i in sheet.mergedRegions.indices){
            if (sheet.getMergedRegion(i).firstColumn >=2){
                deleteMergeRegion.add(i)
            }
        }
        for (i in deleteMergeRegion.reversed()){
            sheet.removeMergedRegion(i)
        }
        sheet.shiftColumns(2, 5, -1)
        sheet.addMergedRegion(CellRangeAddress(2, 3,2,2))
        sheet.addMergedRegion(CellRangeAddress(2, 3, 3, 3))
        sheet.addMergedRegion(CellRangeAddress(2, 3, 4, 4))
        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }

    fun getListCompetitor(filePath:String): List<Competitor>{
        val fileInputStream = FileInputStream(filePath)
        val workBook = WorkbookFactory.create(fileInputStream).getSheetAt(0)
        var numberOfCompetitor = 0
        var name = ""
        var age = 0
        var nameTeam = ""
        var participantNumber = 0
        var listCompetitor = mutableListOf<Competitor>()
        var gender: String = ""
        for (row in 4..workBook.lastRowNum){
            try {
                if (workBook.getRow(row).getCell(0).cellType == CellType.NUMERIC) {
                    numberOfCompetitor = workBook.getRow(row).getCell(0).numericCellValue.toInt()
                }
                if (workBook.getRow(row).getCell(1).cellType == CellType.STRING) {
                    name = workBook.getRow(row).getCell(1).stringCellValue.toString()
                }
                if (workBook.getRow(row).getCell(1).cellType == CellType.BLANK){
                    continue
                }
                if (workBook.getRow(row).getCell(2).cellType == CellType.STRING) {
                    val genderTable = workBook.getRow(row).getCell(2).stringCellValue.toString()
                    if (genderTable == "М") {
                        gender = Gender.MALE.string
                    }
                    if (genderTable == "Ж") {
                        gender = Gender.FEMALE.string
                    }
                }
                if (workBook.getRow(row).getCell(3).cellType == CellType.NUMERIC) {
                    age = workBook.getRow(row).getCell(3).numericCellValue.toInt()
                }
                if (workBook.getRow(row).getCell(4).cellType == CellType.STRING) {
                    nameTeam = workBook.getRow(row).getCell(4).stringCellValue.toString()
                }
                if (workBook.getRow(row).getCell(5).cellType == CellType.NUMERIC) {
                    participantNumber = workBook.getRow(row).getCell(5).numericCellValue.toInt()
                }
                listCompetitor.add(Competitor(name, age, gender, nameTeam, participantNumber))
            }
            catch (e: NullPointerException){
                throw Exception("table is filled in incorrectly")
            }
        }
        fileInputStream.close()
        return listCompetitor
    }
}