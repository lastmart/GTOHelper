package com.gtohelper.data.models

import com.gtohelper.domain.models.Competitor

class CompetitorResults(
    id: Int,
    name: String, // to class
    team: String,
    degree: String, // to enum
    gender: String, // to enum
    val points: Int
)