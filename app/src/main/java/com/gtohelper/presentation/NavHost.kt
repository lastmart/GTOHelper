package com.gtohelper.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gtohelper.presentation.ui.competitions.CompetitionListRoute
import com.gtohelper.presentation.ui.competitions.CompetitionListViewModel
import com.gtohelper.presentation.ui.competitions.add_competition.AddCompetitionRoute
import com.gtohelper.presentation.ui.competitions.add_competition.AddCompetitionViewModel
import com.gtohelper.presentation.ui.competitors_list.CompetitorListRoute
import com.gtohelper.presentation.ui.competitors_list.CompetitorsListViewModel
import com.gtohelper.presentation.ui.competitors_list.add_competitor.AddCompetitorRoute
import com.gtohelper.presentation.ui.competitors_list.add_competitor.AddCompetitorViewModel
import com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table.AddCompetitorsFromTableRoute
import com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table.AddCompetitorsFromTableViewModel
import com.gtohelper.presentation.ui.disciplines_list.DisciplineListRoute
import com.gtohelper.presentation.ui.disciplines_list.DisciplinesListViewModel
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.AddDisciplineRoute
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.AddDisciplineViewModel


@Composable
fun AppNavHost(navController: NavHostController) {
    val competitionIdArgument =
        remember { listOf(navArgument("competition_id") { type = NavType.IntType }) }

    val competitionNameArgument =
        remember { navArgument("competition_name") { type = NavType.StringType } }

    NavHost(navController = navController, startDestination = "competitions") {

        composable("competitions") {
            val viewModel = hiltViewModel<CompetitionListViewModel>()
            CompetitionListRoute(navController, viewModel)
        }

        composable("add_competition") {
            val viewModel = hiltViewModel<AddCompetitionViewModel>()
            AddCompetitionRoute(navController, viewModel)
        }

//        composable(
//            "detail/{competition_id}",
//            arguments = competitionIdArgument
//        ) {
//            val viewModel = hiltViewModel<CompetitionDetailViewModel>()
//            val competitionId = it.arguments?.getInt("competition_id") ?: 0
//            CompetitionDetailRoute(navController, viewModel, competitionId)
//        }

        composable(
            "competitors/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<CompetitorsListViewModel>()
            val competitionId = it.arguments?.getInt("competition_id") ?: 0
            CompetitorListRoute(navController, viewModel, competitionId)
        }

        composable(
            "add_competitor/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<AddCompetitorViewModel>()
            AddCompetitorRoute(navController, viewModel)
        }

        composable(
            "add_competitor_from_table/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<AddCompetitorsFromTableViewModel>()
            AddCompetitorsFromTableRoute(navController, viewModel)
        }

        composable(
            "disciplines/{competition_id}/{competition_name}",
            arguments = competitionIdArgument + competitionNameArgument
        ) {
            val viewModel = hiltViewModel<DisciplinesListViewModel>()
            val competitionId = it.arguments?.getInt("competition_id") ?: 0
            val competitionName = it.arguments?.getString("competition_name") ?: "Дисциплиныыыы"

            DisciplineListRoute(
                navController = navController,
                viewModel = viewModel,
                competitionId = competitionId,
                competitionName = competitionName
            )
        }

        composable(
            route = "add_discipline/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<AddDisciplineViewModel>()
            val competitionId = it.arguments?.getInt("competition_id") ?: 0
            AddDisciplineRoute(
                navController = navController,
                viewModel = viewModel,
                competitionId = competitionId
            )
        }

        composable(
            "results/{competition_id}",
            arguments = competitionIdArgument
        ) {
            // TODO
        }
    }
}


