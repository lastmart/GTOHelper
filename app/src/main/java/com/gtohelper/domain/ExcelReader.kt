package com.gtohelper.domain

import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun main(){
    val filePath = "/Users/glebmoskalev/Downloads/пример импортируемой таблицы.xlsx"
    for (e in ExcelReader(null).getCompetitorList2(filePath)){
        println(e)
    }
}

class ExcelReader(private val competitorRepository: CompetitorRepository?) {
    fun getCompetitorList2(filePath:String): List<Competitor>{
        val fileInputStream = FileInputStream(filePath)
        val workBook = WorkbookFactory.create(fileInputStream)
        val sheet = workBook.getSheetAt(0)
        val listCompetitor = mutableListOf<Competitor>()
        var name = ""
        var gender = ""
        var degree = 0
        var nameTeam = ""
        var participantNumber = 0
        for (row in 1..sheet.lastRowNum){
            try {
                if (sheet.getRow(row).getCell(0).cellType == CellType.NUMERIC){
                    participantNumber = sheet.getRow(row).getCell(0).numericCellValue.toInt()
                }
                if (sheet.getRow(row).getCell(1).cellType == CellType.STRING){
                    name = sheet.getRow(row).getCell(1).stringCellValue
                }
                if (sheet.getRow(row).getCell(2).cellType == CellType.STRING){
                    val genderTable = sheet.getRow(row).getCell(2).stringCellValue
                    if (genderTable[0].lowercaseChar() == 'м'){
                        gender = Gender.MALE.string
                    }
                    else if (genderTable[0].lowercaseChar() == 'ж'){
                        gender = Gender.FEMALE.string
                    }
                }
                if (sheet.getRow(row).getCell(3).cellType == CellType.NUMERIC){
                    degree = sheet.getRow(row).getCell(3).numericCellValue.toInt()
                }
                if (sheet.getRow(row).getCell(4).cellType == CellType.STRING){
                    nameTeam = sheet.getRow(row).getCell(4).stringCellValue
                }

                val competitor = Competitor(name, gender, nameTeam, participantNumber, degree)
                if (competitor !in listCompetitor){
                    listCompetitor.add(competitor)
                }
            }
            catch (e: NullPointerException){
                throw Exception("Table is filled in incorrectly")
            }
        }
        fileInputStream.close()
        return listCompetitor
    }
}