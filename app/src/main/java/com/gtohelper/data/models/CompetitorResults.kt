package com.gtohelper.data.models

class CompetitorResults(
    id: Int,
    name: String, // to class
    team: String,
    degree: String, // to enum
    gender: String, // to enum
    val points: Int
) : Competitor(id, name, team, degree, gender)