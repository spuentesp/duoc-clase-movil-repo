# Fundamentos de Kotlin para Android

## Introducción

Kotlin es el lenguaje oficial de Android. Es moderno, seguro y más fácil de leer que Java. Este documento cubre los conceptos esenciales que necesitas para desarrollar apps Android.

## Variables: val vs var

### val - Inmutable (No se puede cambiar)

```kotlin
val nombre = "Juan"
// nombre = "Pedro" // ❌ ERROR: No se puede reasignar

val edad = 25
// edad = 26 // ❌ ERROR
```

**Usa `val` cuando:**
- El valor no cambiará
- Quieres más seguridad en tu código
- Es la opción recomendada por defecto

### var - Mutable (Sí se puede cambiar)

```kotlin
var contador = 0
contador = 1 // ✅ OK
contador = 2 // ✅ OK

var nombre = "Ana"
nombre = "María" // ✅ OK
```

**Usa `var` cuando:**
- El valor necesita cambiar con el tiempo
- Estás acumulando datos
- Manejas estados que se actualizan

### Ejemplos prácticos

```kotlin
// En una app de contador
var clickCount = 0 // var porque cambia

fun onButtonClick() {
    clickCount++ // Incrementa el contador
}

// Configuración de la app
val appName = "Mi App" // val porque nunca cambia
val maxUsers = 100 // val porque es un límite fijo
```

### Regla de oro

**Siempre usa `val` por defecto. Solo usa `var` cuando realmente necesites cambiar el valor.**

```kotlin
// ✅ BIEN
val pi = 3.14159
val userName = getCurrentUser()

// ⚠️ Solo si necesitas cambiar
var searchQuery = ""
var isLoading = false
```

## Tipos de Datos

Kotlin infiere (adivina) el tipo, pero puedes especificarlo:

```kotlin
// Tipo inferido
val nombre = "Juan" // String
val edad = 25 // Int
val precio = 19.99 // Double
val activo = true // Boolean

// Tipo explícito
val nombre: String = "Juan"
val edad: Int = 25
val precio: Double = 19.99
val activo: Boolean = true
```

### Tipos comunes

```kotlin
// Números
val entero: Int = 42
val largo: Long = 1000000L
val decimal: Double = 3.14
val flotante: Float = 2.5f

// Texto
val letra: Char = 'A'
val texto: String = "Hola Mundo"

// Booleano
val verdadero: Boolean = true
val falso: Boolean = false

// Listas
val numeros: List<Int> = listOf(1, 2, 3)
val nombres: MutableList<String> = mutableListOf("Ana", "Luis")
```

## Null Safety - Seguridad contra nulos

### El problema de null

En muchos lenguajes, esto causa crashes:

```java
// Java - puede crashear
String nombre = null;
int longitud = nombre.length(); // 💥 NullPointerException
```

### Solución de Kotlin: Tipos Nullable

```kotlin
// Por defecto, las variables NO pueden ser null
var nombre: String = "Juan"
// nombre = null // ❌ ERROR: No se permite null

// Para permitir null, usa "?"
var nombreOpcional: String? = "Juan"
nombreOpcional = null // ✅ OK
```

### Operadores para manejar nulls

#### 1. Safe Call Operator (?.)

```kotlin
val nombre: String? = null

// ❌ Forma insegura (causa error de compilación)
// val longitud = nombre.length()

// ✅ Safe call: retorna null si nombre es null
val longitud = nombre?.length // longitud = null

// Ejemplo práctico
val usuario: User? = getUserById(123)
val email = usuario?.email // null si usuario es null
val dominio = usuario?.email?.substringAfter("@") // Encadenado
```

#### 2. Elvis Operator (?:) - Valor por defecto

```kotlin
val nombre: String? = null

// Si nombre es null, usa "Desconocido"
val nombreFinal = nombre ?: "Desconocido"
println(nombreFinal) // "Desconocido"

// Ejemplos prácticos
val edad: Int? = null
val edadFinal = edad ?: 0 // 0 si es null

val usuario: User? = null
val nombreUsuario = usuario?.name ?: "Invitado"
```

#### 3. Not-null Assertion (!!) - Úsalo con cuidado

```kotlin
val nombre: String? = "Juan"

// Afirmas que NO es null (puede crashear si es null)
val longitudSegura = nombre!!.length // ✅ OK porque no es null

val nombreNull: String? = null
// val longitud = nombreNull!!.length // 💥 CRASH
```

**⚠️ Evita usar `!!` a menos que estés 100% seguro.**

### Ejemplos prácticos de null safety

```kotlin
// Función que puede retornar null
fun buscarUsuario(id: Int): User? {
    return if (id > 0) {
        User("Juan", "juan@email.com")
    } else {
        null
    }
}

// Uso seguro
val usuario = buscarUsuario(5)

// Opción 1: Safe call
val email = usuario?.email
println(email) // null o el email

// Opción 2: Elvis operator
val emailSeguro = usuario?.email ?: "sin@email.com"
println(emailSeguro) // Siempre tiene un valor

// Opción 3: let (ejecuta bloque solo si no es null)
usuario?.let {
    println("Usuario encontrado: ${it.name}")
    println("Email: ${it.email}")
}

// Opción 4: if tradicional
if (usuario != null) {
    // Aquí Kotlin sabe que usuario NO es null
    println(usuario.email) // No necesitas ?
}
```

### Patrón común en Android

```kotlin
@Composable
fun ProfileScreen(userId: Int?) {
    // Manejar userId opcional
    val id = userId ?: run {
        Text("Error: ID no proporcionado")
        return
    }

    // Aquí id ya no es null
    LoadUserData(id)
}
```

## Data Classes

Las **data classes** son clases diseñadas solo para contener datos.

### Sin data class (mucho código)

```kotlin
class User(val name: String, val email: String) {
    // Necesitas escribir esto manualmente
    override fun equals(other: Any?): Boolean { ... }
    override fun hashCode(): Int { ... }
    override fun toString(): String { ... }
    fun copy(...): User { ... }
}
```

### Con data class (automático)

```kotlin
data class User(
    val name: String,
    val email: String
)

// Kotlin genera automáticamente:
// - equals()
// - hashCode()
// - toString()
// - copy()
// - componentN() para destructuring
```

### Funciones automáticas

```kotlin
data class User(val name: String, val age: Int)

val user1 = User("Juan", 25)
val user2 = User("Juan", 25)
val user3 = User("Ana", 30)

// toString() automático
println(user1) // User(name=Juan, age=25)

// equals() automático
println(user1 == user2) // true (mismo contenido)
println(user1 == user3) // false

// copy() automático - crear copia modificando algunos valores
val user4 = user1.copy(age = 26)
println(user4) // User(name=Juan, age=26)

val user5 = user1.copy(name = "Pedro")
println(user5) // User(name=Pedro, age=25)
```

### Destructuring

```kotlin
data class User(val name: String, val email: String, val age: Int)

val user = User("Juan", "juan@email.com", 25)

// Extraer valores
val (nombre, email, edad) = user
println(nombre) // "Juan"
println(email) // "juan@email.com"
println(edad) // 25

// Ignorar valores que no necesitas
val (nombre, _, edad) = user // Ignora email
```

### Uso práctico en Android

```kotlin
// Modelo de datos
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val inStock: Boolean
)

// En tu ViewModel
val product = Product(
    id = "P001",
    name = "Laptop",
    price = 999.99,
    inStock = true
)

// Modificar precio (inmutable, crea nueva instancia)
val productWithDiscount = product.copy(price = 799.99)

// En una lista
val products = listOf(
    Product("P001", "Laptop", 999.99, true),
    Product("P002", "Mouse", 29.99, false),
    Product("P003", "Teclado", 79.99, true)
)
```

### Valores por defecto

```kotlin
data class User(
    val id: String,
    val name: String,
    val email: String = "", // Valor por defecto
    val age: Int = 0, // Valor por defecto
    val isActive: Boolean = true // Valor por defecto
)

// Puedes crear usuarios de varias formas
val user1 = User("1", "Juan") // usa defaults para email, age, isActive
val user2 = User("2", "Ana", "ana@email.com") // usa defaults para age e isActive
val user3 = User("3", "Pedro", age = 30) // usa default para email, especifica age
```

## Funciones

### Función básica

```kotlin
fun saludar(nombre: String): String {
    return "Hola, $nombre"
}

val mensaje = saludar("Juan") // "Hola, Juan"
```

### Función de una sola expresión

```kotlin
fun sumar(a: Int, b: Int): Int = a + b

fun esMayorDeEdad(edad: Int): Boolean = edad >= 18

// Uso
val resultado = sumar(5, 3) // 8
val puede = esMayorDeEdad(20) // true
```

### Parámetros con valores por defecto

```kotlin
fun crearUsuario(
    nombre: String,
    edad: Int = 18,
    activo: Boolean = true
): User {
    return User(nombre, edad, activo)
}

// Diferentes formas de llamar
val user1 = crearUsuario("Juan") // usa defaults
val user2 = crearUsuario("Ana", 25) // especifica edad
val user3 = crearUsuario("Pedro", activo = false) // usa named parameters
```

### Named parameters (parámetros nombrados)

```kotlin
fun configurarPerfil(
    nombre: String,
    bio: String,
    publico: Boolean,
    notificaciones: Boolean
) { ... }

// Sin nombres (confuso)
configurarPerfil("Juan", "Desarrollador", true, false)

// Con nombres (claro)
configurarPerfil(
    nombre = "Juan",
    bio = "Desarrollador",
    publico = true,
    notificaciones = false
)
```

## Lambdas y Funciones de Orden Superior

### ¿Qué es una Lambda?

Una **lambda** es una función anónima (sin nombre) que puedes pasar como parámetro.

```kotlin
// Función normal
fun esPar(numero: Int): Boolean {
    return numero % 2 == 0
}

// Lambda equivalente
val esPar = { numero: Int -> numero % 2 == 0 }

// Uso
println(esPar(4)) // true
println(esPar(5)) // false
```

### Sintaxis de lambdas

```kotlin
// Forma completa
val sumar = { a: Int, b: Int -> a + b }

// Si hay un solo parámetro, puedes usar "it"
val doble = { numero: Int -> numero * 2 }
val doble2: (Int) -> Int = { it * 2 } // Mismo, usando "it"

// Sin parámetros
val saludar = { println("Hola") }

// Uso
println(sumar(3, 5)) // 8
println(doble(4)) // 8
saludar() // Imprime "Hola"
```

### Funciones de orden superior

Son funciones que **reciben** otras funciones como parámetros.

```kotlin
// Función que recibe una lambda
fun ejecutarOperacion(a: Int, b: Int, operacion: (Int, Int) -> Int): Int {
    return operacion(a, b)
}

// Uso con diferentes operaciones
val suma = ejecutarOperacion(5, 3) { a, b -> a + b } // 8
val resta = ejecutarOperacion(5, 3) { a, b -> a - b } // 2
val multiplicacion = ejecutarOperacion(5, 3) { a, b -> a * b } // 15
```

### Ejemplo práctico: onClick en Compose

```kotlin
@Composable
fun BotonPersonalizado(
    texto: String,
    onClick: () -> Unit // Lambda sin parámetros que no retorna nada
) {
    Button(onClick = onClick) {
        Text(texto)
    }
}

// Uso
BotonPersonalizado(texto = "Guardar") {
    println("Guardando datos...")
    guardarEnBaseDeDatos()
}
```

### Trailing lambda

Cuando la lambda es el último parámetro, puede ir fuera de los paréntesis:

```kotlin
// Forma tradicional
Button(onClick = { println("Click") }) {
    Text("Presionar")
}

// Forma con trailing lambda (más común en Compose)
Button(
    onClick = {
        println("Click")
    }
) {
    Text("Presionar")
}
```

## Collections (Listas, Maps, Sets)

### List - Listas

```kotlin
// Lista inmutable (no se puede modificar)
val numeros = listOf(1, 2, 3, 4, 5)
// numeros.add(6) // ❌ ERROR

// Lista mutable
val numerosMutables = mutableListOf(1, 2, 3)
numerosMutables.add(4) // ✅ OK
numerosMutables.remove(2) // Elimina el 2

// Acceso a elementos
val primero = numeros[0] // 1
val ultimo = numeros.last() // 5
val tamano = numeros.size // 5
```

### Operaciones comunes en listas

#### filter - Filtrar elementos

```kotlin
val numeros = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// Filtrar números pares
val pares = numeros.filter { it % 2 == 0 }
println(pares) // [2, 4, 6, 8, 10]

// Ejemplo práctico
data class User(val name: String, val age: Int)

val usuarios = listOf(
    User("Juan", 17),
    User("Ana", 25),
    User("Pedro", 30),
    User("María", 16)
)

val mayoresDeEdad = usuarios.filter { it.age >= 18 }
println(mayoresDeEdad) // [Ana, Pedro]
```

#### map - Transformar elementos

```kotlin
val numeros = listOf(1, 2, 3, 4, 5)

// Duplicar cada número
val duplicados = numeros.map { it * 2 }
println(duplicados) // [2, 4, 6, 8, 10]

// Ejemplo práctico
val usuarios = listOf(
    User("Juan", 25),
    User("Ana", 30)
)

// Obtener solo los nombres
val nombres = usuarios.map { it.name }
println(nombres) // ["Juan", "Ana"]

// Transformar a otra estructura
val emails = usuarios.map { "${it.name.lowercase()}@email.com" }
println(emails) // ["juan@email.com", "ana@email.com"]
```

#### forEach - Iterar sobre elementos

```kotlin
val numeros = listOf(1, 2, 3, 4, 5)

numeros.forEach { numero ->
    println("Número: $numero")
}

// Ejemplo práctico
usuarios.forEach { usuario ->
    println("${usuario.name} tiene ${usuario.age} años")
}
```

#### find - Buscar elemento

```kotlin
val numeros = listOf(1, 2, 3, 4, 5)

val primerPar = numeros.find { it % 2 == 0 }
println(primerPar) // 2

// Ejemplo práctico
val usuario = usuarios.find { it.name == "Ana" }
println(usuario) // User(name=Ana, age=30)
```

#### any / all / none - Validaciones

```kotlin
val numeros = listOf(1, 2, 3, 4, 5)

val hayPares = numeros.any { it % 2 == 0 } // true
val todosPares = numeros.all { it % 2 == 0 } // false
val ningunNegativo = numeros.none { it < 0 } // true

// Ejemplo práctico
val usuarios = listOf(
    User("Juan", 17),
    User("Ana", 25),
    User("Pedro", 30)
)

val hayMenores = usuarios.any { it.age < 18 } // true
val todosMayores = usuarios.all { it.age >= 18 } // false
```

#### sortedBy - Ordenar

```kotlin
val numeros = listOf(5, 2, 8, 1, 9)
val ordenados = numeros.sorted()
println(ordenados) // [1, 2, 5, 8, 9]

// Ejemplo práctico
val usuarios = listOf(
    User("Juan", 25),
    User("Ana", 30),
    User("Pedro", 20)
)

val porEdad = usuarios.sortedBy { it.age }
val porNombre = usuarios.sortedBy { it.name }
```

#### Encadenar operaciones

```kotlin
val usuarios = listOf(
    User("Juan", 17),
    User("Ana", 25),
    User("Pedro", 30),
    User("María", 16),
    User("Luis", 28)
)

// Filtrar mayores de edad, ordenar por edad, obtener nombres
val resultado = usuarios
    .filter { it.age >= 18 }
    .sortedBy { it.age }
    .map { it.name }

println(resultado) // ["Ana", "Luis", "Pedro"]
```

### Map - Diccionarios

```kotlin
// Inmutable
val edades = mapOf(
    "Juan" to 25,
    "Ana" to 30,
    "Pedro" to 22
)

// Acceso
val edadJuan = edades["Juan"] // 25 (puede ser null)
val edadCarlos = edades["Carlos"] // null

// Mutable
val edadesMutables = mutableMapOf("Juan" to 25)
edadesMutables["Ana"] = 30 // Agregar
edadesMutables.remove("Juan") // Eliminar

// Iterar
edades.forEach { (nombre, edad) ->
    println("$nombre tiene $edad años")
}
```

### Set - Conjuntos (sin duplicados)

```kotlin
val numeros = setOf(1, 2, 3, 2, 1) // Elimina duplicados
println(numeros) // [1, 2, 3]

val numerosMutables = mutableSetOf<Int>()
numerosMutables.add(1)
numerosMutables.add(2)
numerosMutables.add(1) // No se agrega (ya existe)
println(numerosMutables) // [1, 2]
```

## Scope Functions (let, apply, also, run)

Son funciones que ejecutan un bloque de código en el contexto de un objeto.

### let - Transformar y usar resultado

```kotlin
val nombre: String? = "Juan"

// Sin let
if (nombre != null) {
    println(nombre.uppercase())
    println(nombre.length)
}

// Con let
nombre?.let {
    println(it.uppercase())
    println(it.length)
}

// Ejemplo práctico: transformar valor
val longitudNombre = nombre?.let {
    println("Procesando: $it")
    it.length // Retorna la longitud
} ?: 0

println(longitudNombre) // 4
```

### apply - Configurar objeto

```kotlin
data class User(
    var name: String = "",
    var email: String = "",
    var age: Int = 0
)

// Sin apply
val user = User()
user.name = "Juan"
user.email = "juan@email.com"
user.age = 25

// Con apply (más limpio)
val user2 = User().apply {
    name = "Ana"
    email = "ana@email.com"
    age = 30
}

// Ejemplo práctico en Android
val button = Button(context).apply {
    text = "Presionar"
    isEnabled = true
    setOnClickListener { println("Click") }
}
```

### also - Realizar acciones adicionales

```kotlin
val numeros = mutableListOf(1, 2, 3)
    .also { println("Lista original: $it") }
    .also { it.add(4) }
    .also { println("Después de agregar: $it") }

// Ejemplo práctico: logging
fun crearUsuario(nombre: String): User {
    return User().apply {
        name = nombre
        email = "${nombre.lowercase()}@email.com"
    }.also {
        println("Usuario creado: $it")
        guardarEnBaseDeDatos(it)
    }
}
```

### run - Ejecutar bloque y retornar resultado

```kotlin
val resultado = run {
    val a = 5
    val b = 10
    a + b // Retorna 15
}

// Ejemplo práctico
val mensaje = run {
    val usuario = obtenerUsuario()
    if (usuario != null) {
        "Bienvenido ${usuario.name}"
    } else {
        "Usuario no encontrado"
    }
}
```

### Comparación rápida

```kotlin
val user = User("Juan", "juan@email.com", 25)

// let: usa "it", retorna resultado del bloque
val nombreMayusculas = user.let { it.name.uppercase() }

// apply: usa "this" (implícito), retorna el objeto
val usuario = User().apply {
    name = "Ana" // "this" es implícito
}

// also: usa "it", retorna el objeto
val user2 = user.also {
    println("Usuario: ${it.name}")
}

// run: usa "this" (implícito), retorna resultado del bloque
val esAdulto = user.run {
    age >= 18
}
```

## String Templates

```kotlin
val nombre = "Juan"
val edad = 25

// Interpolación simple
println("Hola, $nombre") // "Hola, Juan"

// Expresiones
println("$nombre tiene $edad años") // "Juan tiene 25 años"

// Expresiones complejas (usa llaves)
println("${nombre.uppercase()} tiene ${edad + 5} años")
// "JUAN tiene 30 años"

// Ejemplo práctico
data class Product(val name: String, val price: Double)
val product = Product("Laptop", 999.99)

println("Producto: ${product.name}, Precio: $${product.price}")
// "Producto: Laptop, Precio: $999.99"
```

## When - Switch mejorado

```kotlin
fun describir(valor: Any): String {
    return when (valor) {
        1 -> "Es uno"
        "Hola" -> "Es un saludo"
        is String -> "Es un String"
        is Int -> "Es un número entero"
        in 1..10 -> "Está entre 1 y 10"
        else -> "No sé qué es"
    }
}

// Ejemplo práctico
sealed class Estado {
    object Cargando : Estado()
    data class Exito(val datos: List<User>) : Estado()
    data class Error(val mensaje: String) : Estado()
}

fun manejarEstado(estado: Estado) {
    when (estado) {
        is Estado.Cargando -> {
            mostrarSpinner()
        }
        is Estado.Exito -> {
            mostrarDatos(estado.datos)
        }
        is Estado.Error -> {
            mostrarError(estado.mensaje)
        }
    }
}
```

## Extension Functions

Permiten agregar funciones a clases existentes sin modificarlas.

```kotlin
// Agregar función a String
fun String.esPalindromo(): Boolean {
    val limpio = this.lowercase().replace(" ", "")
    return limpio == limpio.reversed()
}

// Uso
println("anita lava la tina".esPalindromo()) // true
println("hola".esPalindromo()) // false

// Ejemplo práctico
fun List<User>.mayoresDeEdad(): List<User> {
    return this.filter { it.age >= 18 }
}

val usuarios = listOf(
    User("Juan", 17),
    User("Ana", 25),
    User("Pedro", 30)
)

val adultos = usuarios.mayoresDeEdad()
```

## Resumen: Conceptos clave

| Concepto | Cuándo usar | Ejemplo |
|----------|-------------|---------|
| `val` | Valor inmutable (preferido) | `val nombre = "Juan"` |
| `var` | Valor que cambia | `var contador = 0` |
| `?` | Permitir null | `var nombre: String? = null` |
| `?.` | Safe call | `usuario?.email` |
| `?:` | Elvis (default si null) | `edad ?: 0` |
| `!!` | Assert not-null (evitar) | `nombre!!.length` |
| `data class` | Modelos de datos | `data class User(...)` |
| `let` | Transformar nullable | `nombre?.let { ... }` |
| `apply` | Configurar objeto | `User().apply { ... }` |
| `filter` | Filtrar lista | `lista.filter { it > 5 }` |
| `map` | Transformar lista | `lista.map { it * 2 }` |

## Ejercicios Prácticos

### Ejercicio 1: Manejo de nulos

```kotlin
// Completa esta función
fun obtenerEmailSeguro(usuario: User?): String {
    // Retorna el email del usuario, o "sin@email.com" si es null
    return usuario?.email ?: "sin@email.com"
}
```

### Ejercicio 2: Filtrar y transformar

```kotlin
data class Producto(val nombre: String, val precio: Double, val enStock: Boolean)

val productos = listOf(
    Producto("Laptop", 999.99, true),
    Producto("Mouse", 29.99, false),
    Producto("Teclado", 79.99, true),
    Producto("Monitor", 299.99, true)
)

// Obtén los nombres de los productos en stock que cuestan menos de $500
val resultado = productos
    .filter { it.enStock && it.precio < 500 }
    .map { it.nombre }

println(resultado) // [Teclado, Monitor]
```

### Ejercicio 3: Data class y copy

```kotlin
data class Configuracion(
    val tema: String = "claro",
    val notificaciones: Boolean = true,
    val idioma: String = "es"
)

val config1 = Configuracion()
val config2 = config1.copy(tema = "oscuro")
val config3 = config1.copy(notificaciones = false, idioma = "en")
```

## Recursos

- **Documentación oficial:** [kotlinlang.org](https://kotlinlang.org)
- **Kotlin Koans:** Ejercicios interactivos
- **Android Basics with Compose:** [developer.android.com/courses](https://developer.android.com/courses/android-basics-compose/course)
