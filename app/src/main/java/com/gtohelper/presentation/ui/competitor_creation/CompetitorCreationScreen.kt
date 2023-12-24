package com.gtohelper.presentation.ui.competitor_creation


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.components.composables.AppTextField
import com.gtohelper.presentation.components.composables.LoadingScreen
import com.gtohelper.presentation.components.composables.RadioGroup
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.components.forms.EditableState
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
                (state as FormSubmissionFailedState<CompetitorCreationForm>).error,
                Toast.LENGTH_LONG
            ).show()
        }

        if (state is FormSubmittedState) navController.navigateUp()

        if (state is FormSubmittingState) LoadingScreen(
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
                    label = stringResource(R.string.competitor_name)
                )

                Spacer(Modifier.height(18.dp))

                AppTextField(
                    value = form.teamName,
                    onValueChange = { viewModel.updateTeamName(it) },
                    label = stringResource(R.string.team_name),
                )

                Spacer(Modifier.height(18.dp))

                Text(stringResource(R.string.competitor_gender))

                RadioGroup(
                    initialValue = Gender.MALE,
                    onChanged = { viewModel.updateGender(it) },
                    values = Gender.entries,
                    nameTransform = { value -> if (value == Gender.MALE) "Мужской" else "Женский" }
                )

                Spacer(modifier = Modifier.height(18.dp))

                AppTextField(
                    label = stringResource(R.string.competitor_degree),
                    value = form.degree.toString(),
                    onValueChange = {
                        viewModel.updateDegree(it.toIntOrNull() ?: 0)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(Modifier.height(18.dp))

                AppTextField(
                    label = stringResource(R.string.competitor_number),
                    value = form.number.toString(),
                    onValueChange = {
                        viewModel.updateNumber(it.toIntOrNull() ?: 0)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

