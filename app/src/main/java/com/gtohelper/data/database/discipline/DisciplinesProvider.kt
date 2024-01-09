package com.gtohelper.data.database.discipline

import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType

class DisciplinesProvider {

    val disciplines = listOf(
        DisciplineEntity(
            name = "Бег на короткие дистанции",
            imageResource = R.drawable.discipline_sprinting,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Бег на 30 м",
                    imageResource = R.drawable.sub_discipline_sprinting_30m,
                    type = DisciplinePointType.SHORT_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на 60 м",
                    imageResource = R.drawable.sub_discipline_sprinting_60m,
                    type = DisciplinePointType.SHORT_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на 100 м",
                    imageResource = R.drawable.sub_discipline_sprinting_100m,
                    type = DisciplinePointType.SHORT_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Бег на длинные дистанции",
            imageResource = R.drawable.discipline_long_distance_running,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Бег на 1 км",
                    imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на 1.5 км",
                    imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на 2 км",
                    imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на 3 км",
                    imageResource = R.drawable.sub_discipline_long_distance_running_3km,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Кросс по пересечённой местности",
            imageResource = R.drawable.discipline_cross_country,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Кросс на 2000 м по пересечённой местности",
                    imageResource = R.drawable.sub_discipline_cross_country_2km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Кросс на 3000 м по пересечённой местности",
                    imageResource = R.drawable.sub_discipline_cross_country_3km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Кросс на 5000 м по пересечённой местности",
                    imageResource = R.drawable.sub_discipline_cross_country_5km,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Смешанное передвижение",
            imageResource = R.drawable.discipline_mixed_movement,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Смешанное передвижение на 1000 м",
                    imageResource = R.drawable.sub_discipline_mixed_movement_1km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Смешанное передвижение на 2000 м",
                    imageResource = R.drawable.sub_discipline_mixed_movement_2km,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )

        },
        DisciplineEntity(
            name = "Смешанное передвижение по пересеченной местности",
            imageResource = R.drawable.discipline_mixed_movement_over_rough_terrain,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Передвижение на 1000 м по пересеченной местности",
                    imageResource = R.drawable.sub_discipline_mixed_movement_over_rough_terrain_1km,
                    type = DisciplinePointType.LONG_TIME
                ),
                SubDisciplineEntity(
                    name = "Передвижение на 2000 м по пересеченной местности",
                    imageResource = R.drawable.sub_discipline_mixed_movement_over_rough_terrain_2km,
                    type = DisciplinePointType.LONG_TIME
                ),
                SubDisciplineEntity(
                    name = "Передвижение на 3000 м по пересеченной местности",
                    imageResource = R.drawable.sub_discipline_mixed_movement_over_rough_terrain_3km,
                    type = DisciplinePointType.LONG_TIME
                )
            )
        },
        DisciplineEntity(
            name = "Челночный бег 3х10 м",
            imageResource = R.drawable.discipline_shuttle_run,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Челночный бег 3х10 м",
                    imageResource = R.drawable.discipline_shuttle_run,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Скандинавская ходьба на 3000 м",
            imageResource = R.drawable.discipline_nordic_walking,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Скандинавская ходьба на 3000 м",
                    imageResource = R.drawable.discipline_nordic_walking,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Передвижение на лыжах",
            imageResource = R.drawable.discipline_movement_on_skis,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Передвижение на лыжах на 2000 м",
                    imageResource = R.drawable.sub_discipline_movement_on_skis_2km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Передвижение на лыжах на 3000 м",
                    imageResource = R.drawable.sub_discipline_movement_on_skis_3km,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Бег на лыжах",
            imageResource = R.drawable.discipline_skiing,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Бег на лыжах на 1000 м",
                    imageResource = R.drawable.sub_discipline_skiing_1km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на лыжах на 2000 м",
                    imageResource = R.drawable.sub_discipline_skiing_2km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на лыжах на 3000 м",
                    imageResource = R.drawable.sub_discipline_skiing_3km,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Бег на лыжах на 5000 м",
                    imageResource = R.drawable.sub_discipline_skiing_5km,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Подтягивание из виса на высокой перекладине",
            imageResource = R.drawable.discipline_human_pull_up_top,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Подтягивание из виса на высокой перекладине",
                    imageResource = R.drawable.discipline_human_pull_up_top,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Подтягивание из виса лежа на низкой перекладине",
            imageResource = R.drawable.discipline_human_pull_up_bottom,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Подтягивание из виса лежа на низкой перекладине",
                    imageResource = R.drawable.discipline_human_pull_up_bottom,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Сгибание и разгибание рук",
            imageResource = R.drawable.discipline_flexion_extension_arms,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Сгибание и разгибание рук в упоре о сиденье стула",
                    imageResource = R.drawable.sub_discipline_flexion_extension_arms_chair,
                    type = DisciplinePointType.AMOUNT,
                ),
                SubDisciplineEntity(
                    name = "Сгибание и разгибание рук в упоре о скамью",
                    imageResource = R.drawable.sub_discipline_flexion_extension_arms_bench,
                    type = DisciplinePointType.AMOUNT,
                ),
                SubDisciplineEntity(
                    name = "Сгибание и разгибание рук в упоре лежа на полу",
                    imageResource = R.drawable.sub_discipline_flexion_extension_arms_chair,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Наклон вперед",
            imageResource = R.drawable.discipline_lean_forward,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Наклон вперед",
                    imageResource = R.drawable.discipline_lean_forward,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Прыжок в длину с места",
            imageResource = R.drawable.discipline_standing_long_jump,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Прыжок в длину с места",
                    imageResource = R.drawable.discipline_standing_long_jump,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Поднимание туловища из положения лежа на спине",
            imageResource = R.drawable.discipline_lifting_body,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Поднимание туловища из положения лежа на спине",
                    imageResource = R.drawable.discipline_lifting_body,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Плавание",
            imageResource = R.drawable.discipline_swimming,
            type = DisciplinePointType.LONG_TIME
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Плавание 25 м",
                    imageResource = R.drawable.sub_discipline_swimming_25m,
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDisciplineEntity(
                    name = "Плавание 50 м",
                    imageResource = R.drawable.sub_discipline_swimming_50m,
                    type = DisciplinePointType.LONG_TIME,
                ),
            )
        },
        DisciplineEntity(
            name = "Стрельба с дистанции 10 м",
            imageResource = R.drawable.discipline_shooting,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Стрельба из винтовки с открытым прицелом",
                    imageResource = R.drawable.sub_discipline_shooting_open_sight,
                    type = DisciplinePointType.AMOUNT,
                ),
                SubDisciplineEntity(
                    name = "Стрельба из винтовки с диоптрическим прицелом",
                    imageResource = R.drawable.sub_discipline_shooting_diopter_sight,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Рывок гири",
            imageResource = R.drawable.discipline_kettlebell_snatch,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Рывок гири",
                    imageResource = R.drawable.discipline_kettlebell_snatch,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Метание",
            imageResource = R.drawable.discipline_throwing,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Метание мяча весом 150 г",
                    imageResource = R.drawable.sub_discipline_ball_throwing_150g,
                    type = DisciplinePointType.AMOUNT,
                ),
                SubDisciplineEntity(
                    name = "Метание спортивного снаряда весом 500 г",
                    imageResource = R.drawable.sub_discipline_shell_throwing_500g,
                    type = DisciplinePointType.AMOUNT,
                ),
                SubDisciplineEntity(
                    name = "Метание спортивного снаряда весом 700 г",
                    imageResource = R.drawable.sub_discipline_shell_throwing_700g,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
        DisciplineEntity(
            name = "Метание мяча в цель с дистанции 6 м",
            imageResource = R.drawable.discipline_ball_throwing_at_target,
            type = DisciplinePointType.AMOUNT
        ).apply {
            subDisciplines = listOf(
                SubDisciplineEntity(
                    name = "Метание мяча в цель с дистанции 6 м",
                    imageResource = R.drawable.discipline_flexion_extension_arms,
                    type = DisciplinePointType.AMOUNT,
                ),
            )
        },
    )
}