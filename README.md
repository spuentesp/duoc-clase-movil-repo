# Proyecto Base Android - Kotlin & Jetpack Compose

Plantilla moderna de proyecto Android construida con Kotlin y Jetpack Compose. Punto de partida perfecto para nuevas aplicaciones Android.

## Ramas

### `main` - Plantilla Básica
Plantilla inicial limpia con solo pantalla de bienvenida.

### `native-features` - Demo Completa de Características Nativas
**¡ESTÁS AQUÍ!** Ejemplos completos de todas las características nativas de Android incluyendo:
- 📳 Patrones de vibración
- 💾 Almacenamiento local (DataStore)
- 🔐 Autenticación biométrica
- 📷 Acceso a la cámara
- 🔦 Control de linterna
- 📱 Sensores de acelerómetro
- 🔋 Monitoreo de batería
- 📍 GPS/Ubicación
- 🔔 Notificaciones push

Ver [GUIA_CARACTERISTICAS_NATIVAS.md](GUIA_CARACTERISTICAS_NATIVAS.md) para documentación completa.

## Características

- **Kotlin** - Lenguaje de programación moderno y conciso
- **Jetpack Compose** - Framework de UI declarativo
- **Material 3** - Componentes de Material Design más recientes
- **Navegación** - Navegación multi-pantalla con Jetpack Navigation
- **Tema Claro/Oscuro** - Cambio automático de tema
- **Colores Dinámicos** - Soporte de temas dinámicos de Android 12+
- **Características Nativas** - Ejemplos funcionales de todas las APIs principales de Android
- **Arquitectura Limpia** - Lista para desarrollo de apps escalables

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/baseproject/
│   │   ├── MainActivity.kt              # Punto de entrada
│   │   └── ui/
│   │       ├── screens/
│   │       │   └── WelcomeScreen.kt     # Pantalla de bienvenida composable
│   │       └── theme/
│   │           ├── Color.kt             # Definiciones de colores
│   │           ├── Theme.kt             # Tema de la app
│   │           └── Type.kt              # Tipografía
│   └── res/
│       ├── values/
│       │   ├── strings.xml              # Recursos de texto
│       │   └── themes.xml               # Temas de la app
│       └── mipmap-*/                    # Iconos de la app
└── build.gradle.kts                     # Dependencias de la app
```

## Requisitos

- **Android Studio** Hedgehog (2023.1.1) o superior
- **JDK** 8 o superior
- **SDK Mínimo** 24 (Android 7.0)
- **SDK Objetivo** 34 (Android 14)

## Primeros Pasos

### 1. Clonar el Repositorio

```bash
git clone <url-de-tu-repo>
cd duoc-clase-movil-repo
```

### 2. Abrir en Android Studio

1. Abrir Android Studio
2. Seleccionar "Open an Existing Project"
3. Navegar al repositorio clonado
4. Esperar a que complete la sincronización de Gradle

### 3. Ejecutar la App

1. Conectar un dispositivo Android o iniciar un emulador
2. Hacer clic en el botón "Run" (ícono de play verde)
3. Seleccionar tu dispositivo/emulador
4. La app se compilará y ejecutará

## Personalización

### Cambiar Nombre del Paquete

1. En Android Studio, hacer clic derecho en el paquete `com.example.baseproject`
2. Seleccionar "Refactor" → "Rename"
3. Actualizar el nombre del paquete
4. Actualizar `namespace` en [app/build.gradle.kts](app/build.gradle.kts)
5. Actualizar `package` en [AndroidManifest.xml](app/src/main/AndroidManifest.xml)

### Cambiar Nombre de la App

Editar `app_name` en [strings.xml](app/src/main/res/values/strings.xml):

```xml
<string name="app_name">Nombre de Tu App</string>
```

### Personalizar Colores del Tema

Editar colores en [Color.kt](app/src/main/java/com/example/baseproject/ui/theme/Color.kt):

```kotlin
val Purple40 = Color(0xFF6650a4)  // Color primario
val PurpleGrey40 = Color(0xFF625b71)  // Color secundario
val Pink40 = Color(0xFF7D5260)  // Color terciario
```

### Agregar Iconos de Lanzador

1. Hacer clic derecho en la carpeta `res` en Android Studio
2. Seleccionar "New" → "Image Asset"
3. Seguir el asistente para generar iconos
4. O usar la herramienta online: https://romannurik.github.io/AndroidAssetStudio/

## Dependencias

- **Compose BOM**: 2023.10.01
- **Material 3**: Última versión
- **Kotlin**: 1.9.20
- **Android Gradle Plugin**: 8.2.0

## Compilación para Producción

### Compilación Debug

```bash
./gradlew assembleDebug
```

### Compilación Release

```bash
./gradlew assembleRelease
```

El APK estará en `app/build/outputs/apk/`

## Próximos Pasos

- Agregar navegación (Jetpack Navigation Compose)
- Implementar ViewModel y gestión de estado
- Agregar inyección de dependencias (Hilt/Koin)
- Configurar networking (Retrofit/Ktor)
- Agregar base de datos local (Room)
- Configurar CI/CD

## Licencia

Este es un proyecto plantilla base. Úsalo libremente para tus propios proyectos.

## Soporte

Para problemas o preguntas sobre desarrollo Android:
- [Android Developers](https://developer.android.com/)
- [Documentación de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Documentación de Kotlin](https://kotlinlang.org/docs/home.html)
