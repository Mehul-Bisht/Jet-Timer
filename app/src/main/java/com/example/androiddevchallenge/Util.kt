package com.example.androiddevchallenge

import com.example.androiddevchallenge.Util.getSeconds
import kotlin.math.absoluteValue

object Util {

    fun Long.getMinutes(): String {
        if(this.div(1000000L) < 10) {
            return "0${this.div(1000000L)}"
        }
        return "${this.div(1000000L)}"
    }

    fun Long.getSeconds(): String {
        if(this.div(1000L) < 10) {
            return "0${this.div(1000L)}"
        }
        return "${this.div(1000L)}"
    }

    fun Long.getMillis(): String {
        val l = this % 1000L
        if(l.div(10L) < 10) {
            return "${l.div(10L)}0"
        }
        return "${l.div(10L)}"
    }

}