package com.gtohelper.data.models

import com.gtohelper.domain.Gender
import com.gtohelper.domain.Competitor
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun main(){
    val filePath = "/Users/glebmoskalev/Downloads/example — копия 2.xlsx"
    for (i in ExcelReader().getCompetitorList(filePath,4)){
        println(i)
    }
}

class ExcelReader {
    fun getCompetitorList(filePath:String, rowWithStartCompetitor: Int): List<Competitor>{
        val fileInputStream = FileInputStream(filePath)
        val workBook = WorkbookFactory.create(fileInputStream)
        val sheet = workBook.getSheetAt(0)

        val listCompetitor = mutableListOf<Competitor>()
        var name = ""
        var gender = ""
        var age = 0
        var nameTeam = ""
        var participantNumber = 0
        for (row in 4..sheet.lastRowNum){
            try {
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
                    age = sheet.getRow(row).getCell(3).numericCellValue.toInt()
                }
                if (sheet.getRow(row).getCell(4).cellType == CellType.STRING){
                    nameTeam = sheet.getRow(row).getCell(4).stringCellValue
                }
                if (sheet.getRow(row).getCell(5).cellType == CellType.NUMERIC){
                    participantNumber = sheet.getRow(row).getCell(5).numericCellValue.toInt()
                }
                val competitor = Competitor(name, age, gender, nameTeam, participantNumber)
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