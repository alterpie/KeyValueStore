package com.assignment.keyvaluestore.api.transaction

internal class TransactionImpl(originalStore: Map<String, String>) : Transaction {
    private val _store = mutableMapOf<String, String>()

    init {
        originalStore.entries.associateTo(_store) { (key, value) -> key to value }
    }

    override val store: Map<String, String> = _store

    override fun set(key: String, value: String) {
        _store[key] = value
    }

    override fun get(key: String): String? {
        return _store[key]
    }

    override fun delete(key: String) {
        _store.remove(key)
    }
}
