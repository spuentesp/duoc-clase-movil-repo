package com.example.baseproject.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Pruebas unitarias para CounterViewModel
 *
 * Demuestra cómo probar:
 * - ViewModels
 * - StateFlow
 * - Coroutines en tests
 * - Cambios de estado
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CounterViewModelTest {

    private lateinit var viewModel: CounterViewModel
    private val testDispatcher = StandardTestDispatcher()

    /**
     * Configuración antes de cada test
     * Configura el dispatcher de coroutines para tests
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CounterViewModel()
    }

    /**
     * Limpieza después de cada test
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `contador inicial es cero`() {
        // Then
        assertEquals(0, viewModel.count.value)
    }

    @Test
    fun `increment aumenta el contador en 1`() {
        // When
        viewModel.increment()

        // Then
        assertEquals(1, viewModel.count.value)
    }

    @Test
    fun `multiple increments funcionan correctamente`() {
        // When
        repeat(5) {
            viewModel.increment()
        }

        // Then
        assertEquals(5, viewModel.count.value)
    }

    @Test
    fun `decrement reduce el contador en 1`() {
        // Given
        viewModel.setCount(5)

        // When
        viewModel.decrement()

        // Then
        assertEquals(4, viewModel.count.value)
    }

    @Test
    fun `decrement no permite valores negativos`() {
        // Given
        viewModel.setCount(0)

        // When
        viewModel.decrement()

        // Then
        assertEquals(0, viewModel.count.value)
    }

    @Test
    fun `reset establece el contador a cero`() {
        // Given
        viewModel.setCount(10)

        // When
        viewModel.reset()

        // Then
        assertEquals(0, viewModel.count.value)
        assertEquals("Contador reiniciado", viewModel.message.value)
    }

    @Test
    fun `setCount establece el valor correcto`() {
        // When
        viewModel.setCount(42)

        // Then
        assertEquals(42, viewModel.count.value)
    }

    @Test
    fun `setCount no acepta valores negativos`() {
        // Given
        viewModel.setCount(10)

        // When
        viewModel.setCount(-5)

        // Then
        assertEquals(10, viewModel.count.value)
    }

    @Test
    fun `multiplyBy funciona correctamente`() {
        // Given
        viewModel.setCount(5)

        // When
        viewModel.multiplyBy(3)

        // Then
        assertEquals(15, viewModel.count.value)
    }

    @Test
    fun `mensaje se actualiza correctamente para contador cero`() {
        // Given
        viewModel.setCount(0)

        // Then
        assertEquals("El contador está en cero", viewModel.message.value)
    }

    @Test
    fun `mensaje se actualiza correctamente para contador bajo`() {
        // Given
        viewModel.setCount(5)

        // Then
        assertEquals("Contador bajo", viewModel.message.value)
    }

    @Test
    fun `mensaje se actualiza correctamente para contador medio`() {
        // Given
        viewModel.setCount(25)

        // Then
        assertEquals("Contador medio", viewModel.message.value)
    }

    @Test
    fun `mensaje se actualiza correctamente para contador alto`() {
        // Given
        viewModel.setCount(100)

        // Then
        assertEquals("Contador alto", viewModel.message.value)
    }

    @Test
    fun `secuencia de operaciones funciona correctamente`() {
        // When
        viewModel.increment()      // 0 -> 1
        viewModel.increment()      // 1 -> 2
        viewModel.multiplyBy(5)    // 2 -> 10
        viewModel.decrement()      // 10 -> 9

        // Then
        assertEquals(9, viewModel.count.value)
        assertEquals("Contador bajo", viewModel.message.value)
    }
}
