package com.gtohelper.data.repository

import com.gtohelper.R
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.mappers.toSubDiscipline
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.repository.DisciplineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DisciplineRepositoryImpl(
    private val dao: DisciplineDao,
    private val competitionId: Int = 1
) : DisciplineRepository {


    init {
        GlobalScope.launch(Dispatchers.IO) {
            if (dao.getDisciplines(competitionId).isNotEmpty()) return@launch

            dao.upsertDisciplines(
                listOf(
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на короткие дистанции",
                        imageResource = R.drawable.discipline_sprinting
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 30 м",
                        parentName = "Бег на короткие дистанции",
                        imageResource = R.drawable.sub_discipline_sprinting_30m
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 60 м",
                        parentName = "Бег на короткие дистанции",
                        imageResource = R.drawable.sub_discipline_sprinting_60m
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 100 м",
                        parentName = "Бег на короткие дистанции",
                        imageResource = R.drawable.sub_discipline_sprinting_100m
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на длинные дистанции",
                        imageResource = R.drawable.discipline_long_distance_running
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 1 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_1km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 1.5 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 2 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_2km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 3 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_3km
                    ),
                )
            )
        }
    }

    override suspend fun getDisciplines(): List<Discipline> {
        val disciplines = dao.getDisciplines(competitionId)

        val parentImageResources = disciplines
            .filter { it.parentName.isNullOrBlank() }
            .associateBy({ it.name }, { it.imageResource })

        return disciplines
            .groupBy { it.parentName }
            .map { (parentName, subDisciplines) ->
                val domainSubDisciplines = subDisciplines.map { it.toSubDiscipline() }

                Discipline(
                    imageResource = parentImageResources[parentName] ?: return@map null,
                    name = parentName ?: return@map null,
                    subDisciplines = domainSubDisciplines,
                    isSelected = false
                )
            }
            .filterNotNull()
    }

    override suspend fun getSelectedDisciplines(): List<Discipline> {
        return getDisciplines()
            .flatMap {
                it.subDisciplines.filter { subDiscipline -> subDiscipline.isSelected }
            }
    }

    override suspend fun getNotSelectedDisciplines(): List<Discipline> {
        return getDisciplines().map {
            Discipline(
                imageResource = it.imageResource,
                name = it.name,
                subDisciplines = it.subDisciplines.filter { subDiscipline -> !subDiscipline.isSelected },
                isSelected = it.isSelected
            )
        }.filter { it.subDisciplines.isNotEmpty() }
    }

    override suspend fun addDisciplineToSelected(discipline: Discipline) {
        val disciplineEntity = dao.getDisciplineByName(discipline.name, competitionId) ?: return
        val newDiscipline = disciplineEntity.copy(isSelected = true)

        dao.upsertDiscipline(newDiscipline)
    }

    override suspend fun deleteDisciplineFromSelectedByName(name: String) {
        val disciplineEntity = dao.getDisciplineByName(name, competitionId) ?: return
        val newDiscipline = disciplineEntity.copy(isSelected = false)

        dao.upsertDiscipline(newDiscipline)
    }


    /* private fun initDisciplines(): MutableList<Discipline> {
         val list = mutableListOf<Discipline>()

         list.add(
             Discipline(
                 R.drawable.discipline_long_distance_running,
                 "Бег на длинные дистанции",
                 subDisciplines = listOf(
                     Discipline(
                         imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                         name = "Бег на 1 км"
                     ),
                     Discipline(
                         imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km,
                         name = "Бег на 1.5 км",
                     ),
                     Discipline(
                         imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                         name = "Бег на 2 км"
                     ),
                     Discipline(
                         imageResource = R.drawable.sub_discipline_long_distance_running_3km,
                         name = "Бег на 3 км"
                     ),
                 )
             )
         )
         list.add(
             Discipline(
                 R.drawable.discipline_sprinting,
                 "Бег на короткие дистанции",
                 subDisciplines = listOf(
                     Discipline(
                         imageResource = R.drawable.sub_discipline_sprinting_30m,
                         name = "Бег на 30 м"
                     ),
                     Discipline(
                         imageResource = R.drawable.sub_discipline_sprinting_60m,
                         name = "Бег на 60 м"
                     ),
                     Discipline(
                         imageResource = R.drawable.sub_discipline_sprinting_100m,
                         name = "Бег на 100 м"
                     )
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
     }*/
}
