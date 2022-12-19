package com.assignment.keyvaluestore.api.store

import org.junit.Assert
import org.junit.Test

class KeyValueStoreImplTest {

    @Test
    fun `should set and return value`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        Assert.assertEquals("123", keyValueStore.get("foo"))
    }

    @Test
    fun `should delete value`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.delete("foo")
        Assert.assertEquals(null, keyValueStore.get("foo"))
    }

    @Test
    fun `should return count of occurrences of a value`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.set("bar", "456")
        keyValueStore.set("baz", "123")
        Assert.assertEquals(2, keyValueStore.count("123"))
        Assert.assertEquals(1, keyValueStore.count("456"))
    }

    @Test
    fun `should commit transaction`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.beginTransaction()
        keyValueStore.set("foo", "456")
        keyValueStore.commitTransaction()
        Assert.assertEquals(false, keyValueStore.rollbackTransaction())
        Assert.assertEquals("456", keyValueStore.get("foo"))
    }

    @Test
    fun `should rollback a transaction`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.set("bar", "abc")
        keyValueStore.beginTransaction()
        keyValueStore.set("foo", "456")
        Assert.assertEquals("456", keyValueStore.get("foo"))
        keyValueStore.set("bar", "def")
        Assert.assertEquals("def", keyValueStore.get("bar"))
        Assert.assertEquals(true, keyValueStore.rollbackTransaction())
        Assert.assertEquals("123", keyValueStore.get("foo"))
        Assert.assertEquals("abc", keyValueStore.get("bar"))
        Assert.assertEquals(false, keyValueStore.commitTransaction())
    }

    @Test
    fun `should handle nested transactions`() {
        val keyValueStore = KeyValueStoreImpl()

        keyValueStore.set("foo", "123")
        keyValueStore.beginTransaction()
        keyValueStore.set("bar", "456")
        keyValueStore.set("foo", "456")
        keyValueStore.beginTransaction()
        Assert.assertEquals(2, keyValueStore.count("456"))
        Assert.assertEquals("456", keyValueStore.get("foo"))
        keyValueStore.set("foo", "789")
        Assert.assertEquals("789", keyValueStore.get("foo"))
        Assert.assertEquals(true, keyValueStore.rollbackTransaction())
        Assert.assertEquals("456", keyValueStore.get("foo"))
        Assert.assertEquals(true, keyValueStore.rollbackTransaction())
        Assert.assertEquals("123", keyValueStore.get("foo"))
    }
}
