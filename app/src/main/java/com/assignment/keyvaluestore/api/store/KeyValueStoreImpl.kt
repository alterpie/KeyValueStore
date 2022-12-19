package com.assignment.keyvaluestore.api.store

import com.assignment.keyvaluestore.api.transaction.Transaction
import java.util.LinkedList

class KeyValueStoreImpl : KeyValueStore {

    private val store = mutableMapOf<String, String>()
    private val transactions = LinkedList<Transaction>()

    override fun set(key: String, value: String) {
        getLastTransaction()?.set(key, value) ?: kotlin.run { store[key] = value }
    }

    override fun get(key: String): String? {
        return getLastTransaction()?.get(key) ?: store[key]
    }

    override fun delete(key: String) {
        getLastTransaction()?.delete(key) ?: kotlin.run { store.remove(key) }
    }

    override fun count(value: String): Int {
        val lastTransaction = getLastTransaction()
        val store = lastTransaction?.store ?: store
        return store.values.count { it == value }
    }

    override fun beginTransaction() {
        TODO("Not yet implemented")
    }

    override fun commitTransaction(): Boolean {
        TODO("Not yet implemented")
    }

    override fun rollbackTransaction(): Boolean {
        TODO("Not yet implemented")
    }

    private fun getLastTransaction(): Transaction? {
        return transactions.lastOrNull()
    }
}
