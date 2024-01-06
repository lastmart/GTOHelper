package com.gtohelper.data.database.discipline

import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType

class DisciplinesProvider(
    val competitionId: Int
) {

    val disciplines = listOf(
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на короткие дистанции",
            parentName = "Бег на короткие дистанции",
            imageResource = R.drawable.discipline_sprinting,
            type = DisciplinePointType.TIME
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 30 м",
            parentName = "Бег на короткие дистанции",
            imageResource = R.drawable.sub_discipline_sprinting_30m,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 60 м",
            parentName = "Бег на короткие дистанции",
            imageResource = R.drawable.sub_discipline_sprinting_60m,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 100 м",
            parentName = "Бег на короткие дистанции",
            imageResource = R.drawable.sub_discipline_sprinting_100m,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на длинные дистанции",
            parentName = "Бег на длинные дистанции",
            imageResource = R.drawable.discipline_long_distance_running,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 1 км",
            parentName = "Бег на длинные дистанции",
            imageResource = R.drawable.sub_discipline_long_distance_running_1km,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 1.5 км",
            parentName = "Бег на длинные дистанции",
            imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 2 км",
            parentName = "Бег на длинные дистанции",
            imageResource = R.drawable.sub_discipline_long_distance_running_2km,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на 3 км",
            parentName = "Бег на длинные дистанции",
            imageResource = R.drawable.sub_discipline_long_distance_running_3km,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Кросс по пересечённой местности",
            parentName = "Кросс по пересечённой местности",
            imageResource = R.drawable.discipline_cross_country,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Смешанное передвижение",
            parentName = "Смешанное передвижение",
            imageResource = R.drawable.discipline_mixed_movement,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Челночный бег",
            parentName = "Челночный бег",
            imageResource = R.drawable.discipline_shuttle_run,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Скандинавская ходьба",
            parentName = "Скандинавская ходьба",
            imageResource = R.drawable.discipline_nordic_walking,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Передвижение на лыжах",
            parentName = "Передвижение на лыжах",
            imageResource = R.drawable.discipline_movement_on_skis,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Бег на лыжах",
            parentName = "Бег на лыжах",
            imageResource = R.drawable.discipline_skiing,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Подтягивание из виса на высокой перекладине",
            parentName = "Подтягивание из виса на высокой перекладине",
            imageResource = R.drawable.discipline_human_pull_up_top,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Подтягивание из виса лежа на низкой перекладине",
            parentName = "Подтягивание из виса лежа на низкой перекладине",
            imageResource = R.drawable.discipline_human_pull_up_bottom,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Сгибание и разгибание рук",
            parentName = "Сгибание и разгибание рук",
            imageResource = R.drawable.discipline_flexion_extension_arms,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Наклон вперед",
            parentName = "Наклон вперед",
            imageResource = R.drawable.discipline_lean_forward,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Прыжок в длину с места",
            parentName = "Прыжок в длину с места",
            imageResource = R.drawable.discipline_standing_long_jump,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Прыжок в длину с разбега",
            parentName = "Прыжок в длину с разбега",
            imageResource = R.drawable.discipline_running_long_jump,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Поднимание туловища",
            parentName = "Поднимание туловища",
            imageResource = R.drawable.discipline_lifting_body,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Плавание",
            parentName = "Плавание",
            imageResource = R.drawable.discipline_swimming,
            type = DisciplinePointType.TIME,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Стрельба",
            parentName = "Стрельба",
            imageResource = R.drawable.discipline_shooting,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Рывок гири",
            parentName = "Рывок гири",
            imageResource = R.drawable.discipline_kettlebell_snatch,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Метание",
            parentName = "Метание",
            imageResource = R.drawable.discipline_throwing,
            type = DisciplinePointType.AMOUNT,
        ),
        DisciplineEntity(
//            competitionId = competitionId,
            name = "Метание мяча в цель",
            parentName = "Метание мяча в цель",
            imageResource = R.drawable.discipline_ball_throwing_at_target,
            type = DisciplinePointType.AMOUNT,
        ),
    )
}