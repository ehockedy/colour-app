package com.hueval.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import com.hueval.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Poppins")
val poppinsFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

val dongleFamily = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.dongle_light, FontWeight.Light),
)

val fontFamily = dongleFamily

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = Typography().bodySmall.copy(
        fontFamily = fontFamily,
        fontSize = 24.sp,
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = fontFamily,
        fontSize = 28.sp,
    ),
    // Default text uses this
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp,
        baselineShift = BaselineShift(-0.2f),
    ),
    // Button uses this: https://m3.material.io/components/buttons/specs
    labelLarge = Typography().labelLarge.copy(
        fontSize = 32.sp,
        fontFamily = fontFamily,
    ),
)