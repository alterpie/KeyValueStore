package com.assignment.keyvaluestore.api.transaction

import org.junit.Assert
import org.junit.Test

class TransactionImplTest {

    @Test
    fun `should set and return value`() {
        val transaction = TransactionImpl(mapOf())

        transaction.set("foo", "123")
        Assert.assertEquals("123", transaction.get("foo"))
    }

    @Test
    fun `should delete value`() {
        val transaction = TransactionImpl(mapOf())

        transaction.set("foo", "123")
        transaction.delete("foo")
        Assert.assertEquals(null, transaction.get("foo"))
    }
}
