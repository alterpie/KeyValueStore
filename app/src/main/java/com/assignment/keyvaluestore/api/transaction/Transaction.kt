package com.assignment.keyvaluestore.api.transaction

import com.assignment.keyvaluestore.api.Storage

interface Transaction : Storage {
    val store: Map<String, String>
}
