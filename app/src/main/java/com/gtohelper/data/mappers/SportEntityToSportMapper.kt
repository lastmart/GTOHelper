package com.gtohelper.data.mappers

import com.gtohelper.common.Mapper
import com.gtohelper.data.database.sport.SportResultEntity
import com.gtohelper.domain.models.SportResult

class SportResultEntityToSportResultMapper : Mapper<SportResultEntity, SportResult> {

    override fun transform(data: SportResultEntity): SportResult {
        return SportResult(
            name = data.name,
            competitorId = data.competitorId,
            resultTime = data.resultTime,
            resultAmount = data.resultAmount
        )
    }
}