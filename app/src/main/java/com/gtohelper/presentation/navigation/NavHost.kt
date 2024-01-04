package com.gtohelper.presentation.navigation

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
import com.gtohelper.presentation.ui.competitors_results.CompetitorsResultsRoute
import com.gtohelper.presentation.ui.competitors_results.CompetitorsResultsViewModel
import com.gtohelper.presentation.ui.disciplines_list.DisciplineListRoute
import com.gtohelper.presentation.ui.disciplines_list.DisciplinesListViewModel
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.AddDisciplineRoute
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.AddDisciplineViewModel


@Composable
fun AppNavHost(navController: NavHostController) {
    val competitionIdArg = "competition_id"
    val competitionNameArg = "competition_name"

    val competitionIdArgument =
        remember { listOf(navArgument(competitionIdArg) { type = NavType.IntType }) }

    val competitionNameArgument =
        remember { listOf(navArgument(competitionNameArg) { type = NavType.StringType }) }

    NavHost(
        navController = navController,
        startDestination = Screen.CompetitionsScreen.route
    ) {

        composable(
            route = Screen.CompetitionsScreen.route
        ) {
            val viewModel = hiltViewModel<CompetitionListViewModel>()
            CompetitionListRoute(navController, viewModel)
        }

        composable(
            route = Screen.AddCompetitionScreen.route
        ) {
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
            route = Screen.CompetitorsListScreen.route + "/{$competitionIdArg}",
            //" competitors/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<CompetitorsListViewModel>()
            val competitionId = it.arguments?.getInt(competitionIdArg) ?: 0
            CompetitorListRoute(navController, viewModel, competitionId)
        }

        composable(
            route = Screen.AddCompetitorScreen.route + "/{$competitionIdArg}",
//            "add_competitor/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<AddCompetitorViewModel>()
            AddCompetitorRoute(navController, viewModel)
        }

        composable(
            route = Screen.AddCompetitorFromTableScreen.route + "/{$competitionIdArg}",
//            "add_competitor_from_table/{competition_id}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<AddCompetitorsFromTableViewModel>()
            AddCompetitorsFromTableRoute(navController, viewModel)
        }

        composable(
            route = Screen.DisciplinesListScreen.route + "/{$competitionIdArg}" + "/{$competitionNameArg}",
            arguments = competitionIdArgument + competitionNameArgument
        ) {
            val viewModel = hiltViewModel<DisciplinesListViewModel>()
            val competitionId = it.arguments?.getInt(competitionIdArg) ?: 0
            val competitionName = it.arguments?.getString(competitionNameArg) ?: "Дисциплины"

            DisciplineListRoute(
                navController = navController,
                viewModel = viewModel,
                competitionId = competitionId,
                competitionName = competitionName
            )
        }

        composable(
            route = Screen.AddDisciplineScreen.route + "/{$competitionIdArg}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<AddDisciplineViewModel>()
            val competitionId = it.arguments?.getInt(competitionIdArg) ?: 0
            AddDisciplineRoute(
                navController = navController,
                viewModel = viewModel,
                competitionId = competitionId
            )
        }

        composable(
            route = Screen.CompetitorsResultsListScreen.route + "/{$competitionIdArg}",
            arguments = competitionIdArgument
        ) {
            val viewModel = hiltViewModel<CompetitorsResultsViewModel>()
            val competitionId = it.arguments?.getInt(competitionIdArg) ?: 0
            CompetitorsResultsRoute(
                navController = navController,
                viewModel = viewModel,
                competitionId = competitionId
            )
        }
    }
}


