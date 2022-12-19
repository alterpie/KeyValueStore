package com.assignment.keyvaluestore.api

interface Transactionable {
    /**
     * start a new transaction.
     */
    fun beginTransaction()

    /**
     * complete the current transaction.
     *
     * @return true if transaction was committed, false if there was no transactions.
     */
    fun commitTransaction(): Boolean

    /**
     * revert to state prior to BEGIN call.
     *
     * @return true if transaction was rollbacked, false if there was no transactions.
     */
    fun rollbackTransaction(): Boolean
}
