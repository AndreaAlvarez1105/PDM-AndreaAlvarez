package com.example.labo01_pdm

import org.junit.Test
import kotlin.math.ln

class Computadora(
    val CPU: String,
    var memoria_RAM: Int,
    var OS: String,
    var almacenamiento: String,
    val Pantalla: Int
) {
    val programas =
        listOf("Notion 2026", "Facebook 2024", "Steam 2027", "Word 2026", "Brave 2026", "Egde 2015")
    val programasActuales = programas.filter { it.contains("2026") }

    fun encender() {
        println("Loading..........\n  Bienvenida! <3 \n------------------------")
    }

    fun apagar() {
        println("Shutting down..........\n  Byee! \n------------------------")
    }
}

class Calculadora(val marca: String, val lifespan: Int, val precio: Double) {

    fun Sumar(a: Int, b: Int) {
        var resultado = a + b;
        println("Suma: $resultado")
    }

    fun Resta(a: Int, b: Int) {
        var resultado = a - b;
        println("Resta: $resultado")
    }

    fun Multiplicacion(a: Int, b: Int) {
        var resultado = a * b;
        println("Multiplicacion: $resultado")
    }

    fun Division(a: Int, b: Int) {
        if (b == 0) {
            println("Division: MATH ERROR: No se puede dividir entre 0")
        } else {
            var resultado = a / b;
            println("Division: $resultado")
        }
    }

}

data class Estudiante(val nombre: String, val carnet: String, val asignatura: String){
}

class Ejercicios_Lab01 {

    @Test
    fun computadora() {
        val micompu = Computadora("Intel Core i7", 16, "Windows", "SSD", 14)
        micompu.encender()
        micompu.apagar()
        println("Programas actuales: " + micompu.programasActuales + "\n")
    }

    @Test
    fun calculadora() {
        val micalcu = Calculadora("Casio", 8, 55.9)

        println("----- CALCULADORA ---- ")
        micalcu.Sumar(45, -15)
        micalcu.Resta(46, 32)
        micalcu.Multiplicacion(22, 6)
        micalcu.Division(98, 0)
        println("---------------------- ")

    }

    @Test
    fun estudiante (){
        val estudiante1 = Estudiante("Andrea Álvarez", "00073824", "Programación de Dispositivos Móviles")
        val estudiante2 = Estudiante("Pamela López", "73824000", "Análisis numérico")
        val estudiante3 = Estudiante("Agatha Chritie", "121334", "Programación de Dispositivos Móviles")
        val estudiante4 = Estudiante("Sofia Reyes", "85483083", "Programación de Dispositivos Móviles")
        val estudiante5 = Estudiante("Max Vasquez", "3947394", "Análisis numérico")
        val estudiante6 = Estudiante("Kim Rivera", "0808644", "Análisis numérico")
        val estudiante7 = Estudiante("Maya Hernandez", "5735468", "Análisis numérico")


        val ciclo01 = listOf(estudiante1, estudiante2, estudiante3, estudiante4, estudiante5, estudiante6, estudiante7)
        val listPDM = ciclo01.filter {
            it.asignatura == "Programación de Dispositivos Móviles"

        }

        println(listPDM)

    }
}



