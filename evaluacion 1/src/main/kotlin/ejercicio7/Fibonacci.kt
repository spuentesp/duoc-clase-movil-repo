package ejercicio7

class GeneradorFibonacci {

    fun primerosN(n: Int): List<Long> {
        require(n >= 0) { "n debe ser >= 0 (recibido: $n)" }
        if (n == 0) return emptyList()
        val salida = mutableListOf(0L, 1L)
        while (salida.size < n) {
            val siguiente = salida[salida.size - 1] + salida[salida.size - 2]
            salida += siguiente
        }
        return salida.take(n)
    }

    fun sumaHasta(n: Int): Long {
        require(n >= 0) { "n debe ser >= 0 (recibido: $n)" }
        return primerosN(n).sum()
    }

    fun menoresQue(limite: Long): List<Long> {
        val lista = mutableListOf(0L, 1L)
        while (true) {
            val siguiente = lista[lista.size - 1] + lista[lista.size - 2]
            if (siguiente >= limite) break
            lista += siguiente
        }
        return lista
    }
}
