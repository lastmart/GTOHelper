package com.gtohelper.data.mappers

import com.gtohelper.common.Mapper
import com.gtohelper.data.database.sport.SportEntity
import com.gtohelper.domain.models.Sport

class SportEntityToSportMapper : Mapper<SportEntity, Sport> {

    override fun transform(data: SportEntity): Sport {
        return Sport(
            name = data.name,
            competitorIds = data.competitorIds
        )
    }
}