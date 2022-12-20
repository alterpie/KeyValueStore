package com.assignment.keyvaluestore.presentation

import com.assignment.keyvaluestore.api.store.KeyValueStore
import com.assignment.keyvaluestore.presentation.model.Command
import com.assignment.keyvaluestore.presentation.model.Output
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KeyValueStoreViewModel(
    private val keyValueStore: KeyValueStore,
    private val commandParser: CommandParser,
) {

    private val _state = MutableStateFlow(listOf<Output>())
    val state: StateFlow<List<Output>> = _state

    fun submitCommand(commandRaw: String) {
        val command = commandParser.parse(commandRaw)
        val outputs = _state.value.toMutableList()
        if (command != null) {
            outputs.add(Output.CommandMessage(command))
            when (command) {
                Command.Begin -> keyValueStore.beginTransaction()
                Command.Commit -> {
                    val message = if (!keyValueStore.commitTransaction()) {
                        Output.ResultMessage.NoTransaction
                    } else {
                        null
                    }
                    message?.let(outputs::add)
                }
                is Command.Count -> {
                    val count = keyValueStore.count(command.value)
                    outputs.add(Output.ResultMessage.Data(count.toString()))
                }
                is Command.Delete -> keyValueStore.delete(command.key)
                is Command.Get -> {
                    val value = keyValueStore.get(command.key)
                    val message = if (value != null) {
                        Output.ResultMessage.Data(value)
                    } else {
                        Output.ResultMessage.KeyNotSet
                    }
                    outputs.add(message)
                }
                Command.Rollback -> {
                    val message = if (!keyValueStore.rollbackTransaction()) {
                        Output.ResultMessage.NoTransaction
                    } else {
                        null
                    }
                    message?.let(outputs::add)
                }
                is Command.Set -> {
                    keyValueStore.set(command.key, command.value)
                }
            }
        } else {
            outputs.add(Output.ResultMessage.CommandNotRecognized(commandRaw))
        }
        _state.value = outputs
    }
}
