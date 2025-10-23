# Arquitectura MVVM y Repository Pattern

## ¿Qué es una Arquitectura?

Una **arquitectura** es la forma en que organizamos el código de nuestra aplicación. Es como el plano de una casa: define dónde va cada cosa y cómo se comunican las diferentes partes.

### ¿Por qué necesitamos una arquitectura?

Sin arquitectura:
- ❌ Todo el código está mezclado
- ❌ Es difícil encontrar bugs
- ❌ Es difícil agregar nuevas funcionalidades
- ❌ No se pueden hacer tests fácilmente

Con arquitectura MVVM:
- ✅ Código organizado y separado
- ✅ Fácil de mantener
- ✅ Fácil de testear
- ✅ Múltiples personas pueden trabajar en paralelo

## ¿Qué es MVVM?

**MVVM** significa: **Model - View - ViewModel**

Es un patrón de arquitectura que separa tu código en tres capas:

```
┌─────────────────────────────────────────┐
│              VIEW (UI)                  │
│   (Lo que el usuario ve)                │
│   - Composables                         │
│   - Screens                             │
└──────────────┬──────────────────────────┘
               │ Observa
               │ Interactúa
               ↓
┌─────────────────────────────────────────┐
│           VIEWMODEL                     │
│   (Lógica de presentación)              │
│   - Maneja el estado                    │
│   - Procesa acciones del usuario        │
└──────────────┬──────────────────────────┘
               │ Obtiene datos
               │ Llama funciones
               ↓
┌─────────────────────────────────────────┐
│          MODEL / REPOSITORY             │
│   (Datos y lógica de negocio)           │
│   - Acceso a base de datos              │
│   - Llamadas a API                      │
│   - Lógica de datos                     │
└─────────────────────────────────────────┘
```

### Analogía del Restaurante

Imagina un restaurante:

- **VIEW (Mesero)**: Interactúa con el cliente, toma el pedido, muestra el menú
- **VIEWMODEL (Coordinador)**: Organiza los pedidos, decide qué preparar, comunica con la cocina
- **MODEL/REPOSITORY (Cocina)**: Prepara la comida, tiene los ingredientes, conoce las recetas

## Parte 1: Model (Modelo)

El **Model** representa los datos de tu aplicación.

### Ejemplo: Modelo de Usuario

```kotlin
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val age: Int
)
```

### Ejemplo: Modelo de Producto

```kotlin
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int,
    val imageUrl: String
)
```

**¿Qué hace?**
- Define la estructura de tus datos
- Es independiente de la UI
- Puede tener validaciones simples

```kotlin
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val age: Int
) {
    // Validación en el modelo
    fun isAdult(): Boolean = age >= 18

    fun isValidEmail(): Boolean = email.contains("@")
}
```

## Parte 2: Repository (Repositorio)

El **Repository** es la fuente única de datos. Se encarga de:
- Obtener datos (de API, base de datos, etc.)
- Guardar datos
- Actualizar datos
- Eliminar datos

### ¿Por qué usar Repository?

**Sin Repository:**
```kotlin
// ❌ ViewModel accede directamente a datos
class UserViewModel {
    fun loadUsers() {
        // Llamada directa a API
        // Llamada directa a base de datos
        // Mezcla de lógica
    }
}
```

**Con Repository:**
```kotlin
// ✅ ViewModel solo pide datos al Repository
class UserViewModel(private val repository: UserRepository) {
    fun loadUsers() {
        repository.getUsers()
    }
}
```

### Ejemplo Simple: Repository en Memoria

```kotlin
// Interfaz del Repository
interface UserRepository {
    fun getUsers(): List<User>
    fun getUserById(id: Int): User?
    fun addUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(id: Int)
}

// Implementación con datos en memoria
class UserRepositoryImpl : UserRepository {
    // Lista privada de usuarios
    private val users = mutableListOf(
        User(1, "Juan Pérez", "juan@email.com", 25),
        User(2, "María García", "maria@email.com", 30),
        User(3, "Pedro López", "pedro@email.com", 22)
    )

    override fun getUsers(): List<User> {
        return users.toList() // Retorna copia
    }

    override fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    override fun addUser(user: User) {
        users.add(user)
    }

    override fun updateUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
    }

    override fun deleteUser(id: Int) {
        users.removeAll { it.id == id }
    }
}
```

### Ejemplo Avanzado: Repository con múltiples fuentes

```kotlin
// Repository que combina API y base de datos local
class ProductRepository(
    private val apiService: ProductApiService,
    private val localDatabase: ProductDatabase
) {
    // Obtener productos: primero de la red, guardar en local
    suspend fun getProducts(): List<Product> {
        return try {
            // Intentar obtener de la red
            val products = apiService.fetchProducts()

            // Guardar en base de datos local
            localDatabase.saveProducts(products)

            products
        } catch (e: Exception) {
            // Si falla la red, usar datos locales
            localDatabase.getProducts()
        }
    }

    // Buscar producto por ID
    suspend fun getProductById(id: String): Product? {
        // Primero buscar en local (más rápido)
        val localProduct = localDatabase.getProductById(id)
        if (localProduct != null) {
            return localProduct
        }

        // Si no está en local, buscar en red
        return try {
            apiService.fetchProductById(id)
        } catch (e: Exception) {
            null
        }
    }
}
```

## Parte 3: ViewModel

El **ViewModel** es el puente entre la UI (View) y los datos (Repository).

### Responsabilidades del ViewModel

1. **Mantener el estado de la UI**
2. **Procesar acciones del usuario**
3. **Obtener datos del Repository**
4. **Preparar datos para mostrar**
5. **Sobrevivir a cambios de configuración** (rotación de pantalla)

### Ejemplo Básico: ViewModel simple

```kotlin
// Estado de la UI
data class UsersUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class UsersViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // Estado observable por la UI
    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    // Cargar usuarios al iniciar
    init {
        loadUsers()
    }

    // Función para cargar usuarios
    fun loadUsers() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        try {
            val users = repository.getUsers()
            _uiState.value = UsersUiState(
                users = users,
                isLoading = false
            )
        } catch (e: Exception) {
            _uiState.value = UsersUiState(
                isLoading = false,
                error = "Error al cargar usuarios: ${e.message}"
            )
        }
    }

    // Agregar nuevo usuario
    fun addUser(name: String, email: String, age: Int) {
        val newUser = User(
            id = (repository.getUsers().maxOfOrNull { it.id } ?: 0) + 1,
            name = name,
            email = email,
            age = age
        )

        repository.addUser(newUser)
        loadUsers() // Recargar lista
    }

    // Eliminar usuario
    fun deleteUser(userId: Int) {
        repository.deleteUser(userId)
        loadUsers() // Recargar lista
    }
}
```

### Ejemplo con Coroutines (operaciones asíncronas)

```kotlin
class ProductsViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        // Lanzar coroutine en el scope del ViewModel
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Operación suspendida (puede tardar)
                val products = repository.getProducts()

                _uiState.value = ProductsUiState(
                    products = products,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = ProductsUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val products = repository.searchProducts(query)
                _uiState.value = _uiState.value.copy(
                    products = products,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}

data class ProductsUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

## Parte 4: View (Composables)

La **View** es lo que el usuario ve e interactúa. En Compose, son los composables.

### Conectar View con ViewModel

```kotlin
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = viewModel()
) {
    // Observar el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // UI basada en el estado
    Column(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                // Mostrar loading
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                // Mostrar error
                Text(
                    text = uiState.error ?: "Error desconocido",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                // Mostrar lista de usuarios
                LazyColumn {
                    items(uiState.users) { user ->
                        UserItem(
                            user = user,
                            onDeleteClick = {
                                viewModel.deleteUser(user.id)
                            }
                        )
                    }
                }
            }
        }

        // Botón para agregar usuario
        FloatingActionButton(
            onClick = {
                viewModel.addUser("Nuevo Usuario", "nuevo@email.com", 25)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar")
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(user.name, fontWeight = FontWeight.Bold)
                Text(user.email, fontSize = 12.sp, color = Color.Gray)
            }

            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
```

## Ejemplo Completo: App de Login

### 1. Model

```kotlin
data class User(
    val id: String,
    val username: String,
    val email: String
)

data class LoginCredentials(
    val username: String,
    val password: String
)
```

### 2. Repository

```kotlin
interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
}

class AuthRepositoryImpl : AuthRepository {
    // Simulación de base de datos
    private val users = mapOf(
        "admin" to Pair("admin123", User("1", "admin", "admin@app.com")),
        "user" to Pair("user123", User("2", "user", "user@app.com"))
    )

    private var currentUser: User? = null

    override suspend fun login(credentials: LoginCredentials): Result<User> {
        // Simular delay de red
        delay(1000)

        val userInfo = users[credentials.username]

        return if (userInfo != null && userInfo.first == credentials.password) {
            currentUser = userInfo.second
            Result.success(userInfo.second)
        } else {
            Result.failure(Exception("Usuario o contraseña incorrectos"))
        }
    }

    override suspend fun logout() {
        currentUser = null
    }

    override suspend fun getCurrentUser(): User? {
        return currentUser
    }
}
```

### 3. ViewModel

```kotlin
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        // Validaciones básicas
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Complete todos los campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = repository.login(
                LoginCredentials(username, password)
            )

            _uiState.value = if (result.isSuccess) {
                LoginUiState.Success(result.getOrNull()!!)
            } else {
                LoginUiState.Error(
                    result.exceptionOrNull()?.message ?: "Error desconocido"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}
```

### 4. View (Screen)

```kotlin
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (User) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    // Navegar cuando login sea exitoso
    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            val user = (uiState as LoginUiState.Success).user
            onLoginSuccess(user)
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            enabled = uiState !is LoginUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            enabled = uiState !is LoginUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar error si existe
        if (uiState is LoginUiState.Error) {
            Text(
                text = (uiState as LoginUiState.Error).message,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.login(username, password)
            },
            enabled = uiState !is LoginUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Iniciar Sesión")
            }
        }

        // Ayuda para testing
        Text(
            text = "Usuario: admin, Contraseña: admin123",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
```

## Inyección de Dependencias

Para pasar el Repository al ViewModel, puedes usar:

### Opción 1: Manual (Simple)

```kotlin
@Composable
fun UsersScreen() {
    val repository = remember { UserRepositoryImpl() }
    val viewModel = viewModel<UsersViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(repository) as T
            }
        }
    )

    // Tu UI aquí
}
```

### Opción 2: Con Hilt (Avanzado)

```kotlin
// En el Repository
@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    // Implementación
}

// En el ViewModel
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    // Implementación
}

// En el Composable
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel()
) {
    // Tu UI
}
```

## Flujo de Datos Completo

```
1. Usuario presiona un botón en la UI
        ↓
2. View llama a una función del ViewModel
        ↓
3. ViewModel llama al Repository
        ↓
4. Repository obtiene/guarda datos
        ↓
5. Repository retorna resultado al ViewModel
        ↓
6. ViewModel actualiza el estado (UiState)
        ↓
7. View observa el cambio de estado
        ↓
8. View se recompone con los nuevos datos
```

### Ejemplo práctico del flujo

```kotlin
// 1. Usuario presiona "Cargar usuarios"
Button(onClick = { viewModel.loadUsers() })

// 2. ViewModel recibe la acción
fun loadUsers() {
    viewModelScope.launch {
        // 3. ViewModel llama al Repository
        val users = repository.getUsers()

        // 5. ViewModel actualiza el estado
        _uiState.value = UsersUiState(users = users)
    }
}

// 4. Repository obtiene datos
override fun getUsers(): List<User> {
    return database.getAllUsers()
}

// 6-7. View observa y se actualiza
val uiState by viewModel.uiState.collectAsState()
LazyColumn {
    items(uiState.users) { user ->
        UserItem(user)
    }
}
```

## Mejores Prácticas

### 1. Separación de responsabilidades

```kotlin
// ❌ MAL: Lógica de UI en ViewModel
class BadViewModel : ViewModel() {
    fun getButtonColor(): Color = Color.Blue
    fun getTextSize(): TextUnit = 16.sp
}

// ✅ BIEN: Solo lógica de negocio
class GoodViewModel : ViewModel() {
    fun loadData()
    fun saveData(data: Data)
    fun validateInput(input: String): Boolean
}
```

### 2. Estados inmutables

```kotlin
// ❌ MAL: Estado mutable expuesto
class BadViewModel : ViewModel() {
    val users = mutableStateListOf<User>()
}

// ✅ BIEN: Estado inmutable hacia afuera
class GoodViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
}
```

### 3. Un ViewModel por pantalla

```kotlin
// ✅ BIEN
LoginScreen → LoginViewModel
HomeScreen → HomeViewModel
ProfileScreen → ProfileViewModel
```

## Resumen

| Capa | Responsabilidad | Ejemplo |
|------|----------------|---------|
| **Model** | Estructura de datos | `data class User(...)` |
| **Repository** | Acceso a datos | `getUserById()`, `saveUser()` |
| **ViewModel** | Lógica de presentación | Maneja estado, procesa acciones |
| **View** | UI e interacciones | Composables, Screens |

### Beneficios de MVVM

1. **Testeable**: Cada capa se puede probar independientemente
2. **Mantenible**: Cambios en UI no afectan la lógica de datos
3. **Reutilizable**: ViewModels y Repositories se pueden compartir
4. **Escalable**: Fácil agregar nuevas funcionalidades

## Recursos

- **Documentación oficial:** [developer.android.com/topic/architecture](https://developer.android.com/topic/architecture)
- **ViewModels:** [developer.android.com/topic/libraries/architecture/viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- **Repository Pattern:** [developer.android.com/codelabs/basic-android-kotlin-compose-add-repository](https://developer.android.com/codelabs/basic-android-kotlin-compose-add-repository)
