package com.example.mytetris

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private var piezaActual: View? = null
    private var filaActual = 0
    private var columnaActual = 4
    private val handler = Handler(Looper.getMainLooper())

    private var tamanoPixel = 0
    
    private val FILAS_TABLERO = 20
    private val COLUMNAS_TABLERO = 10
    
    private val tableroOcupado = Array(FILAS_TABLERO) { BooleanArray(COLUMNAS_TABLERO) }
    
    private val bloquesEnTablero = mutableMapOf<Pair<Int, Int>, View>()
    
    private lateinit var gestureDetector: GestureDetectorCompat
    private var puntuacion = 0
    private lateinit var scoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val densidad = resources.displayMetrics.density
        tamanoPixel = (30 * densidad).toInt()
        
        scoreTextView = findViewById(R.id.score_text)
        
        setupGestureDetector()
        
        crearPrimerBloque()
        handler.post(caerRunnable)
    }
    
    private fun setupGestureDetector() {
        val gameBoard = findViewById<FrameLayout>(R.id.game_board)
        
        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                return true
            }
            
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null) return false
                
                val diffX = e2.x - e1.x
                val diffY = e2.y - e1.y
                
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > 50) {
                        if (diffX > 0) {
                            moverDerecha()
                        } else {
                            moverIzquierda()
                        }
                        return true
                    }
                } else {
                    if (diffY > 50) {
                        acelerarCaida()
                        return true
                    }
                }
                
                return false
            }
        })
        
        gameBoard.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }
    
    private fun crearNuevaPieza() {
        val gameBoard = findViewById<FrameLayout>(R.id.game_board)

        val bloque = View(this)

        val params = FrameLayout.LayoutParams(tamanoPixel, tamanoPixel)
        params.leftMargin = tamanoPixel * 4
        params.topMargin = 0

        bloque.setBackgroundResource(R.drawable.bloque_base)
        bloque.layoutParams = params

        gameBoard.addView(bloque)

        piezaActual = bloque
        filaActual = 0
        columnaActual = 4
        
        handler.post(caerRunnable)
    }

    private fun crearPrimerBloque() {
        val gameBoard = findViewById<FrameLayout>(R.id.game_board)

        val bloque = View(this)

        val params = FrameLayout.LayoutParams(tamanoPixel, tamanoPixel)
        params.leftMargin = tamanoPixel * 4
        params.topMargin = 0

        bloque.setBackgroundResource(R.drawable.bloque_base)
        bloque.layoutParams = params

        gameBoard.addView(bloque)

        piezaActual = bloque
        filaActual = 0
        columnaActual = 4
    }

    private val caerRunnable = object : Runnable {
        override fun run() {
            if (puedeCaer()) {
                filaActual++

                val params = piezaActual?.layoutParams as? FrameLayout.LayoutParams
                if params != null) {
                    params.topMargin = filaActual * tamanoPixel
                    piezaActual?.layoutParams = params
                }

                handler.postDelayed(this, 500)
            } else {
                onPiezaAterrizó()
            }
        }
    }
    
    private fun puedeCaer(): Boolean {
        val siguienteFila = filaActual + 1
        
        if (siguienteFila >= FILAS_TABLERO) {
            return false
        }
        
        if (tableroOcupado[siguienteFila][columnaActual]) {
            return false
        }
        
        return true
    }
    
    private fun moverIzquierda() {
        if (columnaActual > 0 && !tableroOcupado[filaActual][columnaActual - 1]) {
            columnaActual--
            actualizarPosicionPieza()
        }
    }
    
    private fun moverDerecha() {
        if (columnaActual < COLUMNAS_TABLERO - 1 && !tableroOcupado[filaActual][columnaActual + 1]) {
            columnaActual++
            actualizarPosicionPieza()
        }
    }
    
    private fun acelerarCaida() {
        while (puedeCaer()) {
            filaActual++
        }
        actualizarPosicionPieza()
        
        handler.removeCallbacks(caerRunnable)
        onPiezaAterrizó()
    }
    
    private fun actualizarPosicionPieza() {
        piezaActual?.let { pieza ->
            val params = pieza.layoutParams as FrameLayout.LayoutParams
            params.leftMargin = columnaActual * tamanoPixel
            params.topMargin = filaActual * tamanoPixel
            pieza.layoutParams = params
        }
    }

    private fun onPiezaAterrizó() {
        tableroOcupado[filaActual][columnaActual] = true
        
        piezaActual?.let {
            it.alpha = 1.0f
            bloquesEnTablero[Pair(filaActual, columnaActual)] = it
        }
        
        verificarLineasCompletas()
        
        if (tableroOcupado[0][4]) {
            gameOver()
            return
        }
        
        handler.postDelayed({
            crearNuevaPieza()
        }, 500)
    }
    
    private fun verificarLineasCompletas() {
        val lineasAEliminar = mutableListOf<Int>()
        
        for (fila in 0 until FILAS_TABLERO) {
            var completa = true
            for (columna in 0 until COLUMNAS_TABLERO) {
                if (!tableroOcupado[fila][columna]) {
                    completa = false
                    break
                }
            }
            if (completa) {
                lineasAEliminar.add(fila)
            }
        }
        
        if (lineasAEliminar.isNotEmpty()) {
            eliminarLineas(lineasAEliminar)
            puntuacion += lineasAEliminar.size * 100
            actualizarPuntuacion()
        }
    }
    
    private fun actualizarPuntuacion() {
        scoreTextView.text = "Puntaje: $puntuacion"
    }
    
    private fun eliminarLineas(lineas: List<Int>) {
        val gameBoard = findViewById<FrameLayout>(R.id.game_board)
        
        for (fila in lineas) {
            for (columna in 0 until COLUMNAS_TABLERO) {
                val bloque = bloquesEnTablero[Pair(fila, columna)]
                bloque?.let { gameBoard.removeView(it) }
                bloquesEnTablero.remove(Pair(fila, columna))
            }
        }
        
        for (lineaEliminada in lineas.sortedDescending()) {
            for (fila in lineaEliminada downTo 1) {
                for (columna in 0 until COLUMNAS_TABLERO) {
                    tableroOcupado[fila][columna] = tableroOcupado[fila - 1][columna]
                    
                    val bloqueArriba = bloquesEnTablero[Pair(fila - 1, columna)]
                    if (bloqueArriba != null) {
                        bloquesEnTablero.remove(Pair(fila - 1, columna))
                        bloquesEnTablero[Pair(fila, columna)] = bloqueArriba
                        
                        val params = bloqueArriba.layoutParams as FrameLayout.LayoutParams
                        params.topMargin = fila * tamanoPixel
                        bloqueArriba.layoutParams = params
                    } else {
                        bloquesEnTablero.remove(Pair(fila, columna))
                    }
                }
            }
            
            for (columna in 0 until COLUMNAS_TABLERO) {
                tableroOcupado[0][columna] = false
            }
        }
    }
    
    private fun gameOver() {
        handler.removeCallbacks(caerRunnable)
        
        runOnUiThread {
            scoreTextView.text = "GAME OVER! Puntaje: $puntuacion"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(caerRunnable)
    }
}