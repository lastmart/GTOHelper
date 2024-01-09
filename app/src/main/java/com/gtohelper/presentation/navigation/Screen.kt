package com.gtohelper.presentation.navigation

sealed class Screen(val route: String) {

    data object CompetitionsScreen : Screen("competitions")
    data object AddCompetitionScreen : Screen("add_competition")
    data object EditCompetitionScreen : Screen("edit_competition")
    data object CompetitorsListScreen : Screen("competitors")
    data object AddCompetitorScreen : Screen("add_competitor")
    data object AddCompetitorFromTableScreen : Screen("add_competitor_from_table")
    data object DisciplinesListScreen : Screen("disciplines")
    data object AddDisciplineScreen : Screen("add_discipline")
    data object EditCompetitorScreen : Screen("edit_competitor")
    data object AddResultsScreen : Screen("add_results")
    data object EditResultScreen : Screen("edit_result")
    data object CompetitorsResultsListScreen : Screen("competitors_results")
    data object HelpScreen : Screen("help")

    fun withRouteArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/{$it}")
            }
        }
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}



