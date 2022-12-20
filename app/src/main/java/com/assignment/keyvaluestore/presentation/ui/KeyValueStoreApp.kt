package com.assignment.keyvaluestore.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.assignment.keyvaluestore.R
import com.assignment.keyvaluestore.api.store.KeyValueStoreImpl
import com.assignment.keyvaluestore.presentation.CommandParser
import com.assignment.keyvaluestore.presentation.KeyValueStoreViewModel
import com.assignment.keyvaluestore.presentation.model.Command
import com.assignment.keyvaluestore.presentation.model.Output

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeyValueStoreApp() {
    val viewModel = remember {
        KeyValueStoreViewModel(KeyValueStoreImpl(), CommandParser())
    }
    val outputs by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()

    val isImeVisible = WindowInsets.isImeVisible
    LaunchedEffect(outputs.size, isImeVisible) {
        if (isImeVisible && outputs.isNotEmpty()) {
            lazyListState.animateScrollToItem(outputs.size - 1)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        OutputSection(state = lazyListState, items = outputs)
        InputSection(onSubmitCommand = { textInput ->
            viewModel.submitCommand(textInput)
        })
    }
}

@Composable
private fun ColumnScope.OutputSection(state: LazyListState, items: List<Output>) {
    LazyColumn(
        state = state,
        modifier = Modifier.weight(1f),
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
    ) {
        items(items) { item ->
            when (item) {
                is Output.CommandMessage -> CommandMessageItem(item)
                is Output.ResultMessage -> ResultMessageItem(item)
            }
        }
    }
}

@Composable
private fun CommandMessageItem(item: Output.CommandMessage) {
    val textValue = when (item.command) {
        is Command.Begin, is Command.Commit, is Command.Rollback -> item.command.name
        is Command.Count -> "${item.command.name} ${item.command.value}"
        is Command.Delete -> "${item.command.name} ${item.command.key}"
        is Command.Get -> "${item.command.name} ${item.command.key}"
        is Command.Set -> "${item.command.name} ${item.command.key} ${item.command.value}"
    }
    Text(
        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
        text = stringResource(R.string.output_command_message_template, textValue),
        color = MaterialTheme.colors.onBackground,
    )
}

@Composable
private fun ResultMessageItem(item: Output.ResultMessage) {
    Text(
        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
        text = item.stringValue(),
        color = MaterialTheme.colors.onBackground,
    )
}

@Composable
private fun InputSection(onSubmitCommand: (String) -> Unit) {
    var textValue by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        value = textValue,
        onValueChange = { textValue = it },
        singleLine = true,
        placeholder = {
            Text(text = stringResource(R.string.enter_command))
        },
        keyboardActions = KeyboardActions(onDone = {
            onSubmitCommand(textValue.trim())
            textValue = ""
        }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = MaterialTheme.colors.onBackground)
    )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .padding(16.dp),
        onClick = {
            onSubmitCommand(textValue.trim())
            textValue = ""
        },
    ) {
        Text(text = stringResource(R.string.submit))
    }
}

@Composable
private fun Output.ResultMessage.stringValue(): String {
    return when (this) {
        is Output.ResultMessage.CommandNotRecognized -> stringResource(
            R.string.command_not_recognized,
            this.input
        )
        is Output.ResultMessage.Data -> this.value
        Output.ResultMessage.KeyNotSet -> stringResource(R.string.key_not_set)
        Output.ResultMessage.NoTransaction -> stringResource(R.string.no_transaction)
    }
}
