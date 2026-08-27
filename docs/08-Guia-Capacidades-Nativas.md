# 08 — Capacidades Nativas de Android

Guía de las pantallas nativas incluidas en este proyecto y los permisos
requeridos para cada una.

## Pantallas disponibles

| Pantalla | Concepto | Permisos requeridos |
|----------|----------|---------------------|
| `AccelerometerScreen` | Sensores de movimiento | Ninguno |
| `BatteryScreen` | Estado de la batería | Ninguno |
| `BiometricScreen` | Autenticación biométrica | Ninguno (usa `androidx.biometric`) |
| `CameraScreen` | Captura de fotos | `CAMERA` |
| `FlashlightScreen` | Linterna | Ninguno (usa `CameraManager.setTorchMode`) |
| `LocalStorageScreen` | Archivos locales | `READ/WRITE_EXTERNAL_STORAGE` (legacy) |
| `LocationScreen` | GPS / ubicación | `ACCESS_FINE_LOCATION` |
| `NotificationsScreen` | Notificaciones | `POST_NOTIFICATIONS` (Android 13+) |
| `VibrationScreen` | Vibración | `VIBRATE` |

## Cómo probar cada pantalla

Las pantallas están en el menú raíz → "Capacidades Nativas". Toca cada
una para ver su demo. Algunos sensores solo están disponibles en
dispositivos físicos (acelerómetro, GPS); en el emulador algunos
devuelven valores simulados.

## Notas por pantalla

### Biometría
- Requiere dispositivo con huella o reconocimiento facial configurado.
- En el emulador: Settings → Security → Fingerprint → Enroll.

### Cámara
- Usa CameraView de Jetpack (sin permisos en runtime adicionales a CAMERA).
- En emulador: la cámara virtual está disponible.

### GPS
- En emulador: Extended Controls → Location → set lat/lon.
- En dispositivo: habilitar ubicación.

### Notificaciones
- Android 13+ requiere pedir permiso POST_NOTIFICATIONS en runtime.
- La app ya lo solicita la primera vez que entras a la pantalla.

## Permisos del Manifest

`AndroidManifest.xml` ya incluye:

```xml
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Recursos adicionales

- [Documentación de permisos Android](https://developer.android.com/guide/topics/permissions/overview)
- [Jetpack CameraX](https://developer.android.com/training/camerax)
- [Android Biometric](https://developer.android.com/training/sign-in/biometric)