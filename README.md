# Proyecto Base Android - Kotlin & Jetpack Compose

Una plantilla de proyecto Android limpia y moderna construida con Kotlin y Jetpack Compose.

## Características

- **Kotlin** - Lenguaje de programación moderno y conciso
- **Jetpack Compose** - Framework de UI declarativa
- **Material 3** -  componentes de Material Design
- **Tema Oscuro/Claro** - Cambio automático de tema
- **Colores Dinámicos** - Soporte para temas dinámicos de Android 12+
- **Arquitectura Limpia** - Lista para desarrollo escalable de aplicaciones

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/baseproject/
│   │   ├── MainActivity.kt              # Punto de entrada
│   │   └── ui/
│   │       ├── screens/
│   │       │   └── WelcomeScreen.kt     # Composable de pantalla de bienvenida
│   │       └── theme/
│   │           ├── Color.kt             # Definiciones de colores
│   │           ├── Theme.kt             # Tema de la aplicación
│   │           └── Type.kt              # Tipografía
│   └── res/
│       ├── values/
│       │   ├── strings.xml              # Recursos de texto
│       │   └── themes.xml               # Temas de la aplicación
│       └── mipmap-*/                    # Íconos de la aplicación
└── build.gradle.kts                     # Dependencias de la aplicación
```

## Requisitos

- **Android Studio** Hedgehog (2023.1.1) o posterior
- **JDK** 8 o superior
- **SDK Mínimo** 24 (Android 7.0)
- **SDK Objetivo** 34 (Android 14)

## Comenzando

### 1. Clonar el Repositorio

```bash
git clone <url-de-tu-repositorio>
cd duoc-clase-movil-repo
```

### 2. Abrir en Android Studio

1. Abre Android Studio
2. Selecciona "Abrir un Proyecto Existente"
3. Navega al repositorio clonado
4. Espera a que se complete la sincronización de Gradle

### 3. Ejecutar la Aplicación

1. Conecta un dispositivo Android o inicia un emulador
2. Haz clic en el botón "Run" (ícono de play verde)
3. Selecciona tu dispositivo/emulador
4. La aplicación se compilará e iniciará

## Personalización

### Cambiar el Nombre del Paquete

1. En Android Studio, haz clic derecho en el paquete `com.example.baseproject`
2. Selecciona "Refactor" → "Rename"
3. Actualiza el nombre del paquete
4. Actualiza `namespace` en [app/build.gradle.kts](app/build.gradle.kts)
5. Actualiza `package` en [AndroidManifest.xml](app/src/main/AndroidManifest.xml)

### Cambiar el Nombre de la Aplicación

Edita `app_name` en [strings.xml](app/src/main/res/values/strings.xml):

```xml
<string name="app_name">Nombre de Tu Aplicación</string>
```

### Personalizar los Colores del Tema

Edita los colores en [Color.kt](app/src/main/java/com/example/baseproject/ui/theme/Color.kt):

```kotlin
val Purple40 = Color(0xFF6650a4)  // Color primario
val PurpleGrey40 = Color(0xFF625b71)  // Color secundario
val Pink40 = Color(0xFF7D5260)  // Color terciario
```

### Agregar Íconos de Lanzamiento

1. Haz clic derecho en la carpeta `res` en Android Studio
2. Selecciona "New" → "Image Asset"
3. Sigue el asistente para generar íconos
4. O usa la herramienta en línea: https://romannurik.github.io/AndroidAssetStudio/

## Dependencias

- **Compose BOM**: 2023.10.01
- **Material 3**: Última versión
- **Kotlin**: 1.9.20
- **Android Gradle Plugin**: 8.2.0

## Compilación para Producción

### Compilación de Depuración

```bash
./gradlew assembleDebug
```

### Compilación de Lanzamiento

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

Este es un proyecto de plantilla base. Úsalo libremente para tus propios proyectos.

## Soporte

Para problemas o preguntas sobre desarrollo Android:
- [Android Developers](https://developer.android.com/)
- [Documentación de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Documentación de Kotlin](https://kotlinlang.org/docs/home.html)