package com.gtohelper.presentation.ui.competitor_creation


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.components.AppTextField
import com.gtohelper.presentation.components.LoadingScreen
import com.gtohelper.presentation.components.RadioGroup
import com.gtohelper.presentation.components.TransparentAddFab
import com.gtohelper.presentation.components.forms.EditableState
import com.gtohelper.presentation.components.forms.FormEditingState
import com.gtohelper.presentation.components.forms.FormSubmissionFailedState
import com.gtohelper.presentation.components.forms.FormSubmittedState
import com.gtohelper.presentation.components.forms.FormSubmittingState


@Composable
fun CompetitorCreationScreen(
    navController: NavController,
    viewModel: CompetitorCreationViewModel,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        floatingActionButton = {
            TransparentAddFab(
                contentDescription = stringResource(R.string.save_new_competitor),
                onClick = viewModel::submit
            )
        }
    ) { padding ->
        val state by viewModel.state.collectAsState()
        val form = state.form
        if (state is FormSubmissionFailedState) {
            Toast.makeText(
                LocalContext.current,
                "Some error",
                Toast.LENGTH_LONG
            ).show()
        }

        if (state is FormSubmittedState) navController.navigateUp()
        else if (state is FormSubmittingState) LoadingScreen(
            Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#F3F3F3")))
                .padding(padding)
        )

        if (state is EditableState) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(android.graphics.Color.parseColor("#F3F3F3")))
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
            ) {
                AppTextField(
                    value = form.name,
                    onValueChange = { viewModel.updateName(it) },
                    hint = stringResource(R.string.enter_name)
                )

                Spacer(modifier = Modifier.height(18.dp))

                AppTextField(
                    value = form.teamName,
                    onValueChange = { viewModel.updateTeamName(it) },
                    hint = stringResource(R.string.enter_team_name),
                )

                Spacer(modifier = Modifier.height(18.dp))

                RadioGroup(
                    initialValue = Gender.MALE,
                    onChanged = {
//                        viewModel.updateGender(it)
                                },
                    values = Gender.entries
                )

                Spacer(modifier = Modifier.height(18.dp))

                AppTextField(
                    hint = stringResource(R.string.enter_competitor_degree),
                    value = form.degree.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly()) viewModel.updateDegree(it.toInt())
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(18.dp))

                AppTextField(
                    hint = stringResource(R.string.enter_competitor_number),
                    value = form.number.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly()) viewModel.updateNumber(it.toInt())
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

