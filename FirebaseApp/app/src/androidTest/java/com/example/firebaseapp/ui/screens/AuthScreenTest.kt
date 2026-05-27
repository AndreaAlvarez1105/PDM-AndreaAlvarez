package com.example.firebaseapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.firebaseapp.data.auth.AuthRepository
import com.example.firebaseapp.viewmodel.AuthState
import com.example.firebaseapp.viewmodel.AuthViewModel
import com.example.firebaseapp.viewmodel.NavigationState
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests de UI para la pantalla de autenticación (AuthScreen).
 *
 * Utilizan createComposeRule para renderizar AuthScreen de forma aislada
 * junto con un AuthViewModel real alimentado por un AuthRepository mockeado.
 * Se prueba tanto la renderización correcta de los elementos UI como
 * la interacción del usuario con los campos y botones, verificando
 * que las acciones se propaguen correctamente al ViewModel y repositorio.
 */
class AuthScreenTest {

    // Regla de Compose para renderizar composables en un entorno de test
    @get:Rule
    val composeTestRule = createComposeRule()

    // Repositorio simulado para inyectar al ViewModel
    private lateinit var repository: AuthRepository
    // ViewModel que se probará a través de la UI
    private lateinit var viewModel: AuthViewModel

    /**
     * Configuración inicial que se ejecuta antes de cada test.
     * Crea un mock relajado del repositorio y una instancia real
     * del ViewModel para probar la integración UI-ViewModel.
     */
    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        viewModel = AuthViewModel(repository)
    }

    /**
     * Test: AuthScreen muestra el título "Firebase Auth Demo".
     *
     * Renderiza AuthScreen y verifica que el título de la pantalla
     * ("Firebase Auth Demo") sea visible para el usuario.
     */
    @Test
    fun authScreen_displaysTitle() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Firebase Auth Demo").assertIsDisplayed()
    }

    /**
     * Test: AuthScreen muestra el campo de entrada de email.
     *
     * Verifica que el TextField con etiqueta "Email" esté
     * presente y visible en la pantalla de autenticación.
     */
    @Test
    fun authScreen_displaysEmailField() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }

    /**
     * Test: AuthScreen muestra el campo de entrada de contraseña.
     *
     * Verifica que el TextField con etiqueta "Password" esté
     * presente y visible en la pantalla de autenticación.
     */
    @Test
    fun authScreen_displaysPasswordField() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    /**
     * Test: AuthScreen muestra el botón de Login.
     *
     * Verifica que el botón con texto "Login" esté
     * presente y visible en la pantalla de autenticación.
     */
    @Test
    fun authScreen_displaysLoginButton() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    /**
     * Test: AuthScreen muestra el botón de Register.
     *
     * Verifica que el botón con texto "Register" esté
     * presente y visible en la pantalla de autenticación.
     */
    @Test
    fun authScreen_displaysRegisterButton() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
    }

    /**
     * Test: Escribir en el campo Email actualiza el estado del ViewModel.
     *
     * Simula la escritura de un email en el campo de texto y verifica
     * que el StateFlow de email en el ViewModel se haya actualizado
     * con el valor ingresado, confirmando la conexión bidireccional
     * entre la UI y el ViewModel.
     */
    @Test
    fun authScreen_typingEmail_updatesViewModel() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Email").performTextInput("test@test.com")
        assertEquals("test@test.com", viewModel.email.value)
    }

    /**
     * Test: Escribir en el campo Password actualiza el estado del ViewModel.
     *
     * Simula la escritura de una contraseña en el campo de texto y verifica
     * que el StateFlow de password en el ViewModel se haya actualizado
     * con el valor ingresado.
     */
    @Test
    fun authScreen_typingPassword_updatesViewModel() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        assertEquals("password123", viewModel.password.value)
    }

    /**
     * Test: Hacer clic en Login llama al método de inicio de sesión del repositorio.
     *
     * Establece email y contraseña en el ViewModel, hace clic en el
     * botón Login y verifica que se haya llamado al método
     * signInWithEmailAndPassword del repositorio, confirmando
     * que la acción de la UI se propague correctamente hasta el repositorio.
     */
    @Test
    fun authScreen_clickingLogin_callsLoginOnViewModel() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        // Configura credenciales en el ViewModel directamente
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        // Simula clic en el botón Login desde la UI
        composeTestRule.onNodeWithText("Login").performClick()

        // Verifica que el repositorio recibió la llamada de inicio de sesión
        io.mockk.verify { repository.signInWithEmailAndPassword(any(), any(), any(), any()) }
    }

    /**
     * Test: Hacer clic en Register llama al método de registro del repositorio.
     *
     * Establece email y contraseña en el ViewModel, hace clic en el
     * botón Register y verifica que se haya llamado al método
     * createUserWithEmailAndPassword del repositorio, confirmando
     * que la acción de la UI se propague correctamente hasta el repositorio.
     */
    @Test
    fun authScreen_clickingRegister_callsRegisterOnViewModel() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        // Configura credenciales en el ViewModel directamente
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        // Simula clic en el botón Register desde la UI
        composeTestRule.onNodeWithText("Register").performClick()

        // Verifica que el repositorio recibió la llamada de registro
        io.mockk.verify { repository.createUserWithEmailAndPassword(any(), any(), any(), any()) }
    }

    /**
     * Test: AuthScreen muestra mensaje de error de validación.
     *
     * Establece campos vacíos y ejecuta login para provocar un error,
     * luego verifica que el mensaje "Email y contrasena son requeridos"
     * sea visible en la UI, confirmando que los errores del ViewModel
     * se reflejan en la pantalla.
     */
    @Test
    fun authScreen_showsErrorMessage() {
        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        val errorMsg = "Email y contrasena son requeridos"
        // Provoca un error de validación con campos vacíos
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")
        viewModel.login()

        // Verifica que el mensaje de error se muestre en la UI
        composeTestRule.onNodeWithText(errorMsg).assertIsDisplayed()
    }

    /**
     * Test: Los botones se deshabilitan durante el estado de carga (Loading).
     *
     * Simula una llamada de login que permanece en estado Loading
     * (no invoca callbacks de éxito ni de fallo). Verifica que
     * los botones Login y Register estén deshabilitados mientras
     * la operación está en curso, previniendo múltiples envíos.
     */
    @Test
    fun authScreen_buttonsDisabledDuringLoading() {
        // Configura el mock para NO invocar callbacks, manteniendo estado Loading
        io.mockk.every {
            repository.signInWithEmailAndPassword(any(), any(), any(), any())
        } answers { /* keep in loading state */ }

        composeTestRule.setContent {
            AuthScreen(viewModel = viewModel)
        }

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.login()

        // Durante el estado Loading, los botones deben estar deshabilitados
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Register").assertIsNotEnabled()
    }
}