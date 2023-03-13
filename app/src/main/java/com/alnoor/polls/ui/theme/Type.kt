package com.alnoor.polls.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alnoor.polls.R

val fontFamily = FontFamily(
    Font(resId = R.font.tilt)
)

fun TextStyle.tiltFont(): TextStyle{
    return this.copy(
        fontFamily = fontFamily
    )
}

// Set of Material typography styles mapTo start with
val Typography = Typography()