package com.example.firebaseapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

/**
 * Tests de instrumentación para MainActivity.
 *
 * Estos tests se ejecutan en un emulador o dispositivo Android real.
 * Utilizan createAndroidComposeRule que lanza la Activity completa,
 * permitiendo probar la UI de forma integral (test de integración).
 * A diferencia de los tests unitarios del ViewModel, estos verifican
 * que los composables se rendericen correctamente en la pantalla.
 */
class MainActivityInstrumentedTest {

    // Regla de Compose que lanza MainActivity en el dispositivo/emulador
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Test: La app se lanza y muestra la pantalla de autenticación.
     *
     * Verifica que al abrir la aplicación, el título "Firebase Auth Demo"
     * sea visible en pantalla, lo que confirma que la Activity
     * se inicia correctamente y muestra la AuthScreen.
     */
    @Test
    fun appLaunches_displaysAuthScreen() {
        composeTestRule.onNodeWithText("Firebase Auth Demo").assertIsDisplayed()
    }

    /**
     * Test: La pantalla de autenticación muestra todos los campos y botones.
     *
     * Verifica que los elementos principales de la AuthScreen estén
     * presentes y visibles: campo Email, campo Password, botón Login
     * y botón Register.
     */
    @Test
    fun authScreen_displaysAllFields() {
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
    }

    /**
     * Test: La pantalla muestra error de validación al hacer login con campos vacíos.
     *
     * Simula un clic en el botón Login sin ingresar datos,
     * y verifica que se muestre el mensaje de error
     * "Email y contrasena son requeridos" en la UI.
     */
    @Test
    fun authScreen_showsValidationErrorOnEmptyLogin() {
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Email y contrasena son requeridos").assertIsDisplayed()
    }

    /**
     * Test: Los campos de texto aceptan entrada del usuario.
     *
     * Verifica que los campos Email y Password permitan
     * escribir texto en ellos usando performTextInput.
     * Esto confirma que los TextFields son interactivos.
     */
    @Test
    fun authScreen_emailFieldAcceptsInput() {
        composeTestRule.onNodeWithText("Email").performTextInput("test@test.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password")
    }
}