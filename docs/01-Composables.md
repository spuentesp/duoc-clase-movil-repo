# Introducción a Jetpack Compose

## ¿Qué es Jetpack Compose?

Jetpack Compose es el toolkit moderno de Android para construir interfaces de usuario (UI) de forma declarativa. En lugar de usar XML para definir layouts, escribes código Kotlin que describe cómo debería verse tu UI.

### Diferencia entre UI tradicional y Compose

**Forma tradicional (XML):**
```xml
<TextView
    android:text="Hola Mundo"
    android:textSize="20sp" />
```

**Con Compose (Kotlin):**
```kotlin
Text(text = "Hola Mundo", fontSize = 20.sp)
```

## ¿Qué es un Composable?

Un **Composable** es una función especial en Kotlin que:
1. Está marcada con la anotación `@Composable`
2. Describe una parte de la UI
3. Puede recibir parámetros para personalizar su apariencia
4. Se puede componer con otros Composables

### Ejemplo básico

```kotlin
@Composable
fun Saludo() {
    Text(text = "¡Hola desde Compose!")
}
```

### Composable con parámetros

```kotlin
@Composable
fun SaludoPersonalizado(nombre: String) {
    Text(text = "¡Hola $nombre!")
}

// Usar el composable
SaludoPersonalizado(nombre = "María")
```

## ¿Cómo funciona Compose?

Compose funciona con el concepto de **recomposición**:

1. **Primera composición:** Compose ejecuta tus funciones composables y crea la UI
2. **Recomposición:** Cuando los datos cambian, Compose vuelve a ejecutar solo las funciones que necesitan actualizarse
3. **Eficiencia:** Solo se actualizan las partes de la UI que cambiaron

### Estado (State)

Para que la UI se actualice automáticamente, usamos `remember` y `mutableStateOf`:

```kotlin
@Composable
fun Contador() {
    // Crear una variable que Compose observa
    var contador by remember { mutableStateOf(0) }

    Column {
        Text(text = "Has presionado $contador veces")
        Button(onClick = { contador++ }) {
            Text("Presionar")
        }
    }
}
```

**¿Qué pasa aquí?**
- `remember`: Guarda el valor entre recomposiciones
- `mutableStateOf`: Crea un estado observable
- Cuando `contador` cambia, Compose redibuja automáticamente el `Text`

## Widgets Básicos (Composables comunes)

### 1. Text - Mostrar texto

```kotlin
@Composable
fun EjemploText() {
    Text(
        text = "Texto simple",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Blue
    )
}
```

### 2. Button - Botones

```kotlin
@Composable
fun EjemploButton() {
    Button(
        onClick = {
            println("¡Botón presionado!")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        )
    ) {
        Text("Presionar aquí")
    }
}
```

### 3. TextField - Entrada de texto

```kotlin
@Composable
fun EjemploTextField() {
    var texto by remember { mutableStateOf("") }

    TextField(
        value = texto,
        onValueChange = { nuevoTexto ->
            texto = nuevoTexto
        },
        label = { Text("Escribe algo") },
        placeholder = { Text("Tu texto aquí...") }
    )
}
```

### 4. Image - Mostrar imágenes

```kotlin
@Composable
fun EjemploImage() {
    Image(
        painter = painterResource(id = R.drawable.mi_imagen),
        contentDescription = "Descripción de la imagen",
        modifier = Modifier.size(100.dp)
    )
}
```

### 5. Card - Tarjetas

```kotlin
@Composable
fun EjemploCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Título de la tarjeta", fontWeight = FontWeight.Bold)
            Text("Contenido de la tarjeta")
        }
    }
}
```

## Layouts - Organizar elementos

### Column - Elementos en vertical

```kotlin
@Composable
fun EjemploColumn() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Elemento 1")
        Text("Elemento 2")
        Text("Elemento 3")
    }
}
```

**Opciones de alineación vertical:**
- `Arrangement.Top`: Arriba
- `Arrangement.Center`: Centro
- `Arrangement.Bottom`: Abajo
- `Arrangement.SpaceBetween`: Espacio entre elementos
- `Arrangement.SpaceEvenly`: Espacio uniforme

### Row - Elementos en horizontal

```kotlin
@Composable
fun EjemploRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Izquierda")
        Text("Centro")
        Text("Derecha")
    }
}
```

### Box - Elementos apilados

```kotlin
@Composable
fun EjemploBox() {
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Fondo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        )
        // Texto encima
        Text("Texto centrado", fontWeight = FontWeight.Bold)
    }
}
```

### LazyColumn - Lista con scroll

```kotlin
@Composable
fun EjemploLista() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    LazyColumn {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
```

## Modifiers - Personalizar apariencia

Los `Modifier` permiten modificar la apariencia y comportamiento de los composables:

```kotlin
@Composable
fun EjemploModifiers() {
    Text(
        text = "Texto modificado",
        modifier = Modifier
            .fillMaxWidth()           // Ocupa todo el ancho
            .padding(16.dp)           // Añade espaciado
            .background(Color.Yellow) // Fondo amarillo
            .border(2.dp, Color.Black) // Borde negro
            .clickable {
                println("¡Texto presionado!")
            }
    )
}
```

**Modifiers comunes:**
- `fillMaxSize()`: Ocupa todo el espacio disponible
- `fillMaxWidth()`: Ocupa todo el ancho
- `fillMaxHeight()`: Ocupa todo el alto
- `size(100.dp)`: Tamaño fijo
- `padding(16.dp)`: Espaciado interno
- `background(Color.Red)`: Color de fondo
- `clickable { }`: Hacer clickeable
- `align()`: Alineación dentro del contenedor

## Material Design 3 en Compose

Material Design es el sistema de diseño de Google. Compose lo implementa con componentes listos para usar.

### Tema y Colores

```kotlin
@Composable
fun MiApp() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFFFFFBFE)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Tu contenido aquí
        }
    }
}
```

### Componentes Material

#### Button variants

```kotlin
@Composable
fun BotonesMaterial() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Botón elevado (el más común)
        Button(onClick = { }) {
            Text("Button")
        }

        // Botón de texto
        TextButton(onClick = { }) {
            Text("Text Button")
        }

        // Botón con contorno
        OutlinedButton(onClick = { }) {
            Text("Outlined Button")
        }

        // Botón flotante
        FloatingActionButton(onClick = { }) {
            Icon(Icons.Default.Add, contentDescription = "Añadir")
        }
    }
}
```

#### TextField variants

```kotlin
@Composable
fun TextFieldsMaterial() {
    var texto by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // TextField normal
        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("TextField") }
        )

        // TextField con contorno
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Outlined TextField") }
        )
    }
}
```

#### Iconos Material

```kotlin
@Composable
fun EjemploIconos() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(Icons.Default.Home, contentDescription = "Inicio")
        Icon(Icons.Default.Favorite, contentDescription = "Favorito")
        Icon(Icons.Default.Settings, contentDescription = "Configuración")
        Icon(Icons.Default.Person, contentDescription = "Perfil")
    }
}
```

### Scaffold - Estructura de pantalla

`Scaffold` proporciona la estructura básica de una pantalla Material:

```kotlin
@Composable
fun PantallaConScaffold() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    ) { paddingValues ->
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Contenido de la pantalla")
        }
    }
}
```

## Ejemplo Completo: Formulario de Login

```kotlin
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo o título
        Text(
            text = "Bienvenido",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Contraseña")
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password"
                    )
                }
            },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de login
        Button(
            onClick = {
                println("Login: $email")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de registro
        TextButton(onClick = { }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}
```

## Consejos y Mejores Prácticas

1. **Mantén los Composables pequeños:** Divide funciones grandes en composables más pequeños
2. **Usa estados correctamente:** Solo convierte en estado lo que realmente necesita provocar recomposición
3. **Preview para desarrollo:** Usa `@Preview` para ver tus composables sin ejecutar la app

```kotlin
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}
```

4. **Nombra descriptivamente:** Los nombres de los composables deben describir qué muestran (ej: `LoginButton`, `UserProfileCard`)
5. **Reutiliza componentes:** Crea composables reutilizables para elementos que se repiten

## Recursos Adicionales

- **Documentación oficial:** [developer.android.com/jetpack/compose](https://developer.android.com/jetpack/compose)
- **Material Design 3:** [m3.material.io](https://m3.material.io)
- **Iconos Material:** [fonts.google.com/icons](https://fonts.google.com/icons)
