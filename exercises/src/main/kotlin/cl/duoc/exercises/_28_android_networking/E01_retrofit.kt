package cl.duoc.exercises._28_android_networking

// ============================================================
// BRIDGE: Networking con Retrofit (solo lectura)
// ============================================================

/*
// Definir interfaz
interface PokeApi {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonDto
}

// Configurar Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(PokeApi::class.java)

// Consumir desde ViewModel
class PokeViewModel(private val api: PokeApi) : ViewModel() {
    fun load(id: Int) {
        viewModelScope.launch {
            try {
                val poke = api.getPokemon(id)
                _state.value = NetworkUiState.Success(poke)
            } catch (t: Throwable) {
                _state.value = NetworkUiState.Error(t.message ?: "Error")
            }
        }
    }
}
*/
