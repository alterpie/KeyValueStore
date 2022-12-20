package com.assignment.keyvaluestore.presentation.model

sealed interface Output {
    data class CommandMessage(val command: Command) : Output
    sealed interface ResultMessage : Output {
        object NoTransaction : ResultMessage
        object KeyNotSet : ResultMessage
        data class CommandNotRecognized(val input: String) : ResultMessage
        data class Data(val value: String) : ResultMessage
    }
}
