package com.example.firebaseapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Tests de UI para la pantalla Home (HomeScreen).
 *
 * Utilizan createComposeRule para renderizar el composable de forma
 * aislada sin necesidad de lanzar la Activity completa. Se prueba
 * que HomeScreen muestre correctamente los datos del usuario
 * autenticado y que el botón de cerrar sesión funcione adecuadamente.
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Test: Muestra correctamente los datos del usuario autenticado.
     * Utiliza un correo de prueba y verifica que todos los campos
     * esperados se muestren en la pantalla.
     */
    @Test
    fun homeScreen_displaysUserDataCorrectly() {
        val testEmail = "estudiante@universidad.edu"

        composeTestRule.setContent {
            HomeScreen(
                userEmail = testEmail,
                onLogout = {}
            )
        }

        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()
        composeTestRule.onNodeWithText(testEmail).assertIsDisplayed()
        composeTestRule.onNodeWithText("Has iniciado sesion correctamente").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cerrar Sesion").assertIsDisplayed()
    }

    /**
     * Test: El botón de cerrar sesión funciona adecuadamente.
     * Verifica que al hacer clic en el botón "Cerrar Sesion",
     * se ejecute correctamente el callback onLogout.
     */
    @Test
    fun homeScreen_logoutButton_triggersCallback() {
        var logoutClicked = false

        // Acción: Renderizar el componente con nuestra bandera en el callback
        composeTestRule.setContent {
            HomeScreen(
                userEmail = "test@test.com",
                onLogout = { logoutClicked = true }
            )
        }

        // Simular el clic del usuario en el botón
        composeTestRule.onNodeWithText("Cerrar Sesion").performClick()

        // Verificación: La bandera debe haber cambiado a true
        assertTrue("El callback onLogout no se ejecutó", logoutClicked)
    }

    /**
     * Test: Layout estable con datos vacíos.
     * Simula un caso límite donde el correo electrónico del usuario
     * llega como un texto vacío. Verifica que el resto de los elementos
     * de la pantalla no desaparezcan ni fallen.
     */
    @Test
    fun homeScreen_emptyEmail_keepsLayoutStable() {
        composeTestRule.setContent {
            HomeScreen(
                userEmail = "",
                onLogout = {}
            )
        }

        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()
        composeTestRule.onNodeWithText("Has iniciado sesion correctamente").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cerrar Sesion").assertIsDisplayed()

    }

    /**
     * Test: Soporte para correos extrañamente largos.
     * Simula un escenario donde el usuario tiene un correo con una longitud
     * muy larga. Verifica que el componente Compose pueda renderizar
     * la cadena de texto completa dentro de la estructura sin lanzar
     * excepciones de desbordamiento de memoria o renderizado.
     */
    @Test
    fun homeScreen_veryLongEmail_rendersWithoutCrashing() {
        val longEmail = "este.es.un.correo.extremadamente.largo.para.probar.limites" +
                "@subdominio.departamento.universidad.muy.larga.edu.sv"

        composeTestRule.setContent {
            HomeScreen(
                userEmail = longEmail,
                onLogout = {}
            )
        }

        composeTestRule.onNodeWithText(longEmail).assertIsDisplayed()
    }


}