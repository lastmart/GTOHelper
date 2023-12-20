package com.gtohelper.data.repository

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.gtohelper.R
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.repository.DisciplineRepository

class DisciplineRepositoryImpl(
    private val context: Context
) : DisciplineRepository {

    override suspend fun getDisciplines(): List<Discipline> {
        val list = mutableListOf<Discipline>()

        list.add(Discipline(getDrawable(R.drawable.discipline_long_distance_running), "Бег на длинные дистанции"))
        list.add(Discipline(getDrawable(R.drawable.discipline_sprinting), "Бег на короткие дистанции"))
        list.add(Discipline(getDrawable(R.drawable.discipline_cross_country), "Кросс по пересечённой местности"))
        list.add(Discipline(getDrawable(R.drawable.discipline_mixed_movement), "Смешанное передвижение"))
        list.add(Discipline(getDrawable(R.drawable.discipline_shuttle_run), "Челночный бег"))
        list.add(Discipline(getDrawable(R.drawable.discipline_nordic_walking), "Скандинавская ходьба"))
        list.add(Discipline(getDrawable(R.drawable.discipline_skiing), "Бег на лыжах"))
        list.add(Discipline(getDrawable(R.drawable.discipline_movement_on_skis), "Передвижение на лыжах"))
        list.add(Discipline(getDrawable(R.drawable.discipline_human_pull_up_top), "Подтягивание из виса на высокой перекладине"))
        list.add(Discipline(getDrawable(R.drawable.discipline_human_pull_up_bottom), "Подтягивание из виса лежа на низкой перекладине"))
        list.add(Discipline(getDrawable(R.drawable.discipline_flexion_extension_arms), "Сгибание и разгибание рук"))
        list.add(Discipline(getDrawable(R.drawable.discipline_lean_forward), "Наклон вперед"))
        list.add(Discipline(getDrawable(R.drawable.discipline_standing_long_jump), "Прыжок в длину с места"))
        list.add(Discipline(getDrawable(R.drawable.discipline_running_long_jump), "Прыжок в длину с разбега"))
        list.add(Discipline(getDrawable(R.drawable.discipline_lifting_body), "Поднимание туловища"))
        list.add(Discipline(getDrawable(R.drawable.discipline_swimming), "Плавание"))
        list.add(Discipline(getDrawable(R.drawable.discipline_shooting), "Стрельба"))
        list.add(Discipline(getDrawable(R.drawable.discipline_kettlebell_snatch), "Рывок гири"))
        list.add(Discipline(getDrawable(R.drawable.discipline_throwing), "Метание"))
        list.add(Discipline(getDrawable(R.drawable.discipline_ball_throwing_at_target), "Метание мяча в цель"))


        return list
    }

    private fun getDrawable(resId: Int) = AppCompatResources.getDrawable(context, resId)!!
}