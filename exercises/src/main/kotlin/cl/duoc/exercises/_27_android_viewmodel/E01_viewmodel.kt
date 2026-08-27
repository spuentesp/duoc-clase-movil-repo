package cl.duoc.exercises._27_android_viewmodel

// ============================================================
// BRIDGE: ViewModel en Android (solo lectura)
// ============================================================

/*
// ViewModel sobrevive a rotación. Se atacha a un ViewModelStoreOwner.
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    fun increment() { _count.value++ }
}

// En el composable, obtienes la instancia con viewModel():
@Composable
fun CounterScreen(vm: CounterViewModel = viewModel()) {
    val count by vm.count.collectAsState()
    Button(onClick = vm::increment) { Text("${'$'}count") }
}

// viewModelScope se cancela automáticamente cuando el VM se destruye.
class MyViewModel : ViewModel() {
    fun load() {
        viewModelScope.launch {
            val data = api.fetch()
            _state.value = data
        }
    }
}
*/
