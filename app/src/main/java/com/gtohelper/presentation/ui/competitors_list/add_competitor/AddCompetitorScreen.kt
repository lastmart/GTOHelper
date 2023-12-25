package com.gtohelper.presentation.ui.competitors_list.add_competitor


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.components.composables.AppTextField
import com.gtohelper.presentation.components.composables.RadioGroup
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.components.forms.FormState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewAddCompetitor() {
    AddCompetitorContent(
        modifier = Modifier.fillMaxWidth(),
        form = AddCompetitorFormState(),
        onEvent = {}
    )
}


@Composable
fun AddCompetitorScreen(
    navController: NavController,
    viewModel: AddCompetitorViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.formState.collect { event ->
            Log.i("ADD_COMPETITION_SCREEN", "Event happened: $event")
            if (event is FormState.FormSubmissionFailedState) {
                snackbarHostState.currentSnackbarData?.dismiss()
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(event.error)
                }
            }
            if (event is FormState.FormSubmittedState) {
                navController.navigateUp()
            }
        }
    }

    val form by viewModel.form.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        floatingActionButton = {
            TransparentAddFab(
                contentDescription = stringResource(R.string.save_new_competitor),
                onClick = { viewModel.onEvent(AddCompetitorEvent.Submit) },
            )
        }) {

        AddCompetitorContent(
            form = form,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(it),
        )
    }

}

// TODO: Make it work?
//@Composable
//private fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            withContext(Dispatchers.Main.immediate) {
//                flow.collect(onEvent)
//            }
//        }
//    }
//}

@Composable
fun AddCompetitorContent(
    modifier: Modifier,
    form: AddCompetitorFormState,
    onEvent: (AddCompetitorEvent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {

        AppTextField(
            value = form.name,
            onValueChange = { onEvent(AddCompetitorEvent.UpdateName(it)) },
            label = stringResource(R.string.competitor_name),
            maxLength = 10,
        )

        Spacer(Modifier.height(18.dp))

        AppTextField(
            value = form.teamName,
            onValueChange = { onEvent(AddCompetitorEvent.UpdateTeamName(it)) },
            label = stringResource(R.string.team_name),
            maxLength = 10,
        )

        Spacer(Modifier.height(18.dp))

        Text(stringResource(R.string.competitor_gender))

        RadioGroup(selectedValue = form.gender,
            onChanged = { onEvent(AddCompetitorEvent.UpdateGender(it)) },
            values = Gender.entries,
            nameTransform = { value -> if (value == Gender.MALE) "Мужской" else "Женский" })

        Spacer(modifier = Modifier.height(18.dp))

        AppTextField(
            label = stringResource(R.string.competitor_degree),
            value = form.degree.toString(),
            onValueChange = { value ->
                onEvent(
                    AddCompetitorEvent.UpdateDegree(
                        (value.toIntOrNull() ?: 0)
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLength = 10,
        )

        Spacer(Modifier.height(18.dp))

        AppTextField(
            label = stringResource(R.string.competitor_number),
            value = form.number.toString(),
            onValueChange = { value ->
                onEvent(
                    AddCompetitorEvent.UpdateNumber(
                        (value.toIntOrNull() ?: 0)
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLength = 10,
        )
    }
}

