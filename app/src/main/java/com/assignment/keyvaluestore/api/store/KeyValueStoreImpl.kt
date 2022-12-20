package com.assignment.keyvaluestore.api.store

import com.assignment.keyvaluestore.api.transaction.Transaction
import com.assignment.keyvaluestore.api.transaction.TransactionImpl
import java.util.LinkedList

internal class KeyValueStoreImpl : KeyValueStore {

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
        transactions.add(TransactionImpl(transactions.lastOrNull()?.store ?: store))
    }

    override fun commitTransaction(): Boolean {
        val lastTransaction = transactions.pollLast() ?: return false
        store.clear()
        store.putAll(lastTransaction.store)
        return true
    }

    override fun rollbackTransaction(): Boolean {
        return transactions.pollLast() != null
    }

    private fun getLastTransaction(): Transaction? {
        return transactions.lastOrNull()
    }
}
