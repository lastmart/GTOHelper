package com.gtohelper.data.mappers

import com.gtohelper.common.Mapper
import com.gtohelper.data.database.result.CompetitorResultsEntity
import com.gtohelper.domain.models.CompetitorResults

class CompetitorResultsEntityToCompetitorResultsMapper: Mapper<CompetitorResultsEntity, CompetitorResults> {

    override fun transform(data: CompetitorResultsEntity): CompetitorResults {
        TODO("Not yet implemented")
    }
}