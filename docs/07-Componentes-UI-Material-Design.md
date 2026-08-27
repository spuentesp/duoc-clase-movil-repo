# 07 - Componentes UI de Material Design 3

## Índice
1. [Introducción a Material Design 3](#introducción-a-material-design-3)
2. [Botones](#botones)
3. [Campos de Texto](#campos-de-texto)
4. [Cards (Tarjetas)](#cards-tarjetas)
5. [Listas](#listas)
6. [Diálogos](#diálogos)
7. [Navegación](#navegación)
8. [Bottom Sheets y Snackbars](#bottom-sheets-y-snackbars)
9. [App Bars](#app-bars)
10. [Controles de Selección](#controles-de-selección)
11. [Íconos y Badges](#íconos-y-badges)
12. [Sistema de Theming](#sistema-de-theming)
13. [Buenas Prácticas](#buenas-prácticas)

---

## Introducción a Material Design 3

Material Design 3 (también conocido como Material You) es el sistema de diseño de Google que proporciona componentes, patrones y directrices para crear interfaces de usuario modernas y accesibles.

### Características Principales

- **Material You**: Personalización dinámica basada en el wallpaper del usuario
- **Colores Adaptativos**: Soporte automático para modo claro y oscuro
- **Componentes Modernos**: Elementos UI actualizados y refinados
- **Accesibilidad**: Diseñado desde el principio para todos los usuarios

### Importar Material Design 3

```kotlin
// En build.gradle.kts
implementation("androidx.compose.material3:material3")
```

---

## Botones

Los botones permiten a los usuarios realizar acciones con un simple toque.

### Tipos de Botones

#### 1. Button (Filled Button)
El botón más prominente, usado para acciones principales.

```kotlin
Button(
    onClick = { /* acción */ }
) {
    Icon(Icons.Default.Check, contentDescription = null)
    Spacer(modifier = Modifier.width(8.dp))
    Text("Guardar")
}
```

#### 2. ElevatedButton
Botón con elevación, útil para destacar en superficies planas.

```kotlin
ElevatedButton(
    onClick = { /* acción */ }
) {
    Text("Elevated")
}
```

#### 3. FilledTonalButton
Botón con color tonal, menos prominente que el filled.

```kotlin
FilledTonalButton(
    onClick = { /* acción */ }
) {
    Text("Tonal")
}
```

#### 4. OutlinedButton
Botón con borde, para acciones secundarias.

```kotlin
OutlinedButton(
    onClick = { /* acción */ }
) {
    Text("Cancelar")
}
```

#### 5. TextButton
Botón de texto sin fondo, menor énfasis.

```kotlin
TextButton(
    onClick = { /* acción */ }
) {
    Text("Más información")
}
```

### Floating Action Buttons (FAB)

Botones flotantes para acciones principales de la pantalla.

```kotlin
// FAB normal
FloatingActionButton(
    onClick = { /* acción */ }
) {
    Icon(Icons.Default.Add, contentDescription = "Agregar")
}

// Extended FAB con texto
ExtendedFloatingActionButton(
    onClick = { /* acción */ },
    icon = { Icon(Icons.Default.Edit, contentDescription = null) },
    text = { Text("Editar") }
)

// Tamaños: Small, Normal, Large
SmallFloatingActionButton(onClick = {}) {
    Icon(Icons.Default.Edit, contentDescription = "Editar")
}

LargeFloatingActionButton(onClick = {}) {
    Icon(
        Icons.Default.Add,
        contentDescription = "Agregar",
        modifier = Modifier.size(36.dp)
    )
}
```

### Icon Buttons

Botones con solo íconos, ideales para barras de herramientas.

```kotlin
IconButton(onClick = { /* acción */ }) {
    Icon(Icons.Default.Settings, contentDescription = "Configuración")
}

FilledIconButton(onClick = {}) {
    Icon(Icons.Default.Favorite, contentDescription = "Favorito")
}

OutlinedIconButton(onClick = {}) {
    Icon(Icons.Default.Share, contentDescription = "Compartir")
}
```

### Icon Toggle Buttons

Botones con estado on/off.

```kotlin
var checked by remember { mutableStateOf(false) }

IconToggleButton(
    checked = checked,
    onCheckedChange = { checked = it }
) {
    Icon(
        if (checked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = "Toggle"
    )
}
```

### Cuándo Usar Cada Botón

| Tipo | Uso Recomendado |
|------|----------------|
| Button (Filled) | Acción principal más importante |
| ElevatedButton | Destacar en superficies de baja elevación |
| FilledTonalButton | Acción principal con menos énfasis |
| OutlinedButton | Acción secundaria importante |
| TextButton | Acción secundaria menos importante |
| FAB | Acción principal flotante de la pantalla |

---

## Campos de Texto

Los campos de texto permiten a los usuarios ingresar y editar texto.

### TextField vs OutlinedTextField

```kotlin
// TextField - Estilo filled
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Nombre") },
    placeholder = { Text("Ingresa tu nombre") }
)

// OutlinedTextField - Con borde
OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Email") },
    leadingIcon = {
        Icon(Icons.Default.Email, contentDescription = null)
    }
)
```

### Componentes del TextField

```kotlin
OutlinedTextField(
    value = text,
    onValueChange = { text = it },

    // Label que flota hacia arriba
    label = { Text("Etiqueta") },

    // Texto de ayuda
    placeholder = { Text("Placeholder") },

    // Ícono al inicio
    leadingIcon = {
        Icon(Icons.Default.Person, contentDescription = null)
    },

    // Ícono al final (ej: botón limpiar)
    trailingIcon = {
        IconButton(onClick = { text = "" }) {
            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
        }
    },

    // Texto de soporte debajo
    supportingText = {
        Text("Texto de ayuda o contador: ${text.length}/50")
    }
)
```

### Validación y Estados

```kotlin
var email by remember { mutableStateOf("") }
val isError = email.isNotEmpty() && !email.contains("@")

OutlinedTextField(
    value = email,
    onValueChange = { email = it },
    label = { Text("Email") },
    isError = isError,
    supportingText = {
        if (isError) {
            Text("Email inválido")
        }
    },
    trailingIcon = {
        if (isError) {
            Icon(
                Icons.Default.Error,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
)
```

### Campo de Contraseña

```kotlin
var password by remember { mutableStateOf("") }
var passwordVisible by remember { mutableStateOf(false) }

OutlinedTextField(
    value = password,
    onValueChange = { password = it },
    label = { Text("Contraseña") },
    visualTransformation = if (passwordVisible)
        VisualTransformation.None
    else
        PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password
    ),
    trailingIcon = {
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
                if (passwordVisible)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff,
                contentDescription = "Toggle password"
            )
        }
    }
)
```

### Tipos de Teclado

```kotlin
// Email
OutlinedTextField(
    value = email,
    onValueChange = { email = it },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email
    )
)

// Número
OutlinedTextField(
    value = number,
    onValueChange = { number = it },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    )
)

// Teléfono
OutlinedTextField(
    value = phone,
    onValueChange = { phone = it },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
    )
)
```

### TextField Multilinea

```kotlin
var notes by remember { mutableStateOf("") }

OutlinedTextField(
    value = notes,
    onValueChange = { notes = it },
    label = { Text("Notas") },
    minLines = 4,
    maxLines = 6,
    modifier = Modifier.fillMaxWidth()
)
```

---

## Cards (Tarjetas)

Las cards agrupan contenido relacionado y acciones sobre un tema.

### Tipos de Cards

```kotlin
// Card estándar (Filled)
Card(
    modifier = Modifier.fillMaxWidth()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Título",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            "Contenido de la card",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Elevated Card
ElevatedCard(
    modifier = Modifier.fillMaxWidth()
) {
    // Contenido
}

// Outlined Card
OutlinedCard(
    modifier = Modifier.fillMaxWidth()
) {
    // Contenido
}
```

### Card Clickeable

```kotlin
Card(
    onClick = { /* acción */ },
    modifier = Modifier.fillMaxWidth()
) {
    // Contenido
}
```

### Card con Imagen y Acciones

```kotlin
ElevatedCard(
    modifier = Modifier.fillMaxWidth()
) {
    Column {
        // Imagen (simulada con Surface)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        // Contenido
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Título de la Card",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                "Descripción del contenido",
                style = MaterialTheme.typography.bodyMedium
            )

            // Acciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {}) {
                    Text("Cancelar")
                }
                Button(onClick = {}) {
                    Text("Aceptar")
                }
            }
        }
    }
}
```

---

## Listas

Las listas muestran múltiples elementos en un formato vertical.

### ListItem

Componente optimizado para elementos de lista.

```kotlin
// ListItem simple
ListItem(
    headlineContent = { Text("Título") }
)

// ListItem con subtítulo
ListItem(
    headlineContent = { Text("Título") },
    supportingContent = { Text("Subtítulo") }
)

// ListItem completo
ListItem(
    headlineContent = { Text("Título") },
    supportingContent = { Text("Descripción") },
    leadingContent = {
        Icon(Icons.Default.Person, contentDescription = null)
    },
    trailingContent = {
        Icon(Icons.Default.ChevronRight, contentDescription = null)
    }
)
```

### LazyColumn

Lista eficiente para grandes cantidades de datos.

```kotlin
LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(100) { index ->
        ListItem(
            headlineContent = { Text("Elemento $index") }
        )
    }
}

// Con lista de datos
val items = listOf("Item 1", "Item 2", "Item 3")

LazyColumn {
    items(items) { item ->
        ListItem(
            headlineContent = { Text(item) }
        )
    }
}
```

### LazyRow

Lista horizontal eficiente.

```kotlin
LazyRow(
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    contentPadding = PaddingValues(16.dp)
) {
    items(10) { index ->
        Card(
            modifier = Modifier.size(150.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Item $index")
            }
        }
    }
}
```

### Lista con Checkboxes

```kotlin
val checkedStates = remember {
    mutableStateListOf(false, true, false)
}
val items = listOf("Tarea 1", "Tarea 2", "Tarea 3")

items.forEachIndexed { index, item ->
    ListItem(
        headlineContent = { Text(item) },
        leadingContent = {
            Checkbox(
                checked = checkedStates[index],
                onCheckedChange = { checkedStates[index] = it }
            )
        }
    )
    if (index < items.lastIndex) {
        Divider()
    }
}
```

---

## Diálogos

Los diálogos interrumpen al usuario para solicitar información o confirmación.

### AlertDialog Simple

```kotlin
var showDialog by remember { mutableStateOf(false) }

Button(onClick = { showDialog = true }) {
    Text("Mostrar Diálogo")
}

if (showDialog) {
    AlertDialog(
        onDismissRequest = { showDialog = false },
        title = { Text("Título") },
        text = { Text("Contenido del diálogo") },
        confirmButton = {
            TextButton(onClick = { showDialog = false }) {
                Text("Aceptar")
            }
        }
    )
}
```

### Diálogo de Confirmación

```kotlin
AlertDialog(
    onDismissRequest = { showDialog = false },
    icon = {
        Icon(Icons.Default.Warning, contentDescription = null)
    },
    title = { Text("¿Eliminar elemento?") },
    text = {
        Text("Esta acción no se puede deshacer.")
    },
    confirmButton = {
        Button(onClick = {
            // Eliminar
            showDialog = false
        }) {
            Text("Eliminar")
        }
    },
    dismissButton = {
        TextButton(onClick = { showDialog = false }) {
            Text("Cancelar")
        }
    }
)
```

### Diálogo Personalizado

```kotlin
AlertDialog(
    onDismissRequest = { showDialog = false },
    title = { Text("Formulario") },
    text = {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    },
    confirmButton = {
        Button(onClick = { showDialog = false }) {
            Text("Enviar")
        }
    },
    dismissButton = {
        TextButton(onClick = { showDialog = false }) {
            Text("Cancelar")
        }
    }
)
```

---

## Navegación

### Navigation Drawer

Panel lateral para navegación entre secciones principales.

```kotlin
val drawerState = rememberDrawerState(DrawerValue.Closed)
val scope = rememberCoroutineScope()
var selectedItem by remember { mutableStateOf("Inicio") }

ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
        ModalDrawerSheet {
            Text(
                "Menú",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Divider()

            val items = listOf(
                "Inicio" to Icons.Default.Home,
                "Perfil" to Icons.Default.Person,
                "Configuración" to Icons.Default.Settings
            )

            items.forEach { (title, icon) ->
                NavigationDrawerItem(
                    icon = { Icon(icon, contentDescription = null) },
                    label = { Text(title) },
                    selected = selectedItem == title,
                    onClick = {
                        selectedItem = title
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
) {
    // Contenido principal
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        // Contenido
    }
}
```

---

## Bottom Sheets y Snackbars

### Modal Bottom Sheet

Panel que se desliza desde abajo.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Button(onClick = { showBottomSheet = true }) {
        Text("Mostrar Bottom Sheet")
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Opciones",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                ListItem(
                    headlineContent = { Text("Opción 1") },
                    leadingContent = {
                        Icon(Icons.Default.Share, contentDescription = null)
                    }
                )
                ListItem(
                    headlineContent = { Text("Opción 2") },
                    leadingContent = {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
```

### Snackbar

Mensaje temporal en la parte inferior de la pantalla.

```kotlin
val snackbarHostState = remember { SnackbarHostState() }
val scope = rememberCoroutineScope()

Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) }
) { padding ->
    Button(
        onClick = {
            scope.launch {
                snackbarHostState.showSnackbar("Mensaje guardado")
            }
        }
    ) {
        Text("Mostrar Snackbar")
    }
}

// Snackbar con acción
scope.launch {
    val result = snackbarHostState.showSnackbar(
        message = "Elemento eliminado",
        actionLabel = "Deshacer",
        duration = SnackbarDuration.Long
    )
    when (result) {
        SnackbarResult.ActionPerformed -> {
            // Usuario presionó "Deshacer"
        }
        SnackbarResult.Dismissed -> {
            // Snackbar se cerró
        }
    }
}
```

---

## App Bars

### TopAppBar

Barra superior con título y acciones.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    TopAppBar(
        title = { Text("Mi App") },
        navigationIcon = {
            IconButton(onClick = { /* acción */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        },
        actions = {
            IconButton(onClick = { /* acción */ }) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
            IconButton(onClick = { /* acción */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Más")
            }
        }
    )
}
```

### Medium y Large TopAppBar

```kotlin
// Medium TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBarExample() {
    MediumTopAppBar(
        title = { Text("Título Medio") },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        }
    )
}

// Large TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeTopAppBarExample() {
    LargeTopAppBar(
        title = { Text("Título Grande") }
    )
}
```

### BottomAppBar

Barra inferior con acciones y FAB.

```kotlin
Scaffold(
    bottomBar = {
        BottomAppBar(
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Person, contentDescription = "Perfil")
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    elevation = BottomAppBarDefaults.floatingActionButtonElevation()
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        )
    }
) { padding ->
    // Contenido
}
```

---

## Controles de Selección

### Checkbox

```kotlin
var checked by remember { mutableStateOf(false) }

Row(
    verticalAlignment = Alignment.CenterVertically
) {
    Checkbox(
        checked = checked,
        onCheckedChange = { checked = it }
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text("Aceptar términos y condiciones")
}
```

### RadioButton

```kotlin
var selectedOption by remember { mutableStateOf(0) }
val options = listOf("Opción 1", "Opción 2", "Opción 3")

Column {
    options.forEachIndexed { index, option ->
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == index,
                onClick = { selectedOption = index }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(option)
        }
    }
}
```

### Switch

```kotlin
var enabled by remember { mutableStateOf(false) }

Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Text("Notificaciones")
    Switch(
        checked = enabled,
        onCheckedChange = { enabled = it }
    )
}
```

### Slider

```kotlin
var value by remember { mutableStateOf(50f) }

Column {
    Text("Volumen: ${value.toInt()}%")
    Slider(
        value = value,
        onValueChange = { value = it },
        valueRange = 0f..100f,
        modifier = Modifier.fillMaxWidth()
    )
}

// Slider con steps
Slider(
    value = value,
    onValueChange = { value = it },
    valueRange = 0f..100f,
    steps = 9, // 11 posiciones en total (0, 10, 20, ..., 100)
    modifier = Modifier.fillMaxWidth()
)
```

### RangeSlider

```kotlin
var range by remember { mutableStateOf(20f..80f) }

Column {
    Text("Rango: ${range.start.toInt()} - ${range.endInclusive.toInt()}")
    RangeSlider(
        value = range,
        onValueChange = { range = it },
        valueRange = 0f..100f,
        modifier = Modifier.fillMaxWidth()
    )
}
```

---

## Íconos y Badges

### Material Icons

```kotlin
// Ícono básico
Icon(
    Icons.Default.Favorite,
    contentDescription = "Favorito"
)

// Ícono con color
Icon(
    Icons.Default.Star,
    contentDescription = "Estrella",
    tint = MaterialTheme.colorScheme.primary
)

// Ícono con tamaño personalizado
Icon(
    Icons.Default.Home,
    contentDescription = "Home",
    modifier = Modifier.size(48.dp)
)
```

### Badges

```kotlin
// Badge simple
BadgedBox(
    badge = { Badge() }
) {
    Icon(
        Icons.Default.Notifications,
        contentDescription = "Notificaciones"
    )
}

// Badge con número
BadgedBox(
    badge = {
        Badge { Text("5") }
    }
) {
    Icon(
        Icons.Default.Email,
        contentDescription = "Mensajes"
    )
}

// Badge personalizado
BadgedBox(
    badge = {
        Badge(
            containerColor = MaterialTheme.colorScheme.error
        ) {
            Text("New")
        }
    }
) {
    Icon(
        Icons.Default.ShoppingCart,
        contentDescription = "Carrito"
    )
}
```

### Chips

```kotlin
// Assist Chip
AssistChip(
    onClick = {},
    label = { Text("Asistente") },
    leadingIcon = {
        Icon(
            Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
)

// Filter Chip
var selected by remember { mutableStateOf(false) }
FilterChip(
    selected = selected,
    onClick = { selected = !selected },
    label = { Text("Filtro") },
    leadingIcon = if (selected) {
        {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    } else null
)

// Input Chip
InputChip(
    selected = false,
    onClick = {},
    label = { Text("Tag") },
    trailingIcon = {
        IconButton(onClick = {}) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Eliminar",
                modifier = Modifier.size(18.dp)
            )
        }
    }
)

// Suggestion Chip
SuggestionChip(
    onClick = {},
    label = { Text("Sugerencia") }
)
```

---

## Sistema de Theming

### MaterialTheme

El `MaterialTheme` proporciona colores, tipografía y formas a todos los componentes.

```kotlin
MaterialTheme(
    colorScheme = /* esquema de colores */,
    typography = /* tipografía */,
    shapes = /* formas */
) {
    // Contenido de la app
}
```

### Typography

Material 3 define 13 estilos de texto.

```kotlin
// Títulos grandes
Text(
    "Display Large",
    style = MaterialTheme.typography.displayLarge
)
Text(
    "Display Medium",
    style = MaterialTheme.typography.displayMedium
)
Text(
    "Display Small",
    style = MaterialTheme.typography.displaySmall
)

// Encabezados
Text(
    "Headline Large",
    style = MaterialTheme.typography.headlineLarge
)
Text(
    "Headline Medium",
    style = MaterialTheme.typography.headlineMedium
)
Text(
    "Headline Small",
    style = MaterialTheme.typography.headlineSmall
)

// Títulos
Text(
    "Title Large",
    style = MaterialTheme.typography.titleLarge
)
Text(
    "Title Medium",
    style = MaterialTheme.typography.titleMedium
)
Text(
    "Title Small",
    style = MaterialTheme.typography.titleSmall
)

// Cuerpo de texto
Text(
    "Body Large",
    style = MaterialTheme.typography.bodyLarge
)
Text(
    "Body Medium",
    style = MaterialTheme.typography.bodyMedium
)
Text(
    "Body Small",
    style = MaterialTheme.typography.bodySmall
)

// Labels
Text(
    "Label Large",
    style = MaterialTheme.typography.labelLarge
)
Text(
    "Label Medium",
    style = MaterialTheme.typography.labelMedium
)
Text(
    "Label Small",
    style = MaterialTheme.typography.labelSmall
)
```

### Color Scheme

Material 3 usa un esquema de colores dinámico.

```kotlin
// Acceder a los colores del tema
val primaryColor = MaterialTheme.colorScheme.primary
val backgroundColor = MaterialTheme.colorScheme.background

// Usar colores en componentes
Surface(
    color = MaterialTheme.colorScheme.primaryContainer
) {
    Text(
        "Texto",
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}
```

**Roles de Color:**

- `primary` / `onPrimary`: Color principal de la marca
- `primaryContainer` / `onPrimaryContainer`: Contenedores con color principal
- `secondary` / `onSecondary`: Color secundario
- `secondaryContainer` / `onSecondaryContainer`: Contenedores secundarios
- `tertiary` / `onTertiary`: Color terciario
- `tertiaryContainer` / `onTertiaryContainer`: Contenedores terciarios
- `error` / `onError`: Para estados de error
- `background` / `onBackground`: Fondo de la app
- `surface` / `onSurface`: Superficies de componentes
- `surfaceVariant` / `onSurfaceVariant`: Variante de superficie

### Shapes

Define el radio de bordes de los componentes.

```kotlin
// Formas predefinidas
val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// Usar formas
Surface(
    shape = MaterialTheme.shapes.medium,
    color = MaterialTheme.colorScheme.primaryContainer
) {
    // Contenido
}
```

### Elevation

Crear profundidad con elevación.

```kotlin
Surface(
    tonalElevation = 3.dp,
    shadowElevation = 3.dp,
    shape = MaterialTheme.shapes.medium
) {
    // Contenido
}
```

**Niveles de elevación:**
- 0dp: Sin elevación (nivel de superficie)
- 1dp: Elevación muy baja
- 3dp: Elevación estándar para cards
- 6dp: Elevación para elementos flotantes
- 8dp+: Elevación alta para modales

---

## Buenas Prácticas

### 1. Usa los Componentes Correctos

- **Botones**: Usa Button para acciones primarias, TextButton para secundarias
- **Text Fields**: OutlinedTextField es más versátil, TextField para diseños densos
- **Cards**: No anides cards dentro de cards
- **Listas**: Usa LazyColumn/LazyRow para rendimiento óptimo

### 2. Respeta la Jerarquía Visual

```kotlin
// ✅ Bueno: Jerarquía clara
Column {
    Text(
        "Título Principal",
        style = MaterialTheme.typography.headlineMedium
    )
    Text(
        "Subtítulo",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

// ❌ Malo: Todo del mismo tamaño
Column {
    Text("Título Principal")
    Text("Subtítulo")
}
```

### 3. Usa Colores del Tema

```kotlin
// ✅ Bueno: Usa colores del tema
Icon(
    Icons.Default.Star,
    contentDescription = "Favorito",
    tint = MaterialTheme.colorScheme.primary
)

// ❌ Malo: Colores hardcodeados
Icon(
    Icons.Default.Star,
    contentDescription = "Favorito",
    tint = Color(0xFF6200EE) // No se adapta al tema
)
```

### 4. Spacing Consistente

```kotlin
// ✅ Bueno: Spacing consistente
Column(
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    Text("Item 1")
    Text("Item 2")
    Text("Item 3")
}

// ❌ Malo: Spacing inconsistente
Column {
    Text("Item 1")
    Spacer(modifier = Modifier.height(8.dp))
    Text("Item 2")
    Spacer(modifier = Modifier.height(24.dp))
    Text("Item 3")
}
```

### 5. Accesibilidad

```kotlin
// ✅ Bueno: Siempre incluye contentDescription
IconButton(onClick = {}) {
    Icon(
        Icons.Default.Settings,
        contentDescription = "Abrir configuración"
    )
}

// ✅ Bueno: Labels claros
TextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Nombre completo") }
)
```

### 6. Estados Loading

```kotlin
var isLoading by remember { mutableStateOf(false) }

Button(
    onClick = {
        isLoading = true
        // Operación async
    },
    enabled = !isLoading
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        Text("Guardar")
    }
}
```

### 7. Feedback al Usuario

```kotlin
// Siempre proporciona feedback
val snackbarHostState = remember { SnackbarHostState() }
val scope = rememberCoroutineScope()

Button(
    onClick = {
        // Realizar acción
        scope.launch {
            snackbarHostState.showSnackbar("Guardado exitosamente")
        }
    }
) {
    Text("Guardar")
}
```

### 8. Manejo de Errores

```kotlin
// Muestra errores de manera clara
OutlinedTextField(
    value = email,
    onValueChange = { email = it },
    isError = !isValidEmail(email),
    supportingText = {
        if (!isValidEmail(email)) {
            Text("Por favor ingresa un email válido")
        }
    }
)
```

---

## Recursos Adicionales

### Documentación Oficial

- [Material Design 3](https://m3.material.io/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Components Compose](https://developer.android.com/jetpack/androidx/releases/compose-material3)

### Herramientas

- [Material Theme Builder](https://m3.material.io/theme-builder): Crea tu tema personalizado
- [Material Icons](https://fonts.google.com/icons): Explora todos los íconos disponibles

### Showcase App

Este repositorio incluye una app showcase completa en la rama `feature/material-showcase` que demuestra todos estos componentes de forma interactiva.

---

## Conclusión

Material Design 3 proporciona un sistema completo de componentes para crear interfaces modernas y accesibles. Al seguir estas guías y buenas prácticas, podrás crear aplicaciones Android hermosas y funcionales que tus usuarios amarán.

**Recuerda:**
- Usa los componentes correctos para cada caso
- Mantén consistencia en diseño y spacing
- Respeta la jerarquía visual
- Siempre piensa en accesibilidad
- Proporciona feedback al usuario

¡Feliz desarrollo! 🚀
