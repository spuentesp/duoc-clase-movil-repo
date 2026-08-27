# Documentación — Desarrollo Android con Kotlin y Jetpack Compose

Esta carpeta contiene guías completas para aprender desarrollo de
aplicaciones Android modernas, desde Kotlin básico hasta publicación.

## 📚 Índice de Contenidos

### Kotlin puro (módulo `exercises/`)

- **[00 — Fundamentos de Kotlin](00-Fundamentos-Kotlin.md)** — conceptos del lenguaje
- **[09 — Guía de Ejercicios Kotlin](09-Guia-Ejercicios-Kotlin.md)** — los 33 ejercicios, cómo correrlos, ruta

### Android (app)

- **[01 — Composables](01-Composables.md)** — Jetpack Compose y componentes UI
- **[02 — Navigation](02-Navigation.md)** — sistema de navegación
- **[03 — MVVM y Repository](03-MVVM-y-Repository.md)** — arquitectura
- **[04 — Conectando PokeAPI a Datos](04-conectando-pokeapi-a-datos.md)** — networking

### Distribución y personalización

- **[05 — Empaquetado y Distribución](05-Empaquetado-y-Distribucion.md)** — APK y Google Play
- **[06 — Personalización de la App](06-Personalizacion-de-la-App.md)** — nombre, ícono, splash

### Catálogos visuales

- **[07 — Componentes UI Material Design](07-Componentes-UI-Material-Design.md)** — showcase de componentes
- **[08 — Capacidades Nativas Android](08-Guia-Capacidades-Nativas.md)** — biometría, cámara, GPS, etc.

### Ejercicios integradores

- **[10 — Guía de Ejercicios Android](10-Guia-Ejercicios-Android.md)** — 8 mini-apps dentro de la app
- **[11 — Principios de Arquitectura](11-Principios-de-Arquitectura.md)** — el "por qué" detrás de las decisiones

---

## 🎯 Ruta de Aprendizaje Recomendada

| Semana | Nivel | Recursos |
|--------|-------|----------|
| 1-2 | Kotlin básico | `exercises/_00_` a `_05_` + [00](00-Fundamentos-Kotlin.md) |
| 3 | Kotlin intermedio | `exercises/_06_` a `_09_` |
| 4 | Kotlin avanzado | `exercises/_10_` a `_13_` |
| 5-6 | Coroutines | `exercises/_14_` a `_19_` |
| 7 | Arquitectura | `exercises/_20_` a `_25_` + [11](11-Principios-de-Arquitectura.md) |
| 8-9 | Bridge Android | `exercises/_26_` a `_30_` |
| 10 | Compose | app sección Material + [01](01-Composables.md) |
| 11 | Navegación | app sección Material (NavigationScreen) + [02](02-Navigation.md) |
| 12-13 | Arquitectura Android | app sección Ejercicios (mini-apps 5-7) + [03](03-MVVM-y-Repository.md) |
| 14 | Capacidades nativas | app sección Nativas + [08](08-Guia-Capacidades-Nativas.md) |
| 15 | Distribución | [05](05-Empaquetado-y-Distribucion.md) + [06](06-Personalizacion-de-la-App.md) |

---

## 🚀 Cómo empezar

### Si nunca programaste en Kotlin

1. Lee [00 — Fundamentos de Kotlin](00-Fundamentos-Kotlin.md).
2. Abre la carpeta `exercises/` y corre `./gradlew test`.
3. Verás que muchos tests fallan — eso es porque son ejercicios por hacer.
4. Empieza por `_00_variables/E01_var_val.kt`, implementa `sumar`, corre el test.

### Si ya sabes Kotlin

1. Lee [01 — Composables](01-Composables.md).
2. Abre la app en Android Studio.
3. Toca "🧪 Ejercicios Android" y resuelve las mini-apps en orden.

### Si quieres ver componentes

1. Abre la app.
2. Toca "🎨 Componentes Material" para ver el catálogo visual.
3. Lee [07](07-Componentes-UI-Material-Design.md) para profundizar.

### Si quieres ver capacidades nativas

1. Abre la app.
2. Toca "📱 Capacidades Nativas".
3. Lee [08](08-Guia-Capacidades-Nativas.md) para los permisos y consideraciones.

---

## 📞 Soporte

- [Documentación oficial de Android](https://developer.android.com/)
- [Kotlin Docs](https://kotlinlang.org/docs/home.html)
- [Jetpack Compose Pathway](https://developer.android.com/courses/pathways/compose)

---

**Última actualización**: ver `git log` del repo.