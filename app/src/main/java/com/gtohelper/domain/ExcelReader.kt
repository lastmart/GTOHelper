package com.gtohelper.domain

import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun main(){
    val filePath = "/Users/glebmoskalev/Downloads/Книга1.xlsx"
//    println(ExcelReader(null).getCompetitorList(filePath))
    for (e in ExcelReader(null).getCompetitorList(filePath)){
        println(e)
    }
}

class ExcelReader(private val competitorRepository: CompetitorRepository?) {
    private fun findCellWithSymbol(sheet:Sheet, stringSearch: String): Pair<Int, Int>{
        val rows = sheet.iterator()
        while (rows.hasNext()) {
            val row = rows.next()
            val cells = row.iterator()
            while (cells.hasNext()) {
                val cell = cells.next()
                val cellValue = cell.toString()
                if (cellValue.contains(stringSearch)) {
                    return Pair(cell.rowIndex, cell.columnIndex)
                }
            }
        }
        return Pair(-1, -1)
    }
    fun getCompetitorList(filePath:String): List<Competitor>{
        val fileInputStream = FileInputStream(filePath)
        val workBook = WorkbookFactory.create(fileInputStream)
        val sheet = workBook.getSheetAt(0)
        val listCompetitor = mutableListOf<Competitor>()
        val (rowId, columnId) = findCellWithSymbol(sheet,"№")
        if (rowId == -1 && columnId == -1){
            throw Exception("TableImportError: Not Found \"№\"")
        }
        if (sheet.getRow(rowId).getCell(columnId + 1).toString().lowercase() != "фио"){
            throw Exception("TableImportError: Not Found \"ФИО\"")
        }
        if (sheet.getRow(rowId).getCell(columnId + 2).toString().lowercase() != "пол"){
            throw Exception("TableImportError: Not Found \"ПОЛ\"")
        }
        if (sheet.getRow(rowId).getCell(columnId + 3).toString().lowercase() != "ступень"){
            throw Exception("TableImportError: Not Found \"СТУПЕНЬ\"")
        }
        if (sheet.getRow(rowId).getCell(columnId + 4).toString().lowercase() != "команда"){
            throw Exception("TableImportError: Not Found \"КОМАНДА\"")
        }

        var name = ""
        var gender = ""
        var degree = 0
        var nameTeam = ""
        var participantNumber = 0
        val listParticipantNumbers = mutableListOf<Int>()
        for (row in rowId+ 1..sheet.lastRowNum){
            if (sheet.getRow(row) == null){
                break
            }
            try {
                if (sheet.getRow(row).getCell(columnId).cellType == CellType.NUMERIC){
                    participantNumber = sheet.getRow(row).getCell(columnId).numericCellValue.toInt()
                    if (participantNumber !in listParticipantNumbers){
                        listParticipantNumbers.add(participantNumber)
                    }
                    else{
                        throw Exception("TableReadDataError: duplication \"participantNumber\": \"$participantNumber\"")
                    }
                }
                else {
                    throw Exception("TableReadDataError: couldn't read the \"participantNumber\"")
                }

                if (sheet.getRow(row).getCell(columnId + 1).cellType == CellType.STRING){
                    name = sheet.getRow(row).getCell(columnId + 1).stringCellValue
                }
                else{
                    throw Exception("TableReadDataError: couldn't read the \"name\"")
                }

                if (sheet.getRow(row).getCell(columnId + 2).cellType == CellType.STRING){
                    val genderTable = sheet.getRow(row).getCell(columnId + 2).stringCellValue
                    if (genderTable[0].lowercaseChar() == 'м'){
                        gender = Gender.MALE.string
                    }
                    else if (genderTable[0].lowercaseChar() == 'ж'){
                        gender = Gender.FEMALE.string
                    }
                    else {
                        throw Exception("TableReadDataError: gender not defined")
                    }
                }
                else{
                    throw Exception("TableReadDataError: couldn't read the \"gender\"")
                }

                if (sheet.getRow(row).getCell(columnId + 3).cellType == CellType.NUMERIC){
                    degree = sheet.getRow(row).getCell(columnId + 3).numericCellValue.toInt()
                    if (degree < 2){
                        throw Exception("TableReadDataError: degree minimum 2")
                    }
                    if (degree > 18){
                        throw Exception("TableReadDataError: degree maximum 18")
                    }
                }
                else{
                    throw Exception("TableReadDataError: couldn't read the \"degree\"")
                }

                if (sheet.getRow(row).getCell(columnId + 4).cellType == CellType.STRING){
                    nameTeam = sheet.getRow(row).getCell(columnId + 4).stringCellValue
                }
                else{
                    throw Exception("TableReadDataError: couldn't read the \"nameTeam\"")
                }

                val competitor = Competitor(name, gender, nameTeam, participantNumber, degree)
                listCompetitor.add(competitor)
            }
            catch (e: NullPointerException){
                throw Exception("TableReadDataError: Table is filled in incorrectly")
            }
        }
        fileInputStream.close()
        return listCompetitor
    }
}