package com.assignment.keyvaluestore.presentation.model

sealed class Command(val name: String) {
    data class Set(val key: String, val value: String) : Command("SET")
    data class Get(val key: String) : Command("GET")
    data class Delete(val key: String) : Command("DELETE")
    data class Count(val value: String) : Command("COUNT")
    object Begin : Command("BEGIN")
    object Commit : Command("COMMIT")
    object Rollback : Command("ROLLBACK")
}
