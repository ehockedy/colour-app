package com.hueval.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = Typography().bodySmall.copy(
        fontFamily = fontFamily,
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = fontFamily,
    ),
    // Default text uses this
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        baselineShift = BaselineShift(-0.2f)
    ),
    // Button uses this: https://m3.material.io/components/buttons/specs
    labelLarge = Typography().labelLarge.copy(
        fontSize = 16.sp,
        fontFamily = fontFamily,
    ),
)