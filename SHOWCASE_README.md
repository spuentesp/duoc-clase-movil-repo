# Material Design 3 Showcase

Una aplicación educativa completa que muestra todos los componentes de Material Design 3 disponibles en Jetpack Compose.

## 🎯 Propósito

Esta aplicación sirve como catálogo interactivo para estudiantes y desarrolladores que desean:
- Aprender sobre los componentes de Material Design 3
- Ver ejemplos funcionales de cada componente
- Entender cuándo y cómo usar cada elemento de UI
- Experimentar con diferentes variantes y configuraciones

## 📱 Características

### Categorías de Componentes

1. **Buttons** - Botones y variantes
   - Button (Filled, Elevated, Tonal, Outlined, Text)
   - FloatingActionButton (Small, Normal, Large, Extended)
   - IconButton y variantes
   - IconToggleButton

2. **Text Fields** - Campos de entrada de texto
   - TextField y OutlinedTextField
   - Validación y estados de error
   - Password fields con visibilidad
   - Diferentes tipos de teclado
   - Campos multilinea

3. **Cards** - Tarjetas y contenedores
   - Card, ElevatedCard, OutlinedCard
   - Cards interactivas (clickeables)
   - Cards con imágenes y acciones
   - Cards de perfil

4. **Lists** - Listas y elementos
   - ListItem con diferentes configuraciones
   - LazyColumn y LazyRow
   - Listas con checkboxes y radio buttons
   - Listas agrupadas con encabezados

5. **Dialogs** - Diálogos y alertas
   - AlertDialog simple
   - Diálogos de confirmación
   - Diálogos con listas
   - Diálogos personalizados con formularios

6. **Navigation** - Navegación lateral
   - NavigationDrawer (Modal)
   - NavigationDrawerItem
   - Gestión de estado de apertura/cierre

7. **Bottom Sheets & Snackbars**
   - ModalBottomSheet
   - Snackbar simple y con acciones
   - Gestión de duración y callbacks

8. **App Bars** - Barras de aplicación
   - TopAppBar con navegación y acciones
   - BottomAppBar con FAB integrado
   - Diferentes scroll behaviors

9. **Selection Controls** - Controles de selección
   - Checkbox
   - RadioButton
   - Switch
   - Slider y RangeSlider

10. **Icons & Badges** - Íconos y badges
    - Material Icons
    - Badge simple y con número
    - Avatares
    - Chips con íconos

11. **Theming** - Sistema de diseño
    - Typography (13 estilos de texto)
    - Color Scheme (colores dinámicos)
    - Shapes (formas y bordes redondeados)
    - Elevation (elevación y sombras)

## 🏗️ Arquitectura del Proyecto

```
app/src/main/java/com/example/baseproject/
├── ui/
│   ├── navigation/
│   │   └── ShowcaseNavigation.kt      # Configuración de navegación
│   ├── screens/
│   │   ├── MainScreen.kt              # Pantalla principal
│   │   ├── ShowcaseCategory.kt        # Modelo de datos
│   │   ├── buttons/
│   │   │   └── ButtonsScreen.kt
│   │   ├── textfields/
│   │   │   └── TextFieldsScreen.kt
│   │   ├── cards/
│   │   │   └── CardsScreen.kt
│   │   ├── lists/
│   │   │   └── ListsScreen.kt
│   │   ├── dialogs/
│   │   │   └── DialogsScreen.kt
│   │   ├── navigation/
│   │   │   └── NavigationScreen.kt
│   │   ├── bottomsheets/
│   │   │   └── BottomSheetsScreen.kt
│   │   ├── appbars/
│   │   │   └── AppBarsScreen.kt
│   │   ├── selectioncontrols/
│   │   │   └── SelectionControlsScreen.kt
│   │   ├── icons/
│   │   │   └── IconsScreen.kt
│   │   └── theming/
│   │       └── ThemingScreen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── MainActivity.kt
```

## 🚀 Cómo Usar

### Navegación

1. **Pantalla Principal**: Muestra una cuadrícula con todas las categorías disponibles
2. **Selección**: Toca cualquier categoría para explorar sus componentes
3. **Interacción**: La mayoría de componentes son interactivos - ¡pruébalos!
4. **Regreso**: Usa el botón de flecha hacia atrás para volver al menú principal

### Experimentación

Cada pantalla incluye:
- Ejemplos funcionales que puedes interactuar
- Código comentado para entender la implementación
- Diferentes variantes del mismo componente
- Buenas prácticas de uso

## 📚 Aprendizaje

### Para Estudiantes

Este showcase está diseñado específicamente para facilitar el aprendizaje:

1. **Exploración Visual**: Ve cómo se ven los componentes en acción
2. **Comparación**: Compara diferentes variantes lado a lado
3. **Interactividad**: Experimenta con los componentes para entender su comportamiento
4. **Código Limpio**: Todo el código está comentado y organizado

### Componentes Reutilizables

El componente `ComponentSection` (en ButtonsScreen.kt) es reutilizable:

```kotlin
ComponentSection(title = "Mi Sección") {
    // Tus componentes aquí
    Button(onClick = {}) {
        Text("Mi Botón")
    }
}
```

## 🎨 Personalización

### Cambiar el Tema

El tema está definido en `ui/theme/Theme.kt`. Para personalizar:

1. Modifica los colores en `Color.kt`
2. Ajusta la tipografía en `Type.kt`
3. Cambia las formas en `Theme.kt`

### Agregar Nuevas Categorías

1. Crea un nuevo screen en `ui/screens/tu_categoria/`
2. Agrega la ruta en `ShowcaseNavigation.kt`
3. Agrega la categoría en `showcaseCategories` (ShowcaseCategory.kt)

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación
- **Jetpack Compose**: Framework de UI declarativa
- **Material Design 3**: Sistema de diseño
- **Navigation Compose**: Navegación entre pantallas
- **Material Icons**: Biblioteca de íconos

## 📖 Recursos Adicionales

### Documentación Oficial

- [Material Design 3](https://m3.material.io/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Components Compose](https://developer.android.com/jetpack/androidx/releases/compose-material3)

### Guías de Diseño

- [Material You](https://material.io/blog/announcing-material-you)
- [Color System](https://m3.material.io/styles/color/overview)
- [Typography](https://m3.material.io/styles/typography/overview)

## 🤝 Contribución

Para agregar nuevos ejemplos o mejorar los existentes:

1. Crea una nueva rama desde `feature/material-showcase`
2. Implementa tus cambios siguiendo el estilo existente
3. Asegúrate de comentar tu código
4. Prueba que todo funcione correctamente

## 📝 Notas de Implementación

### Composables Clave

- `@OptIn(ExperimentalMaterial3Api::class)`: Algunos componentes son experimentales
- `remember`: Para mantener estado en recomposiciones
- `LazyColumn/LazyRow`: Para listas eficientes
- `Scaffold`: Para estructura de pantalla con TopBar y BottomBar

### Estado y Eventos

Todos los componentes interactivos demuestran:
- Manejo de estado con `remember` y `mutableStateOf`
- Callbacks para eventos del usuario
- Actualización reactiva de la UI

## 🎓 Ejercicios Sugeridos

1. **Personaliza el tema** con tus propios colores
2. **Crea una nueva categoría** de componentes
3. **Modifica los ejemplos** para adaptarlos a tu caso de uso
4. **Combina componentes** para crear interfaces más complejas
5. **Implementa validación** en los formularios de ejemplo

## 📄 Licencia

Este proyecto es educativo y está disponible para uso académico.

---

**Desarrollado con ❤️ para estudiantes de desarrollo Android**
