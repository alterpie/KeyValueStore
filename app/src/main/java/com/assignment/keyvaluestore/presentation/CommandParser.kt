package com.assignment.keyvaluestore.presentation

import com.assignment.keyvaluestore.presentation.model.Command

class CommandParser {

    fun parse(rawValue: String): Command? {
        val parts = rawValue.split(" ")
        return when (parts.size) {
            3 -> {
                Command.Set(parts[1], parts[2])
            }
            2 -> {
                when (parts[0]) {
                    "GET" -> Command.Get(parts[1])
                    "DELETE" -> Command.Delete(parts[1])
                    "COUNT" -> Command.Count(parts[1])
                    else -> null
                }
            }
            1 -> {
                when (parts[0]) {
                    "BEGIN" -> Command.Begin
                    "COMMIT" -> Command.Commit
                    "ROLLBACK" -> Command.Rollback
                    else -> null
                }
            }
            else -> null
        }
    }
}
