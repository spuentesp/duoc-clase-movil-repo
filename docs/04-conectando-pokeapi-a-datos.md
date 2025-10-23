# 04 · Conectando PokeAPI a nuestra app

En este módulo recorremos, paso a paso, cómo el proyecto conecta una fuente de datos **real** (PokeAPI) con la UI basada en Jetpack Compose. Todo el flujo sigue la arquitectura **Repository → ViewModel → UiState → Compose**, exactamente como lo implementamos en el código del repositorio.

---

## 1. PokeAPI en contexto

[PokeAPI](https://pokeapi.co/) es una API REST pública con información de Pokémon. Utilizamos dos endpoints:

- `GET /api/v2/pokemon?limit=20` para obtener una lista básica (nombre y URL).
- `GET /api/v2/pokemon/{name}` para traer el detalle completo de un Pokémon.

---

## 2. Modelos de datos (DTO)

Archivo: `app/src/main/java/com/example/app/model/Pokemon.kt`

> 🧭 **Guía:** define estas data classes primero. Así, cuando agregues Retrofit, las respuestas se mapearán sin esfuerzo y evitarás campos `null` inesperados.

```kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = { Text(pokemonName.replaceFirstChar { it.uppercase() }) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }
        )
    }
) { innerPadding ->
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.isLoading) {
            item { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }
        }

        state.error?.let { error ->
            item { Text("Error: $error", color = MaterialTheme.colorScheme.error) }
        }

        state.detail?.let { detail ->
            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(detail.name.replaceFirstChar { it.uppercase() }, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text("ID: #${'$'}{detail.id}")
                        detail.sprites.frontDefault?.let { url ->
                            Image(
                                painter = rememberAsyncImagePainter(url),
                                contentDescription = detail.name,
                                modifier = Modifier.fillMaxWidth().height(220.dp)
                            )
                        }
                        Text("Tipos: ${'$'}{detail.types.joinToString { it.type.name }}")
                    }
                }
            }
        }

        state.species?.let { info ->
            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Tomamos la flavor text en español (o inglés) y limpiamos 
 y 
                        // Mostramos especie, felicidad base, ratio de captura y evolución previa si existe
                    }
                }
            }
        }

        state.speciesError?.let { speciesError ->
            item {
                Text(
                    text = "No fue posible cargar datos adicionales: $speciesError",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
```
kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = { Text("Pokédex") },
            actions = {
                IconButton(onClick = onProfileClick) {
                    Icon(Icons.Default.Person, contentDescription = "Ir al perfil")
                }
            }
        )
    }
) { innerPadding ->
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item { Text("Explora Pokémon y usa el icono para volver al perfil.") }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre o número") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { performSearch() })
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = limitInput,
                    onValueChange = { limitInput = it },
                    label = { Text("Limit") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = offsetInput,
                    onValueChange = { offsetInput = it },
                    label = { Text("Offset") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Button(
                    onClick = {
                        val limit = limitInput.toIntOrNull()?.takeIf { it > 0 } ?: 20
                        val offset = offsetInput.toIntOrNull()?.coerceAtLeast(0) ?: 0
                        focusManager.clearFocus()
                        viewModel.loadList(limit, offset)
                    }
                ) {
                    Text("Actualizar")
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.End) {
                Button(onClick = { performSearch() }, enabled = searchQuery.isNotBlank()) {
                    Text("Consultar")
                }
            }
        }

        item {
            Column {
                if (detailState.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                detailState.error?.let { Text("Error al consultar: $it", color = MaterialTheme.colorScheme.error) }
                detailState.detail?.let { detail ->
                    Card {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(detail.name.replaceFirstChar { it.uppercase() }, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            detail.sprites.frontDefault?.let { url ->
                                Image(
                                    painter = rememberAsyncImagePainter(url),
                                    contentDescription = detail.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                        .height(160.dp)
                                )
                            }
                            Text("Tipos: ${'$'}{detail.types.joinToString { it.type.name }}")
                            // Flavor text, especie, felicidad y ratio de captura desde pokemon-species
                            TextButton(onClick = { onPokemonSelected(detail.name) }) {
                                Text("Ver detalle completo")
                            }
                        }
                    }
                }
            }
        }

        item { Text("Pokémon disponibles", style = MaterialTheme.typography.titleMedium) }

        if (listState.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        listState.error?.let { error ->
            item { Text("Error al cargar la lista: $error", color = MaterialTheme.colorScheme.error) }
        }

        items(listState.pokemons) { pokemon ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        searchQuery = pokemon.name
                        viewModel.loadDetail(pokemon.name) // refresca el panel superior
                    }
            ) {
                val id = pokemon.url.trimEnd('/').substringAfterLast('/')
                Text("#${'$'}id - ${'$'}{pokemon.name.replaceFirstChar { it.uppercase() }}", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
```
kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = { Text("Pokédex") },
            actions = {
                IconButton(onClick = onProfileClick) {
                    Icon(Icons.Default.Person, contentDescription = "Ir al perfil")
                }
            }
        )
    }
) { innerPadding ->
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item { Text("Explora Pokémon y usa el icono para volver al perfil.") }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre o número") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { performSearch() })
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(/* limit */)
                OutlinedTextField(/* offset */)
                Button(onClick = { /* actualiza limit/offset */ }) { Text("Actualizar") }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.End) {
                Button(onClick = { performSearch() }, enabled = searchQuery.isNotBlank()) {
                    Text("Consultar")
                }
            }
        }

        item {
            Column {
                if (detailState.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                detailState.error?.let { Text("Error al consultar: $it", color = MaterialTheme.colorScheme.error) }
                detailState.detail?.let { detail ->
                    Card {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(detail.name.replaceFirstChar { it.uppercase() }, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            // Imagen frontal + tipos desde /pokemon/{id}
                            // Flavor text, especie, felicidad base, ratio de captura desde /pokemon-species/{id}
                            TextButton(onClick = { onPokemonSelected(detail.name) }) {
                                Text("Ver detalle completo")
                            }
                        }
                    }
                }
            }
        }

        item { Text("Pokémon disponibles", style = MaterialTheme.typography.titleMedium) }

        if (listState.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        listState.error?.let { error ->
            item { Text("Error al cargar la lista: $error", color = MaterialTheme.colorScheme.error) }
        }

        items(listState.pokemons) { pokemon ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        searchQuery = pokemon.name
                        viewModel.loadDetail(pokemon.name) // refresca el panel superior
                    }
            ) {
                Text(
                    text = "#${'$'}{pokemon.url.trimEnd('/').substringAfterLast('/')} - ${'$'}{pokemon.name.replaceFirstChar { it.uppercase() }}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
```
kotlin
data class BasicPokemon(
    val name: String,
    val url: String // URL con la ruta al detalle; luego extraemos el ID de aquí
)

data class PokemonListResponse(
    val results: List<BasicPokemon> // PokeAPI siempre anida la lista bajo "results"
)

data class PokemonTypeEntry(
    val slot: Int,
    val type: TypeInfo // type incluye el nombre del tipo (fire, water, etc.)
)

data class TypeInfo(
    val name: String
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeEntry>,
    val sprites: Sprites // PokeAPI entrega varias imágenes; usamos solo la frontal
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String? // viene null en algunos Pokémon (forme alterna, etc.)
)
```

> Estas clases reflejan la estructura exacta del JSON que entrega PokeAPI. No contienen lógica, solamente datos.

Archivo: `app/src/main/java/com/example/app/model/PokemonSpecies.kt`

> 🧩 **Guía:** agrega este archivo una vez que la lista funcione. Empieza mostrando solo `flavor_text_entries` y luego expande con felicidad, captura o evolución según necesites.

```kotlin
data class PokemonSpecies(
    @SerializedName("base_happiness") val baseHappiness: Int?, // 0-140: sirve para comparar afinidad
    @SerializedName("capture_rate") val captureRate: Int?, // mayor número = más fácil de capturar
    val color: NamedApiResource?, // color en la Pokédex (no siempre coincide con la ilustración)
    @SerializedName("evolves_from_species") val evolvesFromSpecies: NamedApiResource?, // especie previa si existe
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>, // descripciones por idioma/version
    val genera: List<GenusEntry>, // “Royal Sword Pokémon”, “Seed Pokémon”, etc.
    @SerializedName("growth_rate") val growthRate: NamedApiResource?, // curva de experiencia (útil para stats avanzados)
    val habitat: NamedApiResource?, // muchos Pokémon antiguos sí traen este dato
    val shape: NamedApiResource? // forma general (blob, humanoide, alado…)
)
```

> `PokemonSpecies` complementa el detalle con información narrativa (flavor text), especie, evolución previa y métricas como `base_happiness` y `capture_rate`.

---

## 3. Servicio remoto con Retrofit

Archivo: `app/src/main/java/com/example/app/data/remote/PokemonApiService.kt`

> 🛠️ **Guía:** parte con solo `fetchPokemonList`. Cuando estés cómodo, suma `fetchPokemonDetail` y `fetchPokemonSpecies`. Mantén los nombres iguales a los endpoints para ubicarlos rápido.

```kotlin
interface PokemonApiService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0 // offset permite saltar resultados: 20, 40, 60…
    ): PokemonListResponse

    @GET("pokemon/{nameOrId}")
    suspend fun fetchPokemonDetail(
        @Path("nameOrId") nameOrId: String // admite tanto el nombre (“gengar”) como el número (“94”)
    ): PokemonDetail

    @GET("pokemon-species/{nameOrId}")
    suspend fun fetchPokemonSpecies(
        @Path("nameOrId") nameOrId: String // endpoint separado con flavor text, evolución, etc.
    ): PokemonSpecies
}
```

Archivo: `app/src/main/java/com/example/app/data/remote/NetworkModule.kt`

> 🔐 **Guía:** si necesitas logging, aquí es donde puedes inyectar un `OkHttpClient` con `HttpLoggingInterceptor`. Evita configurarlo directamente en cada repositorio.

```kotlin
object NetworkModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val api: PokemonApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)
    }
}
```

> `NetworkModule` centraliza la configuración de Retrofit y nos entrega una instancia lista para usar del servicio.

### Configurar permisos de Internet

Retrofit u OkHttp no podrán conectarse a PokeAPI si la app no declara el permiso de red. Abre `app/src/main/AndroidManifest.xml` y, justo debajo de la etiqueta `<manifest>`, agrega:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Luego vuelve a generar e instalar la aplicación. Si reinstalas sobre una versión que no tenía este permiso, Android seguirá bloqueando las llamadas y verás el error `java.lang.SecurityException: Permission denied (missing INTERNET permission?)`.

---

## 4. Repository: puerta de entrada a los datos

Archivo: `app/src/main/java/com/example/app/data/PokemonRepository.kt`

> 💡 **Guía:** mantén este archivo libre de lógica de UI. Si agregas caché o base de datos, hazlo aquí para que el ViewModel solo consuma métodos limpios como `getPokemonDetail`.

```kotlin
class PokemonRepository(
    private val api: PokemonApiService = NetworkModule.api
) {
    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): PokemonListResponse {
        return api.fetchPokemonList(limit, offset) // aquí podrías agregar cache/local DB si fuera necesario
    }

    suspend fun getPokemonDetail(nameOrId: String): PokemonDetail {
        return api.fetchPokemonDetail(nameOrId) // mantiene la UI aislada de Retrofit
    }

    suspend fun getPokemonSpecies(nameOrId: String): PokemonSpecies {
        return api.fetchPokemonSpecies(nameOrId) // reutilizamos la misma instancia de API
    }
}
```

> La UI nunca habla directamente con Retrofit. Siempre pasa por el `Repository`, lo que facilita reemplazar la fuente de datos en el futuro (cache local, base de datos, etc.).

---

## 5. UiState: el estado inmutable de la UI

Archivo: `app/src/main/java/com/example/app/viewmodel/PokemonUiState.kt`

> 🧠 **Guía:** agrega solo los campos que la UI necesita. Cuando el diseño cambie, actualiza este archivo antes que el ViewModel para mantener un flujo claro.

```kotlin
data class PokemonListUiState(
    val pokemons: List<BasicPokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val limit: Int = 20, // recordamos los parámetros actuales para mostrarlos en la UI
    val offset: Int = 0
)

data class PokemonDetailUiState(
    val detail: PokemonDetail? = null, // null hasta que la API responda
    val isLoading: Boolean = false,
    val error: String? = null,
    val species: PokemonSpecies? = null, // info adicional (flavor text, evolución…)
    val speciesError: String? = null // guardamos un error separado para species
)
```

> Los estados son inmutables. Solo el ViewModel los modifica mediante copias (`copy`).

---

## 6. ViewModel: orquestando el flujo

Archivo: `app/src/main/java/com/example/app/viewmodel/PokemonViewModel.kt`

> 🚀 **Guía:** si el repositorio lanza excepciones, capta todo aquí. Así podrás decidir si muestras un Snackbar, un dialog, o un estado vacío sin tocar capas inferiores.

```kotlin
class PokemonViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    var listState by mutableStateOf(PokemonListUiState())
        private set

    var detailState by mutableStateOf(PokemonDetailUiState())
        private set

    fun loadList(limit: Int = 20, offset: Int = 0) {
        listState = listState.copy(
            isLoading = true,
            error = null,
            limit = limit,
            offset = offset
        )
        viewModelScope.launch {
            try {
                val result = repository.getPokemonList(limit, offset)
                listState = listState.copy(
                    pokemons = result.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                listState = listState.copy(isLoading = false, error = e.message) // mostramos el mensaje en la pantalla
            }
        }
    }

    fun loadDetail(nameOrId: String) {
        detailState = detailState.copy(
            isLoading = true,
            error = null,
            species = null,
            speciesError = null
        )
        viewModelScope.launch {
            try {
                val result = repository.getPokemonDetail(nameOrId)
                detailState = detailState.copy(detail = result) // completamos el detalle base
                try {
                    val species = repository.getPokemonSpecies(nameOrId)
                    detailState = detailState.copy(species = species) // si falla species seguimos mostrando el detalle base
                } catch (speciesError: Exception) {
                    detailState = detailState.copy(speciesError = speciesError.message)
                }
                detailState = detailState.copy(isLoading = false)
            } catch (e: Exception) {
                detailState = detailState.copy(isLoading = false, error = e.message)
            }
        }
    }
}
```

---

## 7. UI con Jetpack Compose

Archivo: `app/src/main/java/com/example/app/view/PokemonScreens.kt`

### `PokemonExplorerScreen`: hub principal tras el login

> 🎯 **Guía:** lanza `loadList()` en `LaunchedEffect`, usa un `LazyColumn` para que todo el panel sea scrollable y permite modificar `limit/offset` como en `pokemon?limit=100000&offset=0`. Agrega mejoras incrementales (búsqueda, filtros) sin romper el flujo principal.

```kotlin
@Composable
fun PokemonExplorerScreen(
    onPokemonSelected: (String) -> Unit,
    onProfileClick: () -> Unit,
    viewModel: PokemonViewModel = viewModel()
) {
    val listState = viewModel.listState
    val detailState = viewModel.detailState
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var limitInput by rememberSaveable { mutableStateOf(listState.limit.toString()) }
    var offsetInput by rememberSaveable { mutableStateOf(listState.offset.toString()) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.loadList() // carga inicial de la pokédex al entrar por primera vez
    }

    LaunchedEffect(listState.limit, listState.offset) {
        limitInput = listState.limit.toString() // mantenemos sincronizados los TextField con el estado real
        offsetInput = listState.offset.toString()
    }

    fun performSearch() {
        val query = searchQuery.trim()
        if (query.isEmpty()) return
        val normalized = if (query.any { it.isLetter() }) query.lowercase() else query // si son números, respetamos el ID tal cual
        focusManager.clearFocus()
        viewModel.loadDetail(normalized)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.Person, contentDescription = "Ir al perfil")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Explora Pokémon y utiliza el icono del AppBar para regresar al perfil.")

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre o número") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { performSearch() })
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = limitInput,
                    onValueChange = { limitInput = it },
                    label = { Text("Limit") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = offsetInput,
                    onValueChange = { offsetInput = it },
                    label = { Text("Offset") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Button(
                    onClick = {
                        val limit = limitInput.toIntOrNull()?.takeIf { it > 0 } ?: 20 // si ingresan letras, volvemos al valor por defecto
                        val offset = offsetInput.toIntOrNull()?.coerceAtLeast(0) ?: 0 // garantizamos offset >= 0
                        focusManager.clearFocus()
                        viewModel.loadList(limit, offset)
                    }
                ) {
                    Text("Actualizar")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { performSearch() },
                    enabled = searchQuery.isNotBlank()
                ) {
                    Text("Consultar")
                }
            }

            if (detailState.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) // indicador independiente para la consulta puntual
            }

            detailState.error?.let {
                Text("Error al consultar: ${'$'}it", color = MaterialTheme.colorScheme.error)
            }

            detailState.detail?.let { detail ->
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = detail.name.replaceFirstChar { it.uppercase() },
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        detail.sprites.frontDefault?.let { url ->
                            Image(
                                painter = rememberAsyncImagePainter(url),
                                contentDescription = detail.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .height(160.dp)
                            )
                        }
                        Text("Tipos: ${'$'}{detail.types.joinToString { it.type.name }}") // joinToString evita armar manualmente una cadena

                        detailState.species?.let { species ->
                            val flavor = species.flavorTextEntries.firstOrNull { it.language.name == "es" }
                                ?: species.flavorTextEntries.firstOrNull { it.language.name == "en" }
                            flavor?.flavorText
                                ?.replace("\n", " ")
                                ?.replace("\u000c", " ") // algunos textos traen este salto de página invisible
                                ?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }

                            val genus = species.genera.firstOrNull { it.language.name == "es" }
                                ?: species.genera.firstOrNull { it.language.name == "en" }
                            genus?.let { Text("Especie: ${'$'}{it.genus}") }

                            Text("Felicidad base: ${'$'}{species.baseHappiness ?: "—"}")
                            Text("Ratio de captura: ${'$'}{species.captureRate ?: "—"}")
                            species.evolvesFromSpecies?.name?.let { origin ->
                                Text("Evoluciona de: ${'$'}{origin.replaceFirstChar { it.uppercase() }}")
                            }
                        }

                        detailState.speciesError?.let {
                            Text(
                                text = "No fue posible cargar datos adicionales: ${'$'}it",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall // avisamos sin bloquear la info principal
                            )
                        }

                        TextButton(onClick = { onPokemonSelected(detail.name) }) {
                            Text("Ver detalle completo")
                        }
                    }
                }
            }

            Text("Pokémon disponibles", style = MaterialTheme.typography.titleMedium)
            if (listState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
            ) {
                items(listState.pokemons) { pokemon ->
                    val id = pokemon.url.trimEnd('/').substringAfterLast('/').toIntOrNull()
                    val label = buildString {
                        id?.let { append("#${'$'}it - ") } // el ID viene al final de la URL; nos ahorramos otra consulta
                        append(pokemon.name.replaceFirstChar { it.uppercase() })
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                searchQuery = pokemon.name
                                viewModel.loadDetail(pokemon.name)
                            }
                    ) {
                        Text(label, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
```

> El usuario puede explorar por nombre o número, ajustar `limit`/`offset` tal como en la URL `pokemon?limit=100000&offset=0`, y consultar narrativa, especie, evolución previa y métricas provenientes de `pokemon-species`.
### `PokemonListScreen`: componente reutilizable

> 🧪 **Guía:** usa este listado cuando solo quieras mostrar datos. Acepta un callback `onPokemonClick` para navegar sin acoplarte a un `NavController`.

```kotlin
@Composable
fun PokemonListScreen(
    onPokemonClick: (String) -> Unit,
    viewModel: PokemonViewModel = viewModel()
) {
    val state = viewModel.listState

    LaunchedEffect(Unit) {
        viewModel.loadList() // disparar la carga una vez cuando el composable aparece
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Lista de Pokémon", fontSize = 24.sp)

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        state.error?.let { error ->
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.pokemons) { pokemon ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onPokemonClick(pokemon.name) }
                ) {
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() }, // capitalizamos solo la primera letra
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
```

### `PokemonDetailScreen`: detalle standalone

> 🧷 **Guía:** ideal para pantallas de detalle dentro de `NavHost`. Si necesitas mostrar stats adicionales, crea subcomposables para mantenerlo legible.

```kotlin
@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    viewModel: PokemonViewModel = viewModel()
) {
    val state = viewModel.detailState

    LaunchedEffect(pokemonName) {
        viewModel.loadDetail(pokemonName) // vuelve a cargar si el nombre cambia en la navegación
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let { error ->
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
        }

        state.detail?.let { detail ->
            Text(
                text = detail.name.replaceFirstChar { it.uppercase() },
                fontSize = 28.sp
            )

            detail.sprites.frontDefault?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = detail.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(200.dp) // tamaño fijo para evitar cambios bruscos de layout
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Tipos: ${detail.types.joinToString { it.type.name }}") // joinToString genera un texto legible (“Grass, Poison”)
        }
    }
}
```

> Para la imagen usamos `rememberAsyncImagePainter` de Coil. Asegúrate de agregar `implementation("io.coil-kt:coil-compose:<versión>")` en `app/build.gradle.kts`.

---

## 8. Flujo unidireccional (UDF)

```
Usuario → Composable (UI)
          ↓
     PokemonViewModel
          ↓
     PokemonRepository
          ↓
     PokeAPI
          ↓
     UiState se actualiza → Compose redibuja
```

Este flujo mantiene la lógica encapsulada y la UI declarativa.

---

## 9. Checklist para replicar el módulo

1. Confirmar que los modelos en `model/Pokemon.kt` coinciden con el JSON de PokeAPI.
2. Revisar `NetworkModule` y `PokemonApiService` para entender cómo se configura Retrofit.
3. Explorar `PokemonRepository` para ver cómo se encapsulan las llamadas.
4. Analizar `PokemonUiState` y `PokemonViewModel` para comprender el manejo de estado.
5. Navegar a `PokemonScreens.kt` y probar la UI desde un `NavHost`.
6. Agregar la dependencia de Coil si aún no se encuentra en Gradle.
7. (Opcional) Integrar las pantallas en la navegación principal (`AppNavigation.kt`).

---

## 10. Relación con Evaluación 2

Este flujo refleja lo que se espera en la evaluación: separar responsabilidades por capas, mantener el estado en el ViewModel y conectar la UI con datos reales de manera reactiva.

---

### Próximo módulo → **StateFlow, Repository Pattern formal y flujo UDF avanzado**.
