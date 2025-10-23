# Guía de Características Nativas

Esta rama demuestra cómo acceder a características nativas de Android usando Kotlin y Jetpack Compose.

## Estructura del Proyecto

```
app/src/main/java/com/example/baseproject/
├── MainActivity.kt                          # Punto de entrada de la app con Navegación
├── navigation/
│   ├── NavGraph.kt                         # Definición del grafo de navegación
│   └── Screen.kt                           # Clase sellada de rutas de pantallas
├── ui/
│   ├── screens/
│   │   ├── WelcomeScreen.kt               # Pantalla de inicio
│   │   ├── FeaturesMenuScreen.kt          # Menú con todas las características nativas
│   │   └── features/
│   │       ├── VibrationScreen.kt         # Demo de API de vibración
│   │       ├── LocalStorageScreen.kt      # Demo de DataStore
│   │       ├── BiometricScreen.kt         # Demo de autenticación biométrica
│   │       ├── CameraScreen.kt            # Demo de acceso a cámara
│   │       ├── FlashlightScreen.kt        # Demo de control de linterna
│   │       ├── AccelerometerScreen.kt     # Demo de sensores de movimiento
│   │       ├── BatteryScreen.kt           # Demo de información de batería
│   │       ├── LocationScreen.kt          # Demo de GPS/Ubicación
│   │       └── NotificationsScreen.kt     # Demo de notificaciones locales
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
```

## Características Nativas Disponibles

### 1. Vibración
- **Pantalla**: [VibrationScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/VibrationScreen.kt)
- **Permiso**: `VIBRATE`
- **Funcionalidades**:
  - Vibración simple
  - Vibración con patrón (ejemplo SOS)
  - Efectos de clic
  - Efectos de clic fuerte

### 2. Almacenamiento Local (DataStore)
- **Pantalla**: [LocalStorageScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/LocalStorageScreen.kt)
- **Permiso**: Ninguno
- **Dependencias**: `androidx.datastore:datastore-preferences`
- **Funcionalidades**:
  - Guardar pares clave-valor
  - Leer datos almacenados
  - Limpiar almacenamiento
  - Flujo de datos reactivo

### 3. Autenticación Biométrica
- **Pantalla**: [BiometricScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/BiometricScreen.kt)
- **Permiso**: Ninguno (el usuario debe registrar datos biométricos)
- **Dependencias**: `androidx.biometric:biometric`
- **Funcionalidades**:
  - Autenticación por huella digital
  - Reconocimiento facial
  - Credenciales del dispositivo como respaldo
  - Verificación de disponibilidad biométrica

### 4. Cámara
- **Pantalla**: [CameraScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/CameraScreen.kt)
- **Permiso**: `CAMERA`
- **Dependencias**: Librerías CameraX
- **Funcionalidades**:
  - Vista previa de cámara
  - Manejo de permisos
  - Selección de cámara frontal/trasera

### 5. Linterna
- **Pantalla**: [FlashlightScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/FlashlightScreen.kt)
- **Permiso**: Ninguno
- **Funcionalidades**:
  - Encender/apagar linterna
  - Control de alternancia simple

### 6. Acelerómetro
- **Pantalla**: [AccelerometerScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/AccelerometerScreen.kt)
- **Permiso**: Ninguno
- **Funcionalidades**:
  - Leer valores de aceleración X, Y, Z
  - Actualizaciones de sensor en tiempo real
  - Gestión del ciclo de vida del sensor

### 7. Batería
- **Pantalla**: [BatteryScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/BatteryScreen.kt)
- **Permiso**: Ninguno
- **Funcionalidades**:
  - Nivel de batería
  - Estado de carga
  - Salud de la batería
  - Temperatura y voltaje

### 8. Ubicación (GPS)
- **Pantalla**: [LocationScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/LocationScreen.kt)
- **Permisos**: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
- **Dependencias**: `play-services-location`
- **Funcionalidades**:
  - Obtener ubicación actual
  - Latitud y longitud
  - Manejo de permisos

### 9. Notificaciones
- **Pantalla**: [NotificationsScreen.kt](app/src/main/java/com/example/baseproject/ui/screens/features/NotificationsScreen.kt)
- **Permiso**: `POST_NOTIFICATIONS` (Android 13+)
- **Funcionalidades**:
  - Notificaciones simples
  - Notificaciones con acciones
  - Canales de notificación
  - Manejo de permisos

## Cómo Usar

1. **Ejecutar la app** en un dispositivo físico o emulador
2. **Hacer clic en "Comenzar"** en la pantalla de bienvenida
3. **Seleccionar una característica** del menú de características
4. **Otorgar permisos** cuando se solicite
5. **Probar la característica** usando los botones provistos
6. **Revisar los ejemplos de código** mostrados en cada pantalla

## Ruta de Aprendizaje

Para estudiantes que aprenden desarrollo Android:

1. Comenzar con **Vibración** - API simple, sin permisos complejos
2. Probar **Linterna** - Otra característica simple
3. Pasar a **Batería** - Información del sistema de solo lectura
4. Aprender **Almacenamiento Local** - Esencial para cualquier app
5. Explorar **Acelerómetro** - Fundamentos de sensores
6. Practicar **Notificaciones** - Interacción con el usuario
7. Abordar **Cámara** - Complejo pero gratificante
8. Dominar **Ubicación** - Casos de uso del mundo real
9. Finalmente **Biométrica** - Seguridad avanzada

## Patrones de Código

Cada pantalla de característica sigue este patrón:

```kotlin
@Composable
fun FeatureScreen(onBackClick: () -> Unit = {}) {
    // 1. Gestión de estado
    var featureState by remember { mutableStateOf(...) }

    // 2. Manejo de permisos (si es necesario)
    val launcher = rememberLauncherForActivityResult(...)

    // 3. UI con Scaffold
    Scaffold(
        topBar = { TopAppBar con botón de regreso }
    ) {
        Column {
            // Descripción de la característica
            // Controles interactivos
            // Mostrar resultados
            // Ejemplo de código
            // Información de permisos/dependencias
        }
    }
}
```

## Resumen de Permisos

Agregar estos permisos a [AndroidManifest.xml](app/src/main/AndroidManifest.xml):

```xml
<!-- Permisos requeridos -->
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Resumen de Dependencias

Todas las dependencias están en [build.gradle.kts](app/build.gradle.kts):

```kotlin
// Navegación
implementation("androidx.navigation:navigation-compose:2.7.5")

// Biométrica
implementation("androidx.biometric:biometric:1.2.0-alpha05")

// Cámara
implementation("androidx.camera:camera-camera2:1.3.0")
implementation("androidx.camera:camera-lifecycle:1.3.0")
implementation("androidx.camera:camera-view:1.3.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.0.0")

// Ubicación
implementation("com.google.android.gms:play-services-location:21.0.1")
```

## Flujo de Navegación

```
PantallaBienvenida
    ↓ (Comenzar)
PantallaMenuCaracteristicas
    ↓ (Seleccionar característica)
Pantalla de Característica Individual
    ↓ (Botón atrás)
PantallaMenuCaracteristicas
```

## Consejos para Estudiantes

1. **Leer los ejemplos de código** en cada pantalla - están listos para producción
2. **Probar en dispositivos reales** cuando sea posible - los emuladores tienen limitaciones
3. **Manejar permisos correctamente** - usar los patrones mostrados
4. **Verificar versiones de Android** - algunas características requieren niveles de API específicos
5. **Experimentar** - modificar el código para aprender cómo funciona
6. **Combinar características** - construir algo que use múltiples capacidades

## Problemas Comunes

### Cámara no funciona en emulador
- Usar un dispositivo físico o asegurar que el AVD tenga cámara habilitada

### Ubicación no se actualiza
- Habilitar servicios de ubicación en configuración del dispositivo
- Otorgar permisos de ubicación
- Para emulador: enviar ubicación via controles extendidos

### Biométrica no disponible
- Usuario debe registrar huella/cara en configuración del dispositivo
- No todos los emuladores soportan biométrica

### Notificaciones no se muestran
- Verificar permisos de notificación (Android 13+)
- Asegurar que el canal de notificación esté creado
- Verificar configuración de notificaciones del dispositivo

## Próximos Pasos

- Combinar múltiples características en una sola app
- Agregar manejo de errores adecuado
- Implementar arquitectura MVVM
- Agregar pruebas unitarias para ViewModels
- Crear diseños de UI personalizados
- Integrar con APIs backend

## Recursos

- [Documentación de Android Developers](https://developer.android.com/)
- [Guía de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Componentes de Material 3](https://m3.material.io/)
