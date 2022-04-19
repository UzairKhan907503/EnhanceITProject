package com.enhanceit.core.ext

import java.util.concurrent.TimeUnit


fun String.compareToCurrent() : Boolean{
    val time = this.toLongOrNull()
    time?.let {
        if(System.currentTimeMillis().toHours() - it.toHours() > 1) return true
    }
    return false
}

fun Long.toHours() = TimeUnit.MILLISECONDS.toHours(this)