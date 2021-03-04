/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

object Util {

    fun Long.getMinutes(): String {
        if (this.div(1000000L) < 10) {
            return "0${this.div(1000000L)}"
        }
        return "${this.div(1000000L)}"
    }

    fun Long.getSeconds(): String {
        if (this.div(1000L) < 10) {
            return "0${this.div(1000L)}"
        }
        return "${this.div(1000L)}"
    }

    fun Long.getMillis(): String {
        val l = this % 1000L
        if (l.div(10L) < 10) {
            return "${l.div(10L)}0"
        }
        return "${l.div(10L)}"
    }
}
