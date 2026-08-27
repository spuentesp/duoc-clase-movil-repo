# Duoc Clase Móvil — Recursos de Estudio Android

Proyecto Android con Kotlin y Jetpack Compose, consolidado como recurso
de estudio progresivo (de Kotlin básico a Android intermedio).

## ✨ Contenido

- 🎨 **Componentes Material Design** — catálogo visual de los componentes
  Material 3 (botones, cards, dialogs, listas, theming, navigation, etc.).
- 📱 **Capacidades Nativas** — pantallas de demostración de biometría,
  cámara, linterna, GPS, sensores, notificaciones, almacenamiento local,
  vibración.
- 🧪 **Ejercicios Android** — 8 mini-apps para practicar Compose,
  ViewModel, Repository, networking.
- 📚 **Ejercicios Kotlin** (módulo hermano `exercises/`) — 33 ejercicios
  Kotlin JVM progresivos de básico a avanzado + coroutines + arquitectura.

## 🗺️ Estructura del repo

```
duoc-clase-movil-repo/
├── app/                              # Android (Compose + Material + Nativo + Ejercicios)
│   └── src/main/java/com/example/baseproject/
│       ├── MainActivity.kt           # aloja RootNav
│       └── ui/
│           ├── screens/{RootMenuScreen, material/, native/, exercises/}
│           ├── navigation/{RootNav, MaterialNav, NativeNav, ExercisesNav}
│           └── theme/
├── exercises/                        # proyecto JVM hermano (Gradle independiente)
│   ├── build.gradle.kts
│   └── src/{main,test}/kotlin/cl/duoc/exercises/
│       ├── _00_variables/ ... _13_exception_handling/   # Kotlin
│       ├── _14_... _19_coroutines_*/                     # Coroutines (6)
│       ├── _20_... _25_arquitectura_*/                   # Arquitectura (6)
│       └── _26_... _29_android_*/ + _30_kata_integrador  # Bridge + kata
├── docs/
│   ├── 00-Fundamentos-Kotlin.md  → 11-Principios-de-Arquitectura.md
│   ├── books/   # gitignored (epubs de referencia)
│   └── README.md   # índice actualizado
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🚀 Cómo empezar

### Requisitos

- Android Studio Hedgehog (2023.1.1) o posterior.
- JDK 17+ (para el módulo `exercises/`).
- SDK mínimo 24, target 34.

### Clonar y abrir

```bash
git clone <url>
cd duoc-clase-movil-repo
# Abre la carpeta en Android Studio.
```

### Correr la app

Con un emulador o dispositivo conectado:

1. Sincroniza Gradle.
2. Run → Run 'app'.
3. La app abre en el menú raíz con 3 secciones.

### Correr los ejercicios Kotlin

```bash
cd exercises
./gradlew test
```

Los tests fallarán hasta que implementes las funciones — eso es parte
del aprendizaje. Ver [docs/09-Guia-Ejercicios-Kotlin.md](docs/09-Guia-Ejercicios-Kotlin.md).

## 📖 Rutas de aprendizaje

- **Si nunca programaste**: [docs/README.md](docs/README.md) tiene la
  ruta detallada semana por semana.
- **Si ya sabes Kotlin**: ve directo a [docs/01-Composables.md](docs/01-Composables.md)
  y a las mini-apps dentro de la app.

## 🤝 Contribuir

- Issues y PRs bienvenidos.
- Nuevas mini-apps: crea un paquete en `app/src/main/java/.../exercises/`
  y registra la ruta en `ExercisesNav.kt`.
- Nuevos ejercicios Kotlin: crea un paquete en `exercises/src/main/kotlin/...`
  con su test hermano.

## 📜 Licencia

Plantilla de uso libre para fines educativos.