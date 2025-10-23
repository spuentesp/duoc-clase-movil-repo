# Base Android Project - Kotlin & Jetpack Compose

A clean, modern Android project template built with Kotlin and Jetpack Compose. Perfect starting point for new Android applications.

## Features

- **Kotlin** - Modern, concise programming language
- **Jetpack Compose** - Declarative UI framework
- **Material 3** - Latest Material Design components
- **Dark/Light Theme** - Automatic theme switching
- **Dynamic Colors** - Android 12+ dynamic theming support
- **Clean Architecture** - Ready for scalable app development

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/baseproject/
│   │   ├── MainActivity.kt              # Entry point
│   │   └── ui/
│   │       ├── screens/
│   │       │   └── WelcomeScreen.kt     # Welcome screen composable
│   │       └── theme/
│   │           ├── Color.kt             # Color definitions
│   │           ├── Theme.kt             # App theme
│   │           └── Type.kt              # Typography
│   └── res/
│       ├── values/
│       │   ├── strings.xml              # String resources
│       │   └── themes.xml               # App themes
│       └── mipmap-*/                    # App icons
└── build.gradle.kts                     # App dependencies
```

## Requirements

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK** 8 or higher
- **Minimum SDK** 24 (Android 7.0)
- **Target SDK** 34 (Android 14)

## Getting Started

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd duoc-clase-movil-repo
```

### 2. Open in Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the cloned repository
4. Wait for Gradle sync to complete

### 3. Run the App

1. Connect an Android device or start an emulator
2. Click the "Run" button (green play icon)
3. Select your device/emulator
4. The app will build and launch

## Customization

### Change Package Name

1. In Android Studio, right-click on the package `com.example.baseproject`
2. Select "Refactor" → "Rename"
3. Update the package name
4. Update `namespace` in [app/build.gradle.kts](app/build.gradle.kts)
5. Update `package` in [AndroidManifest.xml](app/src/main/AndroidManifest.xml)

### Change App Name

Edit the `app_name` in [strings.xml](app/src/main/res/values/strings.xml):

```xml
<string name="app_name">Your App Name</string>
```

### Customize Theme Colors

Edit colors in [Color.kt](app/src/main/java/com/example/baseproject/ui/theme/Color.kt):

```kotlin
val Purple40 = Color(0xFF6650a4)  // Primary color
val PurpleGrey40 = Color(0xFF625b71)  // Secondary color
val Pink40 = Color(0xFF7D5260)  // Tertiary color
```

### Add Launcher Icons

1. Right-click on `res` folder in Android Studio
2. Select "New" → "Image Asset"
3. Follow the wizard to generate icons
4. Or use the online tool: https://romannurik.github.io/AndroidAssetStudio/

## Dependencies

- **Compose BOM**: 2023.10.01
- **Material 3**: Latest
- **Kotlin**: 1.9.20
- **Android Gradle Plugin**: 8.2.0

## Building for Production

### Debug Build

```bash
./gradlew assembleDebug
```

### Release Build

```bash
./gradlew assembleRelease
```

The APK will be in `app/build/outputs/apk/`

## Next Steps

- Add navigation (Jetpack Navigation Compose)
- Implement ViewModel and state management
- Add dependency injection (Hilt/Koin)
- Set up networking (Retrofit/Ktor)
- Add local database (Room)
- Configure CI/CD

## License

This is a base template project. Use it freely for your own projects.

## Support

For issues or questions about Android development:
- [Android Developers](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)