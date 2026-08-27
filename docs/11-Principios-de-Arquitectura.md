# 11 — Principios de Arquitectura

Compendio escrito de los principios detrás de las 6 cápsulas de
Arquitectura en `exercises/_20_` a `_25_`. Cada cápsula tiene su `.kt`
ejecutable; este documento los explica con más profundidad.

## 1. Separar UI de lógica

**Principio**: el composable no debe contener reglas de negocio. Debe
emitir eventos y observar estado.

**Por qué**:
- Testeable sin emulador.
- Lógica reutilizable en otras pantallas.
- Refactors de UI no rompen reglas.

**Cómo se ve en código**: ViewModel con `MutableStateFlow` +
`collectAsState()` en el composable.

## 2. MVVM vs MVC

**Diferencia clave**: en MVVM, la View es pasiva y solo observa el
ViewModel. En MVC clásico, la View puede leer el Model directamente.

**Por qué importa**: en MVVM el sentido de dependencias es siempre
View → ViewModel → Model. Es más fácil de razonar.

## 3. Repository Pattern

**Principio**: la UI pide "dame los items" sin saber de dónde vienen.

**Por qué**:
- Cambiar la fuente (API ↔ DB ↔ fake) sin tocar UI ni ViewModel.
- Testear con un fake.
- Punto único para caché, retry, logging.

**Cuándo NO**: si solo hay una fuente y no planeas testear, agregar
Repository es sobreingeniería.

## 4. Unidirectional Data Flow (UDF)

**Principio**: View → ViewModel (eventos), ViewModel → View (estado).
La View no modifica el estado directamente.

**Por qué**:
- Estado siempre en un solo lugar.
- Testeable: dado un evento, espero un estado.
- Sin "fuentes de verdad" múltiples.

**Antipatrón**: variables `var count` en el composable que la View y
la lógica modifican a la vez. Eso es bidireccional y propenso a bugs.

## 5. Inyección de dependencias (manual)

**Principio**: las dependencias se pasan por constructor, no se crean
dentro.

**Por qué**:
- Testeable: en tests pasas un fake.
- Transparente: la firma del constructor documenta las necesidades.
- Sin magia: no necesitas Koin/Hilt para empezar.

**Cuándo SÍ frameworks**: cuando el grafo crece (10+ clases), la
inyección manual se vuelve boilerplate. Ahí entra Hilt.

## 6. Sealed class para UI state

**Principio**: los estados discretos de una pantalla se modelan con
sealed class.

**Por qué**:
- El compilador obliga a manejar todos los casos en `when`.
- Refactors seguros: agregar un estado nuevo rompe el código que no
  lo maneje, y eso es bueno.

**Antipatrón**: modelar estado como String ("loading"/"ok"/"error").
Typos silenciosos, sin chequeo de exhaustividad.

## Resumen

| Principio | Antipatrón |
|-----------|-----------|
| UI sin lógica | Lógica en el composable |
| MVVM | View lee Model directamente |
| Repository | ViewModel conoce la API |
| UDF | Estado mutable bidireccional |
| DI manual | `new ApiClient()` dentro |
| Sealed state | Strings para estados |

Estos 6 principios son la base de cualquier app Android medianamente
compleja. Si entiendes el "por qué" de cada uno, no necesitas memorizar
el "qué".
