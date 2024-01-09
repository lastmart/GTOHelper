package com.gtohelper.presentation.ui.competitors_list.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.components.composables.menus.AppDropdownMenu
import com.gtohelper.presentation.components.composables.groups.AppRadioGroup
import com.gtohelper.presentation.components.composables.input_fields.AppTextField
import com.gtohelper.presentation.ui.util.toRoman


@Composable
fun CompetitorUiForm(
    modifier: Modifier,
    form: CompetitorFormState,
    onEvent: (CompetitorFormEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {

        AppTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            value = form.name,
            onValueChange = { onEvent(CompetitorFormEvent.UpdateName(it)) },
            label = stringResource(R.string.competitor_name_title),
            maxLength = 50,
        )

        Spacer(Modifier.height(18.dp))

        AppTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            value = form.teamName,
            onValueChange = { onEvent(CompetitorFormEvent.UpdateTeamName(it)) },
            label = stringResource(R.string.team_name_title),
            maxLength = 50,
        )

        Spacer(Modifier.height(18.dp))

        AppTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.competitor_number),
            value = if (form.number == null) "" else form.number.toString(),
            onValueChange = { value ->
                onEvent(
                    CompetitorFormEvent.UpdateNumber(value.toIntOrNull())
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLength = 4,
        )

        Spacer(modifier = Modifier.height(18.dp))
        AppDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.competitor_degree_title),
            selected = form.degree,
            values = (2..18).toList(),
            onValueChanged = { onEvent(CompetitorFormEvent.UpdateDegree(it)) },
            stringTransform = { value -> value.toRoman()?.let { "$it ступень" } ?: "-" }
        )

        Spacer(Modifier.height(18.dp))

        Row {

            Text(
                text = stringResource(R.string.gender_title),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 20.sp,
            )
            Spacer(Modifier.width(10.dp))
            AppRadioGroup(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                selectedValue = form.gender,
                onChanged = { onEvent(CompetitorFormEvent.UpdateGender(it)) },
                values = Gender.entries,
                nameTransform = { value -> if (value == Gender.MALE) "Мужской" else "Женский" })
        }

        Spacer(Modifier.height(18.dp))
    }
}

@Preview
@Composable
fun PreviewCompetitorForm() {
    CompetitorUiForm(
        modifier = Modifier
            .fillMaxWidth(),
        form = CompetitorFormState(),
        onEvent = {}
    )
}