package cl.duoc.exercises._24_arquitectura_di_manual

// ============================================================
// POR QUÉ Inyección de Dependencias (manual)
// ============================================================
// DI manual = pasar las dependencias por constructor, no crearlas dentro.
// Por qué:
//   - Testeable: en tests pasas un fake; en prod, el real.
//   - Transparente: miras la firma del constructor y sabes qué necesita.
//   - Sin frameworks mágicos: Koin/Hilt son opcionales; empezar manual.
//
// DEMO: ViewModel que crea sus deps vs ViewModel que las recibe.

class ApiClient { fun ping(): Boolean = true }

class BadViewModel {
    private val api = ApiClient()  // acoplado
    fun load() = api.ping()
}

class GoodViewModel(private val api: ApiClient) {
    fun load() = api.ping()
}

fun main() {
    val api = ApiClient()
    val vm = GoodViewModel(api)
    println(vm.load())
}
