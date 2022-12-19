package com.assignment.keyvaluestore.api

import java.util.LinkedList

class KeyValueStoreImpl : KeyValueStore {

    private val store = mutableMapOf<String, String>()
    private val transactions = LinkedList<Transaction>()

    override fun set(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun get(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun delete(key: String) {
        TODO("Not yet implemented")
    }

    override fun count(value: String): Int {
        TODO("Not yet implemented")
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

    private class Transaction() {

    }
}
