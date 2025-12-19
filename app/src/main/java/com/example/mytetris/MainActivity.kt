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

    private var piezaActual: Pieza? = null
    private var bloquesPiezaActual = mutableListOf<View>()
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
    private lateinit var gameBoard: FrameLayout
    
    private var nivel = 1
    private var lineasEliminadas = 0
    private var velocidadCaida = 500L
    private lateinit var levelTextView: TextView
    private lateinit var linesTextView: TextView
    
    private val LINEAS_POR_NIVEL = 10
    private val VELOCIDAD_MINIMA = 100L
    private val REDUCCION_VELOCIDAD_POR_NIVEL = 40L

    private val caerRunnable = object : Runnable {
        override fun run() {
            if (puedeCaer()) {
                filaActual++
                actualizarPosicionPieza()
                handler.postDelayed(this, velocidadCaida)
            } else {
                onPiezaAterrizó()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val densidad = resources.displayMetrics.density
        tamanoPixel = (30 * densidad).toInt()
        
        gameBoard = findViewById(R.id.game_board)
        scoreTextView = findViewById(R.id.score_text)
        levelTextView = findViewById(R.id.level_text)
        linesTextView = findViewById(R.id.lines_text)
        
        setupGestureDetector()
        actualizarUI()
        
        crearNuevaPieza()
        handler.post(caerRunnable)
    }
    
    private fun setupGestureDetector() {
        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                rotarPieza()
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
        filaActual = 0
        columnaActual = 3
        
        val tipoAleatorio = TipoPieza.values().random()
        piezaActual = Pieza(tipoAleatorio)
        
        bloquesPiezaActual.forEach { gameBoard.removeView(it) }
        bloquesPiezaActual.clear()
        
        piezaActual?.let { pieza ->
            val forma = pieza.obtenerForma()
            
            forma.forEach { coord ->
                val bloque = View(this)
                bloque.setBackgroundColor(pieza.color)
                
                val params = FrameLayout.LayoutParams(tamanoPixel, tamanoPixel)
                bloque.layoutParams = params
                
                gameBoard.addView(bloque)
                bloquesPiezaActual.add(bloque)
            }
            
            actualizarPosicionPieza()
        }
        
        handler.postDelayed(caerRunnable, velocidadCaida)
    }
    
    private fun puedeCaer(): Boolean {
        piezaActual?.let { pieza ->
            val forma = pieza.obtenerForma()
            
            for (coord in forma) {
                val nuevaFila = filaActual + coord.fila + 1
                val nuevaColumna = columnaActual + coord.columna
                
                if (nuevaFila >= FILAS_TABLERO) return false
                if (nuevaColumna < 0 || nuevaColumna >= COLUMNAS_TABLERO) return false
                if (tableroOcupado[nuevaFila][nuevaColumna]) return false
            }
            return true
        }
        return false
    }
    
    private fun puedeMover(deltaFila: Int, deltaColumna: Int): Boolean {
        piezaActual?.let { pieza ->
            val forma = pieza.obtenerForma()
            
            for (coord in forma) {
                val nuevaFila = filaActual + coord.fila + deltaFila
                val nuevaColumna = columnaActual + coord.columna + deltaColumna
                
                if (nuevaFila < 0 || nuevaFila >= FILAS_TABLERO) return false
                if (nuevaColumna < 0 || nuevaColumna >= COLUMNAS_TABLERO) return false
                if (tableroOcupado[nuevaFila][nuevaColumna]) return false
            }
            return true
        }
        return false
    }
    
    private fun moverIzquierda() {
        if (puedeMover(0, -1)) {
            columnaActual--
            actualizarPosicionPieza()
        }
    }
    
    private fun moverDerecha() {
        if (puedeMover(0, 1)) {
            columnaActual++
            actualizarPosicionPieza()
        }
    }
    
    private fun rotarPieza() {
        piezaActual?.let { pieza ->
            val rotacionAnterior = pieza.rotacion
            pieza.rotar()
            
            if (!puedeMover(0, 0)) {
                var ajustado = false
                
                if (puedeMover(0, -1)) {
                    columnaActual--
                    ajustado = true
                }
                else if (puedeMover(0, 1)) {
                    columnaActual++
                    ajustado = true
                }
                else if (puedeMover(0, -2)) {
                    columnaActual -= 2
                    ajustado = true
                }
                
                if (!ajustado) {
                    pieza.rotacion = rotacionAnterior
                    return
                }
            }
            
            actualizarPosicionPieza()
        }
    }
    
    private fun acelerarCaida() {
        var bloquesCaidos = 0
        while (puedeCaer()) {
            filaActual++
            bloquesCaidos++
        }
        
        if (bloquesCaidos > 0) {
            puntuacion += bloquesCaidos
            actualizarUI()
        }
        
        actualizarPosicionPieza()
        
        handler.removeCallbacks(caerRunnable)
        onPiezaAterrizó()
    }
    
    private fun actualizarPosicionPieza() {
        piezaActual?.let { pieza ->
            val forma = pieza.obtenerForma()
            
            forma.forEachIndexed { index, coord ->
                if (index < bloquesPiezaActual.size) {
                    val bloque = bloquesPiezaActual[index]
                    val params = bloque.layoutParams as FrameLayout.LayoutParams
                    params.leftMargin = (columnaActual + coord.columna) * tamanoPixel
                    params.topMargin = (filaActual + coord.fila) * tamanoPixel
                    bloque.layoutParams = params
                }
            }
        }
    }

    private fun onPiezaAterrizó() {
        piezaActual?.let { pieza ->
            val forma = pieza.obtenerForma()
            
            forma.forEachIndexed { index, coord ->
                val fila = filaActual + coord.fila
                val columna = columnaActual + coord.columna
                
                if (fila >= 0 && fila < FILAS_TABLERO && 
                    columna >= 0 && columna < COLUMNAS_TABLERO) {
                    
                    tableroOcupado[fila][columna] = true
                    
                    if (index < bloquesPiezaActual.size) {
                        bloquesEnTablero[Pair(fila, columna)] = bloquesPiezaActual[index]
                    }
                }
            }
        }
        
        bloquesPiezaActual.clear()
        
        verificarLineasCompletas()
        
        if (tableroOcupado[0].any { it } || tableroOcupado[1].any { it }) {
            gameOver()
            return
        }
        
        handler.postDelayed({
            crearNuevaPieza()
        }, 300)
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
            
            lineasEliminadas += lineasAEliminar.size
            
            puntuacion += when (lineasAEliminar.size) {
                1 -> 100 * nivel
                2 -> 300 * nivel
                3 -> 500 * nivel
                4 -> 800 * nivel
                else -> lineasAEliminar.size * 100 * nivel
            }
            
            verificarSubidaNivel()
            
            actualizarUI()
        }
    }
    
    private fun verificarSubidaNivel() {
        val nuevoNivel = (lineasEliminadas / LINEAS_POR_NIVEL) + 1
        
        if (nuevoNivel > nivel) {
            nivel = nuevoNivel
            
            velocidadCaida = (500L - ((nivel - 1) * REDUCCION_VELOCIDAD_POR_NIVEL))
                .coerceAtLeast(VELOCIDAD_MINIMA)
            
            runOnUiThread {
                scoreTextView.text = "🎉 ¡NIVEL $nivel! 🎉"
                handler.postDelayed({
                    actualizarUI()
                }, 1500)
            }
        }
    }
    
    private fun actualizarUI() {
        scoreTextView.text = "Puntaje: $puntuacion"
        levelTextView.text = "Nivel: $nivel"
        linesTextView.text = "Líneas: $lineasEliminadas"
    }
    
    private fun eliminarLineas(lineas: List<Int>) {
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
            scoreTextView.text = "GAME OVER!"
            levelTextView.text = "Puntaje Final: $puntuacion"
            linesTextView.text = "Nivel Alcanzado: $nivel"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(caerRunnable)
    }
}