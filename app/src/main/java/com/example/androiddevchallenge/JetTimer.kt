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

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JetTimer : ViewModel() {

    var started = false
    var paused = false
    var millisLeft: Long = 0L
    var millisTotal: Long = 0L
    lateinit var timerr: CountDownTimer

    private val _fractionCovered: MutableLiveData<Float> = MutableLiveData()
    val fractionCovered: LiveData<Float> get() = _fractionCovered

    private val _timeRemaining: MutableLiveData<Long> = MutableLiveData()
    val timeRemaining: LiveData<Long> get() = _timeRemaining

    private val _isPaused: MutableLiveData<Boolean> = MutableLiveData()
    val isPaused: LiveData<Boolean> get() = _isPaused

    init {
        _fractionCovered.value = 100F
        _timeRemaining.value = 0L
        _isPaused.value = false
    }

    fun initTimer(millisRemaining: Long) {

        timerr = object : CountDownTimer(millisRemaining, 30) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("timer ", "$millisUntilFinished")
                millisLeft = millisUntilFinished
                val fraction: Float = millisLeft / millisTotal.toFloat()
                _fractionCovered.value = fraction
                _timeRemaining.value = millisUntilFinished
            }

            override fun onFinish() {
                Log.d("timer ", "finished")
                started = !started
                // millisLeft = 0
                _fractionCovered.value = 0F
                _timeRemaining.value = 0L
            }
        }
    }

    fun startTimer(duration: Int) {
        if (!started) {
            started = !started
            millisTotal = duration * 1000L
            initTimer(millisTotal)
            timerr.start()
            _isPaused.value = false
        }
    }

    fun resetTimer() {
        if (started) {
            started = !started
            timerr.cancel()
            paused = false
            millisLeft = 0L
            _fractionCovered.value = 100F
            _timeRemaining.value = millisTotal
            _isPaused.value = false
        }
    }

    fun pauseResume() {
        if (started) {
            if (!paused) {
                // running state, pause timer
                timerr.cancel()
                paused = !paused
                _isPaused.value = true
            } else {
                // paused state, resume timer
                initTimer(millisLeft)
                timerr.start()
                paused = !paused
                _isPaused.value = false
            }
        }
    }
}
