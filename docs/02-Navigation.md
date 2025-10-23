# Navigation en Jetpack Compose

## ¿Qué es Navigation?

Navigation es el sistema que permite **moverse entre diferentes pantallas** en tu aplicación Android. En Compose, usamos la librería `Navigation Compose` que nos permite navegar entre composables de forma declarativa.

### Analogía simple

Imagina tu app como un edificio con varias habitaciones:
- Cada habitación es una **pantalla** (screen)
- Los pasillos son las **rutas** (routes)
- Tú eres el **NavController** que decide a qué habitación ir

## Conceptos Básicos

### 1. NavController

El `NavController` es el **cerebro de la navegación**. Es quien:
- Conoce en qué pantalla estás
- Te lleva a otras pantallas
- Maneja el botón "Atrás"
- Guarda el historial de navegación

```kotlin
val navController = rememberNavController()
```

### 2. NavHost

El `NavHost` es el **contenedor** donde se muestran las pantallas. Define:
- Qué pantallas existen en tu app
- Cuál es la pantalla inicial
- Cómo se llama cada pantalla (su ruta)

```kotlin
NavHost(
    navController = navController,
    startDestination = "home" // Pantalla inicial
) {
    // Aquí defines tus pantallas
}
```

### 3. Route (Ruta)

Una **ruta** es el nombre único que identifica una pantalla. Es como una dirección:

```kotlin
composable("home") { HomeScreen() }
composable("profile") { ProfileScreen() }
composable("settings") { SettingsScreen() }
```

**¿Por qué usamos Strings?**
- Son simples y fáciles de recordar
- Podemos incluir parámetros: `"profile/{userId}"`
- Son consistentes con el desarrollo web (URLs)
- Facilitan el testing y debugging

## Ejemplo Básico de Navigation

```kotlin
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Pantalla de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home")
                }
            )
        }

        // Pantalla de Home
        composable("home") {
            HomeScreen(
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }

        // Pantalla de Perfil
        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}
```

### Pantallas del ejemplo

```kotlin
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginSuccess,
            enabled = username.isNotEmpty()
        ) {
            Text("Iniciar Sesión")
        }
    }
}

@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Pantalla Principal", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToProfile) {
            Text("Ver Perfil")
        }
    }
}
```

## Navegación con Parámetros

A veces necesitas **pasar información** entre pantallas. Por ejemplo, pasar el ID de un usuario.

### Definir ruta con parámetros

```kotlin
NavHost(navController = navController, startDestination = "users") {
    // Lista de usuarios
    composable("users") {
        UsersListScreen(
            onUserClick = { userId ->
                // Navegar pasando el ID
                navController.navigate("user_detail/$userId")
            }
        )
    }

    // Detalle de usuario
    composable(
        route = "user_detail/{userId}",
        arguments = listOf(
            navArgument("userId") {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        // Obtener el parámetro
        val userId = backStackEntry.arguments?.getInt("userId") ?: 0

        UserDetailScreen(
            userId = userId,
            onBackClick = { navController.navigateUp() }
        )
    }
}
```

### Ejemplo completo con parámetros

```kotlin
data class User(val id: Int, val name: String, val email: String)

@Composable
fun UsersListScreen(onUserClick: (Int) -> Unit) {
    val users = listOf(
        User(1, "Juan Pérez", "juan@email.com"),
        User(2, "María García", "maria@email.com"),
        User(3, "Pedro López", "pedro@email.com")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(users) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onUserClick(user.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(user.name, fontWeight = FontWeight.Bold)
                    Text(user.email, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun UserDetailScreen(userId: Int, onBackClick: () -> Unit) {
    // En una app real, buscarías el usuario por ID
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBackClick) {
            Text("← Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Detalle del Usuario #$userId", fontSize = 24.sp)
    }
}
```

## Tipos de Navegación

### 1. navigate() - Navegación simple

```kotlin
// Ir a otra pantalla
navController.navigate("profile")
```

### 2. navigateUp() - Volver atrás

```kotlin
// Volver a la pantalla anterior
navController.navigateUp()
```

### 3. popBackStack() - Eliminar pantallas del historial

```kotlin
// Volver a una pantalla específica
navController.popBackStack("home", inclusive = false)

// inclusive = false: mantiene "home" en el stack
// inclusive = true: también elimina "home"
```

### 4. navigate con opciones - Control avanzado

```kotlin
navController.navigate("home") {
    // Eliminar todas las pantallas anteriores
    popUpTo("login") {
        inclusive = true
    }

    // Evitar duplicados
    launchSingleTop = true
}
```

## Navegación Condicional

### ¿Qué es?

La navegación condicional permite **controlar el acceso** a ciertas pantallas basándote en:
- Si el usuario está autenticado
- Roles o permisos
- Estado de la aplicación
- Datos específicos

### Ejemplo 1: Proteger rutas con autenticación

```kotlin
@Composable
fun AppWithAuth() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentUser by remember { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "login"
    ) {
        // Pantalla pública
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->
                    isLoggedIn = true
                    currentUser = username
                    navController.navigate("home") {
                        // Eliminar login del historial
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Pantalla protegida
        composable("home") {
            if (isLoggedIn) {
                HomeScreen(
                    username = currentUser ?: "",
                    onLogout = {
                        isLoggedIn = false
                        currentUser = null
                        navController.navigate("login") {
                            popUpTo(0) // Limpiar todo el historial
                        }
                    }
                )
            } else {
                // Redirigir si no está autenticado
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
        }

        // Otra pantalla protegida
        composable("settings") {
            if (isLoggedIn) {
                SettingsScreen()
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("login")
                }
            }
        }
    }
}
```

### Ejemplo 2: Navegación basada en roles

```kotlin
enum class UserRole {
    ADMIN, USER, GUEST
}

@Composable
fun AppWithRoles() {
    val navController = rememberNavController()
    var userRole by remember { mutableStateOf(UserRole.GUEST) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToAdmin = {
                    if (userRole == UserRole.ADMIN) {
                        navController.navigate("admin")
                    } else {
                        // Mostrar mensaje de error
                        println("Acceso denegado: Solo administradores")
                    }
                }
            )
        }

        composable("admin") {
            if (userRole == UserRole.ADMIN) {
                AdminScreen()
            } else {
                // Redirigir si no tiene permisos
                LaunchedEffect(Unit) {
                    navController.navigateUp()
                }
            }
        }
    }
}
```

### Ejemplo 3: Navegación con validación de datos

```kotlin
@Composable
fun FormNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "step1") {
        // Paso 1: Datos básicos
        composable("step1") {
            Step1Screen(
                onNext = { name, email ->
                    if (name.isNotBlank() && email.contains("@")) {
                        // Pasar datos al siguiente paso
                        navController.navigate("step2/$name/$email")
                    } else {
                        // Mostrar error, no navegar
                        println("Complete los campos correctamente")
                    }
                }
            )
        }

        // Paso 2: Confirmación
        composable(
            route = "step2/{name}/{email}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""

            Step2Screen(
                name = name,
                email = email,
                onConfirm = {
                    navController.navigate("success") {
                        // Limpiar el flujo del formulario
                        popUpTo("step1") { inclusive = true }
                    }
                }
            )
        }

        composable("success") {
            SuccessScreen()
        }
    }
}
```

## Gestión del Back Stack

El **Back Stack** es la pila de pantallas visitadas. Controlar el back stack es crucial para una buena experiencia de usuario.

### Ejemplo: Flujo de Login correcto

```kotlin
// ❌ MAL: El usuario puede volver al login después de autenticarse
navController.navigate("home")

// ✅ BIEN: Limpiar el historial después de login
navController.navigate("home") {
    popUpTo("login") { inclusive = true }
}
```

### Ejemplo: Navegación entre tabs

```kotlin
@Composable
fun MainScreenWithTabs() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    selected = false,
                    onClick = {
                        navController.navigate("home") {
                            // Evitar múltiples copias de la misma pantalla
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    selected = false,
                    onClick = {
                        navController.navigate("search") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen() }
            composable("search") { SearchScreen() }
        }
    }
}
```

## Mejores Prácticas

### 1. Centraliza las rutas

```kotlin
object NavigationRoutes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val USER_DETAIL = "user_detail/{userId}"

    fun userDetail(userId: Int) = "user_detail/$userId"
}

// Uso
navController.navigate(NavigationRoutes.HOME)
navController.navigate(NavigationRoutes.userDetail(123))
```

### 2. Usa Sealed Classes para navegación segura

```kotlin
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    data class UserDetail(val userId: Int) : Screen("user_detail/$userId")
}

// Navegación
when (val screen = Screen.UserDetail(userId = 5)) {
    is Screen.Login -> navController.navigate(screen.route)
    is Screen.Home -> navController.navigate(screen.route)
    is Screen.UserDetail -> navController.navigate(screen.route)
}
```

### 3. Extrae la lógica de navegación

```kotlin
class Navigator(private val navController: NavController) {
    fun navigateToHome(clearBackStack: Boolean = false) {
        navController.navigate("home") {
            if (clearBackStack) {
                popUpTo(0)
            }
        }
    }

    fun navigateToLogin() {
        navController.navigate("login") {
            popUpTo(0)
        }
    }

    fun navigateBack() {
        navController.navigateUp()
    }
}
```

## Resumen

1. **NavController**: Controla la navegación
2. **NavHost**: Contenedor de pantallas
3. **Routes (Strings)**: Identificadores únicos de pantallas
4. **navigate()**: Ir a una pantalla
5. **navigateUp()**: Volver atrás
6. **Navegación condicional**: Controlar acceso basado en datos/estado
7. **Back Stack**: Gestionar el historial de navegación

### Diagrama mental

```
Usuario presiona botón
        ↓
navController.navigate("destino")
        ↓
NavHost busca la ruta "destino"
        ↓
Muestra el composable asociado
        ↓
Actualiza el Back Stack
```

## Recursos

- **Documentación oficial:** [developer.android.com/jetpack/compose/navigation](https://developer.android.com/jetpack/compose/navigation)
- **Código de ejemplo:** Ver `AppNavigation.kt` en el proyecto
