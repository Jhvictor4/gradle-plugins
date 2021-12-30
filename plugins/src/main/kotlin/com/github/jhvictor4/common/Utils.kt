package com.github.jhvictor4.common

import java.io.ByteArrayOutputStream

object Utils {
    val String?.validOrNull
        get() = takeUnless { it.isNullOrBlank() }

    val ByteArrayOutputStream.result: String
        get() = toString().trim()
}