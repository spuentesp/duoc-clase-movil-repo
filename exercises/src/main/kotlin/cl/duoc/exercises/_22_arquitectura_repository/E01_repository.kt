package cl.duoc.exercises._22_arquitectura_repository

import kotlinx.coroutines.runBlocking

// ============================================================
// POR QUÉ Repository Pattern
// ============================================================
// Una pantalla que carga datos no debería saber si vienen de:
//   - una API REST
//   - una base de datos local
//   - un fake en memoria para tests
// Repository encapsula la fuente. La UI pide "dame los items";
// el Repository decide de dónde sacarlos.
//
// Sin Repository: ViewModel -> API (acoplado).
// Con Repository: ViewModel -> Repository -> API/DB/Fake.
//
// DEMO: interface ItemRepository + dos implementaciones (Fake/Api) + un ViewModel
// que la consume. main() muestra cómo cambiar la fuente sin tocar el ViewModel.

interface ItemRepository {
    suspend fun getItems(): List<String>
}

class FakeItemRepository : ItemRepository {
    override suspend fun getItems(): List<String> = listOf("fake-1", "fake-2")
}

class ApiItemRepository : ItemRepository {
    override suspend fun getItems(): List<String> {
        // llamada HTTP real
        return listOf("api-1", "api-2")
    }
}

class ItemViewModel(private val repo: ItemRepository) {
    suspend fun load(): List<String> = repo.getItems()
}

fun main() {
    // En tests: pasamos Fake. En prod: pasamos Api.
    val vm = ItemViewModel(FakeItemRepository())
    println(runBlocking { vm.load() })  // [fake-1, fake-2]
}
