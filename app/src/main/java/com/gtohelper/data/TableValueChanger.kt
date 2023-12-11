package com.gtohelper.data

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream

fun main(){
    val changer = TableValueChanger()
    val filePath = "/Users/glebmoskalev/Downloads/example — копия.xlsx"
    changer.changeName(filePath, "Москалев Глеб", "Глеб Москалёв")
}
class TableValueChanger {
    fun changeName(filePath:String, oldName:String, newName:String){
        //TODO: продумать, изменения по competitor, а не только по oldName
        val fileInputStream = FileInputStream(filePath)
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(0)
        for (row in 4..sheet.lastRowNum){
            try {
                if (sheet.getRow(row).getCell(1).cellType == CellType.STRING &&
                    sheet.getRow(row).getCell(1).stringCellValue == oldName) {
                    sheet.getRow(row).getCell(1).setCellValue(newName)
                }
            }
            catch (e: NullPointerException)
            {
                continue
            }
        }
        fileInputStream.close()
        val fileOutputStream = FileOutputStream(filePath)
        workBook.write(fileOutputStream)
        fileOutputStream.close()
    }
}