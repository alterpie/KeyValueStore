package com.assignment.keyvaluestore.api.store

import com.assignment.keyvaluestore.api.Storage
import com.assignment.keyvaluestore.api.Transactionable

interface KeyValueStore : Transactionable, Storage {

    /**
     * return the number of keys that have the given value.
     */
    fun count(value: String): Int
}
