package com.example.nammashasana.data

data class Inscription(
    val id: String,
    val name: String,
    val location: String,
    val dynasty: String,
    val king: String,
    val description: String,
    val kannadaTranslation: String,
    val giftOrLaw: String,
    val gpsCoordinates: String,
    val imageUrl: String,
    val imageBitmap: android.graphics.Bitmap? = null,
    val imageResName: String? = null
)

data class PreservationReport(
    val id: String,
    val placeName: String,
    val damageType: String,
    val description: String
)