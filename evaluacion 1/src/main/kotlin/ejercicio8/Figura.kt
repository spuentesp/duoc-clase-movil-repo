package ejercicio8

sealed class Figura {
    abstract val color: String
    abstract fun area(): Double
    abstract fun perimetro(): Double

    class Circulo(override val color: String, val radio: Double) : Figura() {
        constructor(color: String) : this(color, radio = 1.0)
        override fun area(): Double = Math.PI * radio * radio
        override fun perimetro(): Double = 2 * Math.PI * radio
        override fun toString(): String = "Circulo(color=$color, radio=$radio)"
    }

    class Rectangulo(
        override val color: String,
        val ancho: Double,
        val alto: Double
    ) : Figura() {
        constructor(color: String, lado: Double) : this(color, lado, lado)
        val esCuadrado: Boolean get() = ancho == alto
        override fun area(): Double = ancho * alto
        override fun perimetro(): Double = 2 * (ancho + alto)
        override fun toString(): String = "Rectangulo(color=$color, ancho=$ancho, alto=$alto)"
    }

    class TrianguloEquilatero(override val color: String, val lado: Double) : Figura() {
        init {
            require(lado > 0) { "Lado debe ser > 0 (recibido: $lado)" }
        }
        override fun area(): Double = (Math.sqrt(3.0) / 4.0) * lado * lado
        override fun perimetro(): Double = 3 * lado
        override fun toString(): String = "TrianguloEquilatero(color=$color, lado=$lado)"
    }
}
