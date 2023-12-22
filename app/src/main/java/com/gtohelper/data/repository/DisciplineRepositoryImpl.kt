package com.gtohelper.data.repository

import com.gtohelper.R
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.repository.DisciplineRepository

class DisciplineRepositoryImpl : DisciplineRepository {

    private val disciplines: MutableList<Discipline> = initDisciplines()

    override suspend fun getDisciplines(): List<Discipline> {
        return disciplines
    }

    override suspend fun deleteDisciplineByName(name: String) {
        disciplines.removeIf { it.name == name }
    }

    private fun initDisciplines(): MutableList<Discipline> {
        val list = mutableListOf<Discipline>()

        list.add(
            Discipline(
                R.drawable.discipline_long_distance_running,
                "Бег на длинные дистанции",
                subDisciplines = listOf(
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_sprinting_30m,
                        name = "Бег на 30 м"
                    ),
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_sprinting_60m,
                        name = "Бег на 60 м"
                    ),
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_sprinting_100m,
                        name = "Бег на 100 м"
                    )
                )
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_sprinting,
                "Бег на короткие дистанции",
                subDisciplines = listOf(
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                        name = "Бег на 1 км"
                    ),
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km,
                        name = "Бег на 1.5 км",
                    ),
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                        name = "Бег на 2 км"
                    ),
                    SubDiscipline(
                        imageResource = R.drawable.sub_discipline_long_distance_running_3km,
                        name = "Бег на 3 км"
                    ),
                )
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_cross_country,
                "Кросс по пересечённой местности"
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_mixed_movement,
                "Смешанное передвижение"
            )
        )
        list.add(Discipline(R.drawable.discipline_shuttle_run, "Челночный бег"))
        list.add(
            Discipline(
                R.drawable.discipline_nordic_walking,
                "Скандинавская ходьба"
            )
        )
        list.add(Discipline(R.drawable.discipline_skiing, "Бег на лыжах"))
        list.add(
            Discipline(
                R.drawable.discipline_movement_on_skis,
                "Передвижение на лыжах"
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_human_pull_up_top,
                "Подтягивание из виса на высокой перекладине"
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_human_pull_up_bottom,
                "Подтягивание из виса лежа на низкой перекладине"
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_flexion_extension_arms,
                "Сгибание и разгибание рук"
            )
        )
        list.add(Discipline(R.drawable.discipline_lean_forward, "Наклон вперед"))
        list.add(
            Discipline(
                R.drawable.discipline_standing_long_jump,
                "Прыжок в длину с места"
            )
        )
        list.add(
            Discipline(
                R.drawable.discipline_running_long_jump,
                "Прыжок в длину с разбега"
            )
        )
        list.add(Discipline(R.drawable.discipline_lifting_body, "Поднимание туловища"))
        list.add(Discipline(R.drawable.discipline_swimming, "Плавание"))
        list.add(Discipline(R.drawable.discipline_shooting, "Стрельба"))
        list.add(Discipline(R.drawable.discipline_kettlebell_snatch, "Рывок гири"))
        list.add(Discipline(R.drawable.discipline_throwing, "Метание"))
        list.add(
            Discipline(
                R.drawable.discipline_ball_throwing_at_target,
                "Метание мяча в цель"
            )
        )

        return list
    }
}