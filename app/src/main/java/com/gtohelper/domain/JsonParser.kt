import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class JsonParser {
    fun getDictionaryWithStandards(): MutableMap<String, MutableMap<Int, MutableMap<String, MutableMap<Any, Int>>>> {
        val dictionaryWithStandards: MutableMap<String, MutableMap<Int, MutableMap<String, MutableMap<Any, Int>>>> =
            HashMap()
//        val jsonFile = File("src/main/dictionaryWithStandards.json")
        val jsonFile = File("/Users/glebmoskalev/Downloads/dictionaryWithStandards.json")
        val jsonString = jsonFile.readText()

        val type: Type = object : TypeToken<Map<String, Map<Int, Map<String, Map<String, Int>>>>>() {}.type
        val gson = Gson()
        val jsonObject: Map<String, Map<Int, Map<String, Map<String, Int>>>> = gson.fromJson(jsonString, type)

        for ((sportKey, sport) in jsonObject) {
            for ((degreeKey, degree) in sport) {
                for ((genderKey, gender) in degree) {
                    for ((normativeKey, pointKey) in gender) {
                        var convertedNormativeKey = when {
                            isValidTimeFormat(normativeKey) -> convertTime(normativeKey)
                            normativeKey.toDoubleOrNull() != null -> normativeKey.toDouble()
                            else -> throw Exception("Could not read the normative $normativeKey")
                        }
                        if (sportKey == "Бег на 30 м (с)" || sportKey == "Челночный бег 3х10 м (с)"
                            || sportKey == "Бег на 60 м (с)" || sportKey == "Бег на 100 м (с)")
                        {
                            convertedNormativeKey  = convertRunTime(normativeKey)
                        }
                        dictionaryWithStandards
                            .computeIfAbsent(sportKey) { HashMap() }
                            .computeIfAbsent(degreeKey, { HashMap() })
                            .computeIfAbsent(genderKey, { HashMap() })
                            .put(convertedNormativeKey, pointKey)
                    }
                }
            }
        }

        return dictionaryWithStandards
    }

    private fun isValidTimeFormat(time: String): Boolean {
        val regex = Regex("""^\d{2}:\d{2}.\d{2}$""")
        return regex.matches(time)
    }

    fun convertRunTime(time:String): LocalTime{
        var changeTime = time
        if ("." !in time) {
            changeTime = "$time.0"
        }
        val seconds = changeTime.split(".")[0]
        val milliseconds = changeTime.split(".")[1]
        val time = "00:${if (seconds.length == 1) "0$seconds" else seconds}.${if (milliseconds.length == 1) "${milliseconds}0" else milliseconds}"
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("mm:ss.SS")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .toFormatter()
        return LocalTime.parse(time, formatter)
    }

    fun convertTime(time:String): LocalTime{
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("mm:ss.SS")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .toFormatter()
        return LocalTime.parse(time, formatter)
    }
}