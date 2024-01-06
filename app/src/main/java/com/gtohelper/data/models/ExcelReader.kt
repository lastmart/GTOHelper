package com.gtohelper.data.models

import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.InputStream

fun main() {
    val filePath = "C:\\Users\\Xoka74\\Desktop\\competitors.xlsx"
    val file = File(filePath)
    val competitors = ExcelReader().getCompetitorList(file.inputStream(), 1)
    val b = ""
}

class ExcelReader {
    fun getCompetitorList(stream: InputStream, competitionId: Int): List<Competitor> {
        val workBook = WorkbookFactory.create(stream)
        val sheet = workBook.getSheetAt(0)

        val competitors = mutableListOf<Competitor>()
        var name = ""
        var gender = Gender.MALE
        var degree = 1
        var nameTeam = ""
        var participantNumber = 0
        for (row in 1..sheet.lastRowNum) {
            try {
                if (sheet.getRow(row).getCell(0).cellType == CellType.NUMERIC) {
                    participantNumber = sheet.getRow(row).getCell(0).numericCellValue.toInt()
                }
                if (sheet.getRow(row).getCell(1).cellType == CellType.STRING) {
                    name = sheet.getRow(row).getCell(1).stringCellValue
                }
                if (sheet.getRow(row).getCell(2).cellType == CellType.STRING) {
                    val genderTable = sheet.getRow(row).getCell(2).stringCellValue
                    if (genderTable[0].lowercaseChar() == 'м') {
                        gender = Gender.MALE
                    } else if (genderTable[0].lowercaseChar() == 'ж') {
                        gender = Gender.FEMALE
                    }
                }
                if (sheet.getRow(row).getCell(3).cellType == CellType.NUMERIC) {
                    degree = sheet.getRow(row).getCell(3).numericCellValue.toInt()
                }
                if (sheet.getRow(row).getCell(4).cellType == CellType.STRING) {
                    nameTeam = sheet.getRow(row).getCell(4).stringCellValue
                }
                val competitor =
                    Competitor(
                        id = 0,
                        name = name,
                        gender = gender,
                        teamName = nameTeam,
                        number = participantNumber,
                        competitionId = competitionId,
                        degree = degree,
                    )
                competitors.add(competitor)
            } catch (e: Exception) {
                throw Exception("Table is filled in incorrectly")
            }
        }

        return competitors
    }
}