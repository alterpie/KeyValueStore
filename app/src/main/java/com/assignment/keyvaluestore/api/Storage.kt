package com.assignment.keyvaluestore.api

interface Storage {
    /**
     * store the value for key.
     */
    fun set(key: String, value: String)

    /**
     * return the current value for key.
     */
    fun get(key: String): String?

    /**
     * remove the entry for key.
     */
    fun delete(key: String)
}
