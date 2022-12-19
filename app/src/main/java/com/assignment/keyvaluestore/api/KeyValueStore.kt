package com.assignment.keyvaluestore.api

interface KeyValueStore {

    /**
     * store the value for key
     */
    fun set(key: String, value: String)
    /**
     * return the current value for key
     */
    fun get(key: String): String?
    /**
     * remove the entry for key
     */
    fun delete(key: String)
    /**
     * return the number of keys that have the given value
     */
    fun count(value: String): Int
    /**
     * start a new transaction
     */
    fun begin()
    /**
     * complete the current transaction
     */
    fun commit()
    /**
     * revert to state prior to BEGIN call
     */
    fun rollback()
}
