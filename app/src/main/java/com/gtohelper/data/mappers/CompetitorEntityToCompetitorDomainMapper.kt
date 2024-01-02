package com.gtohelper.data.mappers

import com.gtohelper.common.Mapper
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.domain.models.Competitor

class CompetitorEntityToCompetitorDomainMapper : Mapper<CompetitorEntity, Competitor> {

    override fun transform(data: CompetitorEntity): Competitor {
        return Competitor(
            nameCompetitor = data.nameCompetitor,
            participantNumber = data.id,
            gender = data.gender.string,
            nameTeam = data.nameTeam,
            degree = data.degree,
        )
    }
}