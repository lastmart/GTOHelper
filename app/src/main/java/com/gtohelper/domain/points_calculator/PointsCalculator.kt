package com.gtohelper.domain.points_calculator

import com.gtohelper.domain.models.Competitor

interface PointsCalculator {

    fun <T : Comparable<T>> getPoints(
        competitor: Competitor,
        sport: String,
        result: T,
    ): Int
}