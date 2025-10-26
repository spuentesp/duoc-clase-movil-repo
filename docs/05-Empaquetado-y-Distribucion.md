# Empaquetado y Distribución de Apps Android

## ¿Qué es un APK?

Un **APK** (Android Package Kit) es el archivo que contiene toda tu aplicación Android:
- Código compilado (archivos .dex)
- Recursos (imágenes, layouts, strings)
- Manifest (permisos, actividades)
- Librerías nativas
- Certificado de firma

Es como un archivo .zip que Android puede instalar y ejecutar.

---

## Tipos de Build

### 1. Debug Build
- **Propósito**: Desarrollo y pruebas
- **Firma**: Certificado de debug automático
- **Optimización**: No optimizado, más grande
- **Uso**: Instalar en dispositivos para probar

### 2. Release Build
- **Propósito**: Distribución a usuarios finales
- **Firma**: Requiere tu propio certificado
- **Optimización**: Código optimizado y ofuscado
- **Uso**: Publicar en Play Store o distribuir

---

## Compilar un APK Debug

### Opción 1: Usando Android Studio

1. **Abrir el menú Build**
   - Click en `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`

2. **Esperar la compilación**
   - Verás una notificación cuando termine
   - Click en `locate` para abrir la carpeta

3. **Ubicación del APK**
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

### Opción 2: Usando Terminal/Gradle

```bash
# En la raíz del proyecto
./gradlew assembleDebug
```

El APK estará en: `app/build/outputs/apk/debug/app-debug.apk`

**Para Windows:**
```bash
gradlew.bat assembleDebug
```

---

## Compilar un APK Release

### Paso 1: Crear un Keystore (Certificado)

Un **keystore** es un archivo que contiene tu certificado digital para firmar apps.

#### Crear Keystore con Android Studio

1. **Menú Build**
   - `Build` → `Generate Signed Bundle / APK`

2. **Seleccionar APK**
   - Click en `APK` → `Next`

3. **Create new keystore**
   - Click en `Create new...`
   - **Key store path**: Elegir ubicación (ej: `~/my-release-key.jks`)
   - **Password**: Crear contraseña segura (¡GUARDARLA!)
   - **Alias**: Nombre del certificado (ej: `my-key-alias`)
   - **Password**: Contraseña del alias
   - **Validity**: 25 años (o más)
   - **Certificate info**: Tu nombre/organización

4. **Guardar información**
   ⚠️ **MUY IMPORTANTE**: Guarda en lugar seguro:
   - Ruta del keystore
   - Password del keystore
   - Alias de la key
   - Password de la key

   **Si pierdes esta información, NO podrás actualizar tu app en Play Store**

#### Crear Keystore con Terminal (Linux/Mac)

```bash
keytool -genkey -v -keystore my-release-key.jks \
  -alias my-key-alias \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

Responde las preguntas que aparecen.

#### Crear Keystore con PowerShell (Windows)

```powershell
# Navegar a la carpeta del proyecto
cd C:\ruta\a\tu\proyecto

# Crear el keystore
keytool -genkey -v -keystore my-release-key.jks `
  -alias my-key-alias `
  -keyalg RSA `
  -keysize 2048 `
  -validity 10000
```

**Notas para Windows**:
- El comando `keytool` viene con el JDK de Android Studio
- Si no funciona, agrega el JDK al PATH o usa la ruta completa:
  ```powershell
  "C:\Program Files\Android\Android Studio\jbr\bin\keytool.exe" -genkey -v -keystore my-release-key.jks `
    -alias my-key-alias `
    -keyalg RSA `
    -keysize 2048 `
    -validity 10000
  ```
- Usa `` ` `` (backtick) al final de cada línea para continuar en la siguiente

**Información que te pedirá**:
1. **Enter keystore password**: Crea una contraseña segura (ej: `MiPassword123!`)
2. **Re-enter password**: Confirma la contraseña
3. **What is your first and last name?**: Tu nombre o nombre de la empresa
4. **What is the name of your organizational unit?**: Departamento (ej: "Desarrollo")
5. **What is the name of your organization?**: Nombre de tu empresa/proyecto
6. **What is the name of your City or Locality?**: Tu ciudad
7. **What is the name of your State or Province?**: Tu región
8. **What is the two-letter country code?**: CL (para Chile)
9. **Is CN=... correct?**: Escribe `yes` si todo está correcto
10. **Enter key password**: Puedes presionar Enter para usar la misma contraseña del keystore

⚠️ **GUARDAR ESTA INFORMACIÓN**:
```text
Keystore Path: C:\ruta\a\tu\proyecto\my-release-key.jks
Keystore Password: [tu contraseña]
Key Alias: my-key-alias
Key Password: [tu contraseña de key]
```

**Guardar en un lugar seguro**: USB, cloud encriptado, gestor de contraseñas.

#### ⚠️ Solución de Problemas: "keytool no se reconoce" en Windows

Si al ejecutar `keytool` en PowerShell recibes el error:
```
keytool: The term 'keytool' is not recognized...
```

**Causa**: El comando `keytool` no está en el PATH de Windows.

**Soluciones**:

**Opción 1: Usar la ruta completa del keytool**

```powershell
# Probar esta ruta primero (Android Studio con JBR)
& "C:\Program Files\Android\Android Studio\jbr\bin\keytool.exe" -genkey -v -keystore my-release-key.jks `
  -alias my-key-alias `
  -keyalg RSA `
  -keysize 2048 `
  -validity 10000
```

**Opción 2: Agregar keytool al PATH temporalmente**

```powershell
# Agregar al PATH solo para esta sesión de PowerShell
$env:Path += ";C:\Program Files\Android\Android Studio\jbr\bin"

# Ahora puedes usar keytool normalmente
keytool -genkey -v -keystore my-release-key.jks `
  -alias my-key-alias `
  -keyalg RSA `
  -keysize 2048 `
  -validity 10000
```

**Opción 3: Agregar keytool al PATH permanentemente**

1. **Encontrar la ruta de keytool**

   Busca en uno de estos directorios:
   ```
   C:\Program Files\Android\Android Studio\jbr\bin\
   C:\Program Files\Java\jdk-XX\bin\
   ```

2. **Agregar al PATH del Sistema**

   a) Presiona `Windows + R`

   b) Escribe: `sysdm.cpl` y presiona Enter

   c) Ve a la pestaña **"Opciones avanzadas"**

   d) Click en **"Variables de entorno"**

   e) En "Variables del sistema", busca `Path` y haz doble click

   f) Click en **"Nuevo"**

   g) Pega la ruta: `C:\Program Files\Android\Android Studio\jbr\bin`

   h) Click en **"Aceptar"** en todas las ventanas

   i) **Cierra y reabre PowerShell** para que tome efecto

3. **Verificar que funciona**

   ```powershell
   # Abrir nueva ventana de PowerShell
   keytool -version
   ```

   Deberías ver la versión de keytool.

**Opción 4: Usar Command Prompt (CMD) en lugar de PowerShell**

A veces funciona mejor en CMD:

```cmd
# Abrir CMD (no PowerShell)
cd C:\ruta\a\tu\proyecto

# Usar ruta completa
"C:\Program Files\Android\Android Studio\jbr\bin\keytool.exe" -genkey -v -keystore my-release-key.jks -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000
```

**Opción 5: Usar Android Studio directamente**

Si nada de lo anterior funciona, usa la interfaz gráfica:
1. `Build` → `Generate Signed Bundle / APK`
2. `APK` → `Next`
3. `Create new...` → Seguir el asistente

### Paso 2: Configurar Firma en build.gradle.kts

Edita `app/build.gradle.kts`:

```kotlin
android {
    // ... otras configuraciones

    signingConfigs {
        create("release") {
            storeFile = file("/ruta/a/tu/my-release-key.jks")
            storePassword = "tu-password-keystore"
            keyAlias = "my-key-alias"
            keyPassword = "tu-password-key"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

⚠️ **SEGURIDAD**: No commitear contraseñas en Git. Mejor usar variables de entorno:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file(System.getenv("KEYSTORE_FILE") ?: "")
        storePassword = System.getenv("KEYSTORE_PASSWORD")
        keyAlias = System.getenv("KEY_ALIAS")
        keyPassword = System.getenv("KEY_PASSWORD")
    }
}
```

### Paso 3: Compilar Release APK

#### Opción 1: Usando Android Studio (Recomendado)

1. **Abrir menú Build**
   - `Build` → `Generate Signed Bundle / APK`

2. **Seleccionar formato**
   - Selecciona `APK`
   - Click en `Next`

3. **Configurar keystore**
   - **Key store path**: Busca tu archivo `.jks`
   - **Key store password**: Ingresa la contraseña del keystore
   - **Key alias**: Ingresa el alias (ej: `my-key-alias`)
   - **Key password**: Ingresa la contraseña de la key
   - ☑️ **Remember passwords**: (Opcional, solo en computador personal)
   - Click en `Next`

4. **Configurar build**
   - **Destination folder**: Deja la ruta por defecto o cámbiala
   - **Build Variants**: Selecciona `release`
   - **Signature Versions**:
     - ☑️ V1 (Jar Signature) - Para Android 6.0 y anteriores
     - ☑️ V2 (Full APK Signature) - Para Android 7.0+
   - Click en `Create`

5. **Resultado**
   - Verás una notificación cuando termine
   - Click en `locate` para abrir la carpeta
   - APK en: `app/release/app-release.apk`

#### Opción 2: Usando Terminal/Gradle (Linux/Mac)

```bash
./gradlew assembleRelease
```

APK en: `app/build/outputs/apk/release/app-release.apk`

#### Opción 3: Usando PowerShell (Windows)

```powershell
# En la raíz del proyecto
.\gradlew.bat assembleRelease
```

**Si tienes variables de entorno configuradas**:

```powershell
# Configurar variables de entorno (una sola vez por sesión)
$env:KEYSTORE_FILE = "C:\ruta\a\tu\my-release-key.jks"
$env:KEYSTORE_PASSWORD = "tu_password_keystore"
$env:KEY_ALIAS = "my-key-alias"
$env:KEY_PASSWORD = "tu_password_key"

# Compilar
.\gradlew.bat assembleRelease
```

**Verificar el APK generado**:

```powershell
# Listar archivos generados
Get-ChildItem -Path app\build\outputs\apk\release\ -Recurse

# Ver detalles del APK
Get-Item app\build\outputs\apk\release\app-release.apk | Format-List
```

APK en: `app\build\outputs\apk\release\app-release.apk`

### Paso 4: Verificar la Firma del APK

Es importante verificar que el APK está correctamente firmado:

#### En Windows (PowerShell)

```powershell
# Verificar firma del APK
& "C:\Program Files\Android\Android Studio\jbr\bin\jarsigner.exe" -verify -verbose -certs app\build\outputs\apk\release\app-release.apk
```

Deberías ver:
```
jar verified.
```

#### En Linux/Mac

```bash
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

#### Verificar información del certificado

**Windows**:
```powershell
& "C:\Program Files\Android\Android Studio\jbr\bin\keytool.exe" -list -v -keystore my-release-key.jks -alias my-key-alias
```

**Linux/Mac**:
```bash
keytool -list -v -keystore my-release-key.jks -alias my-key-alias
```

Verifica que:
- El certificado es válido
- La fecha de expiración es correcta (debe ser varios años en el futuro)
- El alias es correcto

---

## Crear un Android App Bundle (AAB)

**AAB** es el formato recomendado por Google Play Store (más eficiente que APK).

### ¿Por qué usar AAB?

- **Tamaño menor**: Google optimiza el APK para cada dispositivo
- **Requerido**: Play Store requiere AAB para apps nuevas
- **Mejor experiencia**: Los usuarios descargan solo lo necesario

### Compilar AAB

```bash
./gradlew bundleRelease
```

AAB en: `app/build/outputs/bundle/release/app-release.aab`

---

## Personalizar Iconos de la App

### Método 1: Image Asset Studio (Recomendado)

1. **Abrir Asset Studio**
   - Click derecho en `res` → `New` → `Image Asset`

2. **Configurar Icono**
   - **Icon Type**: Launcher Icons (Adaptive and Legacy)
   - **Name**: `ic_launcher`
   - **Foreground Layer**:
     - **Source Asset**: Selecciona tu imagen
     - **Resize**: Ajusta el tamaño
     - **Trim**: Recortar espacios transparentes
   - **Background Layer**:
     - **Source Asset**: Color o imagen de fondo
     - **Color**: Elegir color de fondo

3. **Preview**
   - Verás cómo se ve en diferentes formas (círculo, cuadrado, etc.)

4. **Finish**
   - Android Studio genera todos los tamaños necesarios:
     - `mipmap-mdpi/` - 48x48
     - `mipmap-hdpi/` - 72x72
     - `mipmap-xhdpi/` - 96x96
     - `mipmap-xxhdpi/` - 144x144
     - `mipmap-xxxhdpi/` - 192x192

### Método 2: Manualmente

Si quieres crear los iconos manualmente, necesitas estas dimensiones:

| Densidad | Tamaño | Carpeta |
|----------|--------|---------|
| MDPI | 48x48 | `mipmap-mdpi/` |
| HDPI | 72x72 | `mipmap-hdpi/` |
| XHDPI | 96x96 | `mipmap-xhdpi/` |
| XXHDPI | 144x144 | `mipmap-xxhdpi/` |
| XXXHDPI | 192x192 | `mipmap-xxxhdpi/` |

**Adaptive Icons** (Android 8.0+):
- **Foreground**: 108x108 dp (contenido principal)
- **Background**: 108x108 dp (fondo)
- Safe zone: 66x66 dp (área visible garantizada)

### Método 3: Herramientas Online

**Android Asset Studio**: https://romannurik.github.io/AndroidAssetStudio/
1. Subir tu imagen
2. Ajustar configuración
3. Descargar ZIP con todos los tamaños
4. Extraer en carpeta `res/`

**easyappicon**: https://easyappicon.com/
- Sube una imagen de 1024x1024
- Genera todos los tamaños automáticamente

---

## Cambiar el Nombre de la App

### En Android Studio

Edita `app/src/main/res/values/strings.xml`:

```xml
<resources>
    <string name="app_name">Mi App Genial</string>
</resources>
```

Este nombre aparece:
- Debajo del icono en el launcher
- En la lista de apps instaladas
- En configuración de apps

---

## Cambiar el Application ID (Package Name)

El **Application ID** es el identificador único de tu app en Play Store.

### Formato
```
com.tucompañia.tuapp
```

Ejemplo: `com.duoc.miproyecto`

### Cambiar Application ID

1. **Editar build.gradle.kts**

```kotlin
android {
    namespace = "com.tucompañia.tuapp"

    defaultConfig {
        applicationId = "com.tucompañia.tuapp"
        // ...
    }
}
```

2. **Refactorizar paquete**
   - Click derecho en el paquete `com.example.baseproject`
   - `Refactor` → `Rename`
   - Cambiar cada parte del paquete
   - Android Studio actualizará todos los imports

⚠️ **Importante**: El Application ID debe ser único en Play Store y no puede cambiarse después de publicar.

---

## Versionar tu App

### Versión Code y Version Name

En `app/build.gradle.kts`:

```kotlin
defaultConfig {
    versionCode = 1        // Número entero que incrementa
    versionName = "1.0"    // Versión visible para usuarios
}
```

**versionCode**:
- Número entero que SIEMPRE incrementa
- Play Store usa esto para saber cuál es más nueva
- Ejemplo: 1, 2, 3, 4...

**versionName**:
- String legible para humanos
- Ejemplo: "1.0", "1.1", "2.0", "2.0.1"

### Esquema de Versionado Recomendado

**Semantic Versioning** (MAJOR.MINOR.PATCH):

```
1.0.0 → Primera versión
1.0.1 → Corrección de bugs
1.1.0 → Nueva característica menor
2.0.0 → Cambios mayores, breaking changes
```

Ejemplo:
```kotlin
versionCode = 10
versionName = "1.0.1"
```

---

## Reducir Tamaño del APK

### 1. Habilitar ProGuard/R8

En `app/build.gradle.kts`:

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true          // Elimina código no usado
        isShrinkResources = true        // Elimina recursos no usados
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

### 2. Usar WebP en lugar de PNG

WebP es más eficiente:
- Click derecho en imagen PNG → `Convert to WebP`
- Ahorra 25-35% de tamaño

### 3. Vectores en lugar de PNGs

Para iconos simples, usa Vector Drawables:
- Escalables sin pérdida de calidad
- Mucho más pequeños

### 4. Splits APK por ABI

Si usas librerías nativas:

```kotlin
android {
    splits {
        abi {
            enable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            universalApk = false
        }
    }
}
```

---

## Instalar APK en Dispositivo

### Método 1: USB (ADB)

```bash
# Habilitar USB Debugging en el dispositivo
# Conectar por USB

adb install app/build/outputs/apk/debug/app-debug.apk

# Para reinstalar
adb install -r app-debug.apk
```

### Método 2: Compartir Archivo

1. Copiar APK a dispositivo (email, Drive, etc.)
2. En el dispositivo: Habilitar "Fuentes desconocidas"
   - Configuración → Seguridad → Instalar apps desconocidas
3. Tocar el archivo APK
4. Confirmar instalación

### Método 3: Android Studio

1. Conectar dispositivo o iniciar emulador
2. Click en botón `Run` (▶️)
3. Android Studio instala y ejecuta automáticamente

---

## Publicar en Google Play Store

### Requisitos

1. **Cuenta de Google Play Console**: $25 USD (pago único)
2. **App firmada con keystore de release**
3. **Android App Bundle (.aab)**
4. **Assets gráficos**:
   - Icono: 512x512 PNG
   - Feature graphic: 1024x500 PNG
   - Screenshots: mínimo 2

### Pasos Básicos

1. **Crear cuenta** en https://play.google.com/console

2. **Crear nueva app**
   - Nombre de la app
   - Idioma predeterminado
   - Tipo (app/juego)

3. **Completar formulario**
   - Descripción corta y larga
   - Capturas de pantalla
   - Categoría
   - Información de contacto
   - Política de privacidad (URL)

4. **Subir App Bundle**
   - Ir a `Producción` → `Crear nueva versión`
   - Subir `.aab` file
   - Notas de la versión

5. **Revisión de contenido**
   - Cuestionario de contenido
   - Clasificación de contenido

6. **Enviar para revisión**
   - Puede tomar 1-7 días

---

## Checklist Pre-Lanzamiento

### Antes de Compilar Release

- [ ] Cambiar `applicationId` a tu paquete único
- [ ] Actualizar `versionCode` y `versionName`
- [ ] Cambiar nombre de la app en `strings.xml`
- [ ] Crear iconos personalizados (launcher icon)
- [ ] Verificar que no haya logs de debug
- [ ] Probar en diferentes tamaños de pantalla
- [ ] Probar en Android version mínima soportada
- [ ] Revisar permisos en AndroidManifest

### Build Release

- [ ] Crear/configurar keystore
- [ ] Compilar `bundleRelease`
- [ ] Probar el APK/AAB firmado
- [ ] Verificar tamaño del archivo (< 100 MB ideal)

### Play Store

- [ ] Preparar descripción en español e inglés
- [ ] Crear capturas de pantalla atractivas
- [ ] Diseñar feature graphic
- [ ] Escribir política de privacidad
- [ ] Definir precio (gratis/pago)
- [ ] Configurar países de distribución

---

## Comandos Útiles de Gradle

```bash
# Limpiar build anterior
./gradlew clean

# Compilar debug
./gradlew assembleDebug

# Compilar release
./gradlew assembleRelease

# Crear bundle (AAB)
./gradlew bundleRelease

# Ver todas las variantes
./gradlew tasks --all

# Ver tamaño del APK
./gradlew assembleRelease --scan

# Instalar en dispositivo conectado
./gradlew installDebug

# Desinstalar
./gradlew uninstallDebug
```

---

## Solución de Problemas Comunes

### Error: "App not installed"

**Causa**: Ya existe una versión firmada con diferente certificado

**Solución**: Desinstalar la versión anterior primero
```bash
adb uninstall com.example.baseproject
```

### Error: "Keystore was tampered with"

**Causa**: Contraseña incorrecta del keystore

**Solución**: Verificar password en build.gradle.kts

### APK muy grande

**Soluciones**:
1. Habilitar `minifyEnabled` y `shrinkResources`
2. Convertir imágenes a WebP
3. Usar App Bundle en lugar de APK
4. Revisar dependencias no necesarias

### Error de firma en Play Store

**Causa**: Perdiste el keystore original

**Solución**: Contactar a Google Play Support o crear nueva app con nuevo package name (última opción)

---

## Recursos Adicionales

### Herramientas

- **Android Asset Studio**: https://romannurik.github.io/AndroidAssetStudio/
- **App Icon Generator**: https://appicon.co/
- **Screenshot Generator**: https://www.mockuphone.com/

### Documentación Oficial

- [Publicar tu app](https://developer.android.com/studio/publish)
- [Firmar tu app](https://developer.android.com/studio/publish/app-signing)
- [Reducir tamaño](https://developer.android.com/topic/performance/reduce-apk-size)
- [Play Console Help](https://support.google.com/googleplay/android-developer)

### Mejores Prácticas

1. **Nunca** commitear el keystore en Git
2. **Siempre** hacer backup del keystore en múltiples lugares seguros
3. **Usar** variables de entorno para passwords
4. **Probar** el APK release antes de publicar
5. **Incrementar** versionCode en cada release
6. **Mantener** changelog actualizado

---

## Ejemplo Completo: De Desarrollo a Play Store

### 1. Preparación

```bash
# Cambiar application ID
# Editar app/build.gradle.kts
applicationId = "com.duoc.miapp"
versionCode = 1
versionName = "1.0"
```

### 2. Personalización

```xml
<!-- strings.xml -->
<string name="app_name">Mi App Increíble</string>
```

### 3. Crear Icono

- Usar Image Asset Studio
- Generar todos los tamaños

### 4. Crear Keystore

```bash
keytool -genkey -v -keystore ~/miapp-release-key.jks \
  -alias miapp-key -keyalg RSA -keysize 2048 -validity 10000
```

### 5. Configurar Firma

```kotlin
// build.gradle.kts
signingConfigs {
    create("release") {
        storeFile = file(System.getenv("KEYSTORE_FILE"))
        storePassword = System.getenv("KEYSTORE_PASSWORD")
        keyAlias = System.getenv("KEY_ALIAS")
        keyPassword = System.getenv("KEY_PASSWORD")
    }
}
```

### 6. Compilar

```bash
export KEYSTORE_FILE=~/miapp-release-key.jks
export KEYSTORE_PASSWORD=mi_password_seguro
export KEY_ALIAS=miapp-key
export KEY_PASSWORD=mi_key_password

./gradlew bundleRelease
```

### 7. Subir a Play Store

1. Ir a Play Console
2. Crear nueva app
3. Subir `app-release.aab`
4. Completar formularios
5. Enviar para revisión

¡Listo! Tu app está en camino a Play Store 🚀
