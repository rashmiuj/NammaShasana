# Namma Shasane (ನಮ್ಮ ಶಾಸನ)

**"Turning Old Stones into Talking History"**

Namma Shasane is an Android application built with Jetpack Compose that helps users discover, catalog, and preserve ancient stone inscriptions (Shasanas). It acts as a digital archive and a community-driven reporting tool to protect historical heritage.

## Features

*   **Find a Shasane (Map View):** Locate ancient inscriptions on a map across various districts like Badami, Aihole, Hassan (Sravanabelagola), Banavasi, and more.
*   **Photo Tagging:** Take photos of newly discovered inscriptions or existing ones and tag their location, dynasty, and translations.
*   **Story View:** Read the Kannada translations, historical significance, and details about the King and Dynasty of various stone inscriptions (e.g., Halmidi Inscription, Badami Cave Inscription).
*   **Preservation Alerts:** Report damaged or vandalized inscriptions to help preserve them for future generations.

## Tech Stack

*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Local Storage:** SharedPreferences (JSON parsing)
*   **Cloud Database:** Firebase Firestore (for syncing across devices)
*   **Maps:** OSMDroid (OpenStreetMap for Android)
*   **Image Loading:** Coil

## Project Structure

*   `app/src/main/java/com/example/nammashasane/ui/screens/NammaShasanaApp.kt`: Contains the main Jetpack Compose UI screens (Home, Details, Map, Add Inscription, etc.).
*   `app/src/main/java/com/example/nammashasane/data/AppStorage.kt`: Local data storage and predefined JSON dataset of inscriptions.
*   `app/src/main/java/com/example/nammashasane/data/Inscription.kt`: Data models for inscriptions and preservation reports.

## Local Data 
The app comes pre-loaded with important historical records from Karnataka, including:
1. Halmidi Inscription (Kadamba Dynasty)
2. Badami Cave Inscription (Chalukya Dynasty)
3. Aihole Inscription (Chalukya Dynasty)
4. Kappe Arabhatta (Chalukya Dynasty)
5. Sravanabelagola Inscription (Ganga Dynasty)
6. Tumkur Inscription (Hoysala Dynasty)
7. Palkigundu Edict (Maurya Dynasty)
8. Banavasi Kadamba Stone (Kadamba Dynasty)
9. Srival Inscription (Rashtrakuta Dynasty)

## Setup and Building

1. Make sure you have the Android SDK setup.
2. The project uses standard Gradle wrapper.
3. Simply run `./gradlew assembleDebug` to build the Dev APK.
