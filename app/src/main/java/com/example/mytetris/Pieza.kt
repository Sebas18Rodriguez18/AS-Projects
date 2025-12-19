package com.example.mytetris

import android.graphics.Color

data class Coordenada(val fila: Int, val columna: Int)

enum class TipoPieza {
    I, O, T, S, Z, J, L
}

class Pieza(val tipo: TipoPieza) {
    
    var rotacion = 0
    val color: Int
    
    init {
        color = when (tipo) {
            TipoPieza.I -> Color.CYAN
            TipoPieza.O -> Color.YELLOW
            TipoPieza.T -> Color.MAGENTA
            TipoPieza.S -> Color.GREEN
            TipoPieza.Z -> Color.RED
            TipoPieza.J -> Color.BLUE
            TipoPieza.L -> Color.rgb(255, 165, 0) // Naranja
        }
    }
    
    fun obtenerForma(): List<Coordenada> {
        return when (tipo) {
            TipoPieza.I -> obtenerFormaI()
            TipoPieza.O -> obtenerFormaO()
            TipoPieza.T -> obtenerFormaT()
            TipoPieza.S -> obtenerFormaS()
            TipoPieza.Z -> obtenerFormaZ()
            TipoPieza.J -> obtenerFormaJ()
            TipoPieza.L -> obtenerFormaL()
        }
    }
    
    fun rotar() {
        rotacion = (rotacion + 1) % 4
    }
    
    // Pieza I (línea recta)
    private fun obtenerFormaI(): List<Coordenada> {
        return when (rotacion % 2) {
            0 -> listOf(
                Coordenada(0, 0),
                Coordenada(0, 1),
                Coordenada(0, 2),
                Coordenada(0, 3)
            )
            else -> listOf(
                Coordenada(0, 0),
                Coordenada(1, 0),
                Coordenada(2, 0),
                Coordenada(3, 0)
            )
        }
    }
    
    // Pieza O (cuadrado)
    private fun obtenerFormaO(): List<Coordenada> {
        return listOf(
            Coordenada(0, 0),
            Coordenada(0, 1),
            Coordenada(1, 0),
            Coordenada(1, 1)
        )
    }
    
    // Pieza T
    private fun obtenerFormaT(): List<Coordenada> {
        return when (rotacion % 4) {
            0 -> listOf(
                Coordenada(0, 1),
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(1, 2)
            )
            1 -> listOf(
                Coordenada(0, 1),
                Coordenada(1, 1),
                Coordenada(1, 2),
                Coordenada(2, 1)
            )
            2 -> listOf(
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(1, 2),
                Coordenada(2, 1)
            )
            else -> listOf(
                Coordenada(0, 1),
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(2, 1)
            )
        }
    }
    
    // Pieza S
    private fun obtenerFormaS(): List<Coordenada> {
        return when (rotacion % 2) {
            0 -> listOf(
                Coordenada(0, 1),
                Coordenada(0, 2),
                Coordenada(1, 0),
                Coordenada(1, 1)
            )
            else -> listOf(
                Coordenada(0, 0),
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(2, 1)
            )
        }
    }
    
    // Pieza Z
    private fun obtenerFormaZ(): List<Coordenada> {
        return when (rotacion % 2) {
            0 -> listOf(
                Coordenada(0, 0),
                Coordenada(0, 1),
                Coordenada(1, 1),
                Coordenada(1, 2)
            )
            else -> listOf(
                Coordenada(0, 1),
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(2, 0)
            )
        }
    }
    
    // Pieza J
    private fun obtenerFormaJ(): List<Coordenada> {
        return when (rotacion % 4) {
            0 -> listOf(
                Coordenada(0, 0),
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(1, 2)
            )
            1 -> listOf(
                Coordenada(0, 1),
                Coordenada(0, 2),
                Coordenada(1, 1),
                Coordenada(2, 1)
            )
            2 -> listOf(
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(1, 2),
                Coordenada(2, 2)
            )
            else -> listOf(
                Coordenada(0, 1),
                Coordenada(1, 1),
                Coordenada(2, 0),
                Coordenada(2, 1)
            )
        }
    }
    
    // Pieza L
    private fun obtenerFormaL(): List<Coordenada> {
        return when (rotacion % 4) {
            0 -> listOf(
                Coordenada(0, 2),
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(1, 2)
            )
            1 -> listOf(
                Coordenada(0, 1),
                Coordenada(1, 1),
                Coordenada(2, 1),
                Coordenada(2, 2)
            )
            2 -> listOf(
                Coordenada(1, 0),
                Coordenada(1, 1),
                Coordenada(1, 2),
                Coordenada(2, 0)
            )
            else -> listOf(
                Coordenada(0, 0),
                Coordenada(0, 1),
                Coordenada(1, 1),
                Coordenada(2, 1)
            )
        }
    }
}