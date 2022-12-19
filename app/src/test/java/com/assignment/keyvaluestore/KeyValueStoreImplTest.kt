package com.assignment.keyvaluestore

import com.assignment.keyvaluestore.api.store.KeyValueStoreImpl
import org.junit.Assert
import org.junit.Test

class KeyValueStoreImplTest {

    @Test
    fun `should set and return value`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        Assert.assertEquals(keyValueStore.get("foo"), "123")
    }

    @Test
    fun `should delete value`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.delete("foo")
        Assert.assertEquals(keyValueStore.get("foo"), null)
    }

    @Test
    fun `should return count of occurrences of a value`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.set("bar", "456")
        keyValueStore.set("baz", "123")
        Assert.assertEquals(keyValueStore.count("123"), 2)
        Assert.assertEquals(keyValueStore.count("456"), 1)
    }

    @Test
    fun `should commit transaction`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.beginTransaction()
        keyValueStore.set("foo", "456")
        keyValueStore.commitTransaction()
        Assert.assertEquals(keyValueStore.rollbackTransaction(), false)
        Assert.assertEquals(keyValueStore.get("foo"), "456")
    }

    @Test
    fun `should rollback a transaction`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.set("bar", "abc")
        keyValueStore.beginTransaction()
        keyValueStore.set("foo", "456")
        Assert.assertEquals(keyValueStore.get("foo"), "456")
        keyValueStore.set("bar", "def")
        Assert.assertEquals(keyValueStore.get("bar"), "def")
        Assert.assertEquals(keyValueStore.rollbackTransaction(), true)
        Assert.assertEquals(keyValueStore.get("foo"), "123")
        Assert.assertEquals(keyValueStore.get("bar"), "abc")
        Assert.assertEquals(keyValueStore.commitTransaction(), false)
    }

    @Test
    fun `should handle nested transactions`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.beginTransaction()
        keyValueStore.set("bar", "456")
        keyValueStore.set("foo", "456")
        keyValueStore.beginTransaction()
        Assert.assertEquals(keyValueStore.get("456"), 2)
        Assert.assertEquals(keyValueStore.get("foo"), "456")
        keyValueStore.set("foo", "789")
        Assert.assertEquals(keyValueStore.get("foo"), "789")
        Assert.assertEquals(keyValueStore.rollbackTransaction(), true)
        Assert.assertEquals(keyValueStore.get("foo"), "456")
        Assert.assertEquals(keyValueStore.rollbackTransaction(), true)
        Assert.assertEquals(keyValueStore.get("foo"), "123")
    }
}
