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

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androiddevchallenge.Util.getMillis
import com.example.androiddevchallenge.Util.getMinutes
import com.example.androiddevchallenge.Util.getSeconds
import kotlin.time.seconds

class MainActivity : AppCompatActivity() {

    lateinit var jetTimer: JetTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        jetTimer = JetTimer()

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        jetTimer.resetTimer()
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    val jetTimerVM: JetTimer = viewModel()
    val progress by jetTimerVM.fractionCovered.observeAsState()
    val time by jetTimerVM.timeRemaining.observeAsState()
    val isPaused by jetTimerVM.isPaused.observeAsState()
    var minutes = remember{ mutableStateOf(0) }
    var seconds = remember{ mutableStateOf(0) }
    var millis = remember{ mutableStateOf(0) }
    val arrowSize: Dp = 30.dp
    val circleSize: Dp = 300.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFF8B07)
            )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Blue,
            elevation = 8.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.clock), contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Jet Timer",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(circleSize),
            horizontalArrangement = Arrangement.Center
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (box) = createRefs()
                Box(
                    modifier = Modifier.constrainAs(box) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    CircularProgressIndicator(
                        progress = 1F,
                        modifier = Modifier
                            .height(circleSize)
                            .width(circleSize),
                        color = Color(0xFFFFAA02),
                        strokeWidth = 20.dp
                    )
                    CircularProgressIndicator(
                        progress = progress?: 0.8F,
                        modifier = Modifier
                            .height(circleSize)
                            .width(circleSize),
                        color = Color.White,
                        strokeWidth = 20.dp
                    )
                }
                val (text) = createRefs()
                Text(
                    text = "${time?.getMinutes()} : ${time?.getSeconds()} : ${time?.getMillis()}",
                    color = Color.White,
                    fontWeight = FontWeight.W600,
                    fontSize = 35.sp,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }

        }
        
        Spacer(modifier = Modifier.height(30.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_up),
                    contentDescription = "increment",
                    modifier = Modifier
                        .width(arrowSize)
                        .height(arrowSize)
                        .clickable(onClick = { minutes.value++ })
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White),
                ) {
                    Text(
                        text = if(minutes.value < 10) "0${minutes.value}" else "${minutes.value}",
                        color = Color(0xFFFF8B07),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.chevron_down),
                    contentDescription = "decrement",
                    modifier = Modifier
                        .width(arrowSize)
                        .height(arrowSize)
                        .clickable(onClick = { minutes.value-- })
                )
            }

            Spacer(modifier = Modifier.width(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_up),
                    contentDescription = "increment",
                    modifier = Modifier
                        .width(arrowSize)
                        .height(arrowSize)
                        .clickable(onClick = { seconds.value++ })
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White),
                ) {
                    Text(
                        text = if(seconds.value < 10) "0${seconds.value}" else "${seconds.value}",
                        color = Color(0xFFFF8B07),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.chevron_down),
                    contentDescription = "decrement",
                    modifier = Modifier
                        .width(arrowSize)
                        .height(arrowSize)
                        .clickable(onClick = { seconds.value-- })
                )
            }

            Spacer(modifier = Modifier.width(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_up),
                    contentDescription = "increment",
                    modifier = Modifier
                        .width(arrowSize)
                        .height(arrowSize)
                        .clickable(onClick = { millis.value++ })
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White),
                ) {
                    Text(
                        text = if(millis.value < 10) "0${millis.value}" else "${millis.value}",
                        color = Color(0xFFFF8B07),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.chevron_down),
                    contentDescription = "decrement",
                    modifier = Modifier
                        .width(arrowSize)
                        .height(arrowSize)
                        .clickable(onClick = { millis.value-- })
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { if(seconds.value > 0) jetTimerVM.startTimer(seconds.value) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            ) {
                Text(
                    text = "Start",
                    color = Color(0xFFFF8B07),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
            Button(onClick = { jetTimerVM.pauseResume() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = if(isPaused!!) "Resume" else "Pause",
                    color = Color(0xFFFF8B07),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Button(onClick = { jetTimerVM.resetTimer() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                Text(
                    text = "Reset",
                    color = Color(0xFFFF8B07),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}