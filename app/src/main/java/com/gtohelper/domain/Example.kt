package com.gtohelper.domain

import JsonParser
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import org.apache.poi.xssf.usermodel.XSSFRichTextString
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.absolutePathString


fun main(){
    println(JsonParser().getDictionaryWithStandards())
}