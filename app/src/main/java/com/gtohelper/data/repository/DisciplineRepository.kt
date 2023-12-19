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

        list.add(Discipline(getDrawable(R.drawable.discipline_human_pull_up), "Подтягивания"))
        list.add(Discipline(getDrawable(R.drawable.discipline_sprinting), "Бег 30 метров"))
        // TODO

        return list
    }

    private fun getDrawable(resId: Int) = AppCompatResources.getDrawable(context, resId)!!
}