package com.adyen.android.assignment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.adyen.android.assignment.R

val robotoSemiBold = FontFamily(
    Font(R.font.roboto_regular, FontWeight.SemiBold),
)
val robotoBold = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Bold),
)

val robotoLight = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Light),
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = robotoBold,
        fontSize = 48.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = robotoBold,
        fontSize = 22.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = robotoBold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = robotoSemiBold,
        fontSize = 32.sp
    ),
    titleMedium = TextStyle(
        fontFamily = robotoSemiBold,
        fontSize = 22.sp
    ),
    titleSmall = TextStyle(
        fontFamily = robotoSemiBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = robotoLight,
        fontSize = 22.sp
    ),
    bodySmall = TextStyle(
        fontFamily = robotoLight,
        fontSize = 14.sp
    )
)