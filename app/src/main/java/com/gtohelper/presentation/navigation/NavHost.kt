package com.gtohelper.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gtohelper.presentation.ui.competitions.CompetitionListRoute
import com.gtohelper.presentation.ui.competitions.CompetitionListViewModel
import com.gtohelper.presentation.ui.competitions.add_competition.AddCompetitionRoute
import com.gtohelper.presentation.ui.competitions.add_competition.AddCompetitionViewModel
import com.gtohelper.presentation.ui.competitions.edit_competition.EditCompetitionRoute
import com.gtohelper.presentation.ui.competitions.edit_competition.EditCompetitionViewModel
import com.gtohelper.presentation.ui.competitors_list.CompetitorListRoute
import com.gtohelper.presentation.ui.competitors_list.CompetitorsListViewModel
import com.gtohelper.presentation.ui.competitors_list.add_competitor.AddCompetitorRoute
import com.gtohelper.presentation.ui.competitors_list.add_competitor.AddCompetitorViewModel
import com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table.AddCompetitorsFromTableRoute
import com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table.AddCompetitorsFromTableViewModel
import com.gtohelper.presentation.ui.competitors_list.edit_competitor.EditCompetitorRoute
import com.gtohelper.presentation.ui.competitors_list.edit_competitor.EditCompetitorViewModel
import com.gtohelper.presentation.ui.competitors_results.CompetitorsResultsRoute
import com.gtohelper.presentation.ui.competitors_results.CompetitorsResultsViewModel
import com.gtohelper.presentation.ui.disciplines_list.DisciplineListRoute
import com.gtohelper.presentation.ui.disciplines_list.DisciplinesListViewModel
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.AddDisciplineRoute
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.AddDisciplineViewModel
import com.gtohelper.presentation.ui.disciplines_list.add_results.AddResultsRoute
import com.gtohelper.presentation.ui.disciplines_list.add_results.AddResultsViewModel
import com.gtohelper.presentation.ui.disciplines_list.add_results.edit_result.EditResultDialogRoute
import com.gtohelper.presentation.ui.disciplines_list.add_results.edit_result.EditResultViewModel
import com.gtohelper.presentation.ui.help.HelpRoute


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AppNavHost(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val navController = rememberNavController()
    val competitionIdArg = "competition_id"
    val competitionNameArg = "competition_name"
    val competitorIdArg = "competitor_id"
    val disciplineIdArg = "discipline_id"
    val sportResultIdArg = "sport_result_id"

    val competitionIdArgument =
        remember { listOf(navArgument(competitionIdArg) { type = NavType.IntType }) }

    val competitionNameArgument =
        remember { listOf(navArgument(competitionNameArg) { type = NavType.StringType }) }

    val competitorIdArgument =
        remember { listOf(navArgument(competitorIdArg) { type = NavType.IntType }) }

    val disciplineIdArgument =
        remember { listOf(navArgument(disciplineIdArg) { type = NavType.IntType }) }

    val sportResultIdArgument =
        remember { listOf(navArgument(sportResultIdArg) { type = NavType.IntType }) }

    val transitionEnterDuration = 100
    val transitionExitDuration = 100

    NavHost(
        navController = navController,
        startDestination = Screen.CompetitionsScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        composable(
            route = Screen.CompetitionsScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<CompetitionListViewModel>()
            CompetitionListRoute(navController, viewModel)
        }

        composable(
            route = Screen.AddCompetitionScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<AddCompetitionViewModel>()
            AddCompetitionRoute(navController, viewModel)
        }

        composable(
            route = Screen.EditCompetitionScreen.withRouteArgs(competitionIdArg),
            arguments = competitionIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<EditCompetitionViewModel>()
            EditCompetitionRoute(navController, viewModel)
        }

        composable(
            route = Screen.CompetitorsListScreen.withRouteArgs(competitionIdArg),
            arguments = competitionIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<CompetitorsListViewModel>()
            val competitionId = it.arguments?.getInt(competitionIdArg) ?: 0
            CompetitorListRoute(navController, viewModel, competitionId)
        }

        composable(
            route = Screen.AddCompetitorScreen.withRouteArgs(competitionIdArg),
            arguments = competitionIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<AddCompetitorViewModel>()
            AddCompetitorRoute(navController, viewModel)
        }

        composable(
            route = Screen.AddCompetitorFromTableScreen.withRouteArgs(competitionIdArg),
            arguments = competitionIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<AddCompetitorsFromTableViewModel>()
            AddCompetitorsFromTableRoute(navController, viewModel)
        }

        composable(
            route = Screen.DisciplinesListScreen.withRouteArgs(
                competitionIdArg,
                competitionNameArg
            ),
            arguments = competitionIdArgument + competitionNameArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
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
            route = Screen.AddDisciplineScreen.withRouteArgs(competitionIdArg),
            arguments = competitionIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<AddDisciplineViewModel>()
            AddDisciplineRoute(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(
            route = Screen.EditCompetitorScreen.withRouteArgs(competitorIdArg),
            arguments = competitorIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<EditCompetitorViewModel>()
            EditCompetitorRoute(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(
            route = Screen.CompetitorsResultsListScreen.withRouteArgs(competitionIdArg),
            arguments = competitionIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<CompetitorsResultsViewModel>()
            CompetitorsResultsRoute(
                navController = navController,
                viewModel = viewModel,
            )
        }

        dialog(
            route = Screen.EditResultScreen.withRouteArgs(
                competitionIdArg,
                disciplineIdArg,
                sportResultIdArg
            ),
            arguments = competitionIdArgument + disciplineIdArgument + sportResultIdArgument,
            dialogProperties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            val editResultViewModel = hiltViewModel<EditResultViewModel>()
            EditResultDialogRoute(navController, editResultViewModel)
        }

        composable(
            route = Screen.AddResultsScreen.withRouteArgs(competitionIdArg, disciplineIdArg),
            arguments = competitionIdArgument + disciplineIdArgument,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            val viewModel = hiltViewModel<AddResultsViewModel>()
            val competitionId = it.arguments?.getInt(competitionIdArg) ?: 0
            val disciplineId = it.arguments?.getInt(disciplineIdArg) ?: 0

            AddResultsRoute(
                navController = navController,
                viewModel = viewModel,
                competitionId = competitionId,
                disciplineId = disciplineId,
            )
        }

        composable(
            route = Screen.HelpScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = transitionEnterDuration,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(transitionEnterDuration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = transitionExitDuration,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(transitionExitDuration, easing = EaseOut)
                )
            }
        ) {
            HelpRoute(
                navController = navController,
            )
        }
    }
}