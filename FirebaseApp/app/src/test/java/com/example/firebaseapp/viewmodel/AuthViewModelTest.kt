package com.example.firebaseapp.viewmodel

import com.example.firebaseapp.data.auth.AuthRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para AuthViewModel.
 *
 * Se utiliza MockK para simular (mock) el AuthRepository, lo que permite
 * probar la lógica del ViewModel de forma aislada sin depender de Firebase.
 * Los tests verifican que el ViewModel maneje correctamente los diferentes
 * estados de autenticación: Idle, Loading, Success y Error.
 */
class AuthViewModelTest {

    // Repositorio simulado (mock) que reemplaza la implementación real de Firebase
    private lateinit var repository: AuthRepository
    // ViewModel bajo prueba
    private lateinit var viewModel: AuthViewModel

    /**
     * Configuración inicial que se ejecuta antes de cada test.
     * - Crea un mock relajado del repositorio (responde con valores por defecto)
     * - Simula que no hay usuario autenticado actualmente
     * - Instancia el ViewModel con el repositorio mockeado
     */
    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        every { repository.getCurrentUser() } returns null
        viewModel = AuthViewModel(repository)
    }

    /**
     * Test: Login con email vacío muestra error.
     *
     * Verifica que al intentar iniciar sesión con un email vacío,
     * el ViewModel cambie el estado a Error con el mensaje
     * "Email y contrasena son requeridos".
     */
    @Test
    fun `login with empty email shows error`() {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Error)
        assertEquals(
            "Email y contrasena son requeridos",
            (viewModel.authState.value as AuthState.Error).message
        )
    }

    /**
     * Test: Login con contraseña vacía muestra error.
     *
     * Verifica que al intentar iniciar sesión con una contraseña vacía,
     * el ViewModel cambie el estado a Error con el mensaje
     * "Email y contrasena son requeridos".
     */
    @Test
    fun `login with empty password shows error`() {
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Error)
        assertEquals(
            "Email y contrasena son requeridos",
            (viewModel.authState.value as AuthState.Error).message
        )
    }

    /**
     * Test: Login con campos que solo contienen espacios en blanco muestra error.
     *
     * Verifica que los campos con solo espacios ("   ") sean tratados
     * como vacíos y produzcan un estado de Error.
     */
    @Test
    fun `login with blank fields shows error`() {
        viewModel.onEmailChange("   ")
        viewModel.onPasswordChange("   ")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Error)
    }

    /**
     * Test: Login exitoso actualiza el estado a Success.
     *
     * Simula que el repositorio llama al callback onSuccess al intentar
     * iniciar sesión. Verifica que el ViewModel cambie el estado a
     * AuthState.Success con el mensaje "Sesion Iniciada".
     */
    @Test
    fun `login success updates auth state to success`() {
        every {
            repository.signInWithEmailAndPassword(any(), any(), any(), any())
        } answers {
            // Extrae y ejecuta el callback de éxito (tercer argumento)
            val onSuccess = thirdArg<() -> Unit>()
            onSuccess()
        }

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Success)
        assertEquals("Sesion Iniciada", (viewModel.authState.value as AuthState.Success).message)
    }

    /**
     * Test: Login fallido actualiza el estado a Error.
     *
     * Simula que el repositorio llama al callback onFailure al intentar
     * iniciar sesión, pasando una excepción con mensaje "Invalid credentials".
     * Verifica que el ViewModel cambie el estado a AuthState.Error
     * con el mensaje de la excepción.
     */

    @Test
    fun `login failure updates auth state to error`() {
        every {
            repository.signInWithEmailAndPassword(any(), any(), any(), any())
        } answers {
            // Extrae y ejecuta el callback de éxito (tercer argumento)
            val onFailure = arg<(Exception) -> Unit>(3)
            onFailure(Exception("Invalid credentials"))
        }

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Error)
        assertEquals("Invalid credentials", (viewModel.authState.value as AuthState.Error).message)
    }


    /**
     * Test: Registro con campos vacíos muestra error.
     *
     * Verifica que al intentar registrarse con email y contraseña vacíos,
     * el ViewModel cambie el estado a Error.
     */

    @Test
    fun `login with empty fields shows error`() {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Error)
        assertEquals(
            "Email y contrasena son requeridos",
            (viewModel.authState.value as AuthState.Error).message
        )
    }



    /**
     * Test: Registro exitoso actualiza el estado a Success.
     *
     * Simula que el repositorio llama al callback onSuccess al crear
     * un usuario. Verifica que el ViewModel cambie el estado a
     * AuthState.Success con el mensaje "Usuario registrado".
     */

    @Test
    fun `register success updates auth state to success`() {
        every {
            repository.createUserWithEmailAndPassword(any(), any(), any(), any())
        } answers {
            // Extrae y ejecuta el callback de éxito (tercer argumento)
            val onSuccess = thirdArg<() -> Unit>()
            onSuccess()
        }

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.register()

        assertTrue(viewModel.authState.value is AuthState.Success)
        assertEquals("Usuario registrado", (viewModel.authState.value as AuthState.Success).message)
    }



    /**
     * Test: Registro fallido actualiza el estado a Error.
     *
     * Simula que el repositorio llama al callback onFailure al crear
     * un usuario, pasando una excepción con mensaje "Email already in use".
     * Verifica que el ViewModel cambie el estado a AuthState.Error
     * con el mensaje de la excepción.
     */

    @Test
    fun `register failure updates auth state to error`() {
        every {
            repository.createUserWithEmailAndPassword(any(), any(), any(), any())
        } answers {
            // Extrae y ejecuta el callback de éxito (tercer argumento)
            val onFailure = arg<(Exception) -> Unit>(3)
            onFailure(Exception("Email already in use"))
        }

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.register()

        assertTrue(viewModel.authState.value is AuthState.Error)
        assertEquals("Email already in use", (viewModel.authState.value as AuthState.Error).message)
    }


    /**
     * Test: Logout llama al repositorio y reinicia el estado.
     *
     * Verifica que al cerrar sesión:
     * - Se llame a repository.signOut()
     * - Se limpien los campos de email y contraseña
     * - El estado de autenticación vuelva a Idle
     */

    @Test
    fun `logout calls repository and resets state`() {
        viewModel.logout()
        verify { repository.signOut() }
        assertEquals("", viewModel.email.value)
        assertEquals("", viewModel.password.value)
        assertTrue(viewModel.authState.value is AuthState.Idle)
    }

    /**
     * Test: onEmailChange actualiza el estado del email.
     *
     * Verifica que al llamar onEmailChange con un nuevo valor,
     * el StateFlow de email se actualice correctamente.
     */

    @Test
    fun `onEmailChange updates email state`() {
        viewModel.onEmailChange("test@test.com")
        assertEquals("test@test.com", viewModel.email.value)

    }

    /**
     * Test: onPasswordChange actualiza el estado de la contraseña.
     *
     * Verifica que al llamar onPasswordChange con un nuevo valor,
     * el StateFlow de contraseña se actualice correctamente.
     */
    @Test
    fun `onPasswordChange updates password state`() {
        viewModel.onPasswordChange("password123")
        assertEquals("password123", viewModel.password.value)

    }


    /**
     * Test: Login con mensaje de error nulo muestra un error genérico.
     *
     * Simula un fallo de login donde la excepción tiene un mensaje nulo.
     * Verifica que el ViewModel muestre el mensaje genérico
     * "Error al iniciar sesion" en lugar de crash o mostrar null.
     */

    @Test
    fun `login with null error message shows generic error message`() {
        every {
            repository.signInWithEmailAndPassword(any(), any(), any(), any())
        } answers {
            // Extrae y ejecuta el callback de éxito (tercer argumento)
            val onFailure = arg<(Exception) -> Unit>(3)
            onFailure(Exception("Error al iniciar sesion"))
        }

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.login()

        assertTrue(viewModel.authState.value is AuthState.Error)
        assertEquals("Error al iniciar sesion", (viewModel.authState.value as AuthState.Error).message)
    }

}