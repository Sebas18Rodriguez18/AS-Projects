package com.example.mytetris

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var piezaActual: View? = null
    private var filaActual = 0
    private val handler = Handler(Looper.getMainLooper())

    private var tamanoPixel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val densidad = resources.displayMetrics.density
        tamanoPixel = (30 * densidad).toInt()

        crearPrimerBloque()
    }

    private fun crearPrimerBloque() {
        val gameBoard = findViewById<FrameLayout>(R.id.game_board)

        val bloque = View(this)

        val params = FrameLayout.LayoutParams(tamanoPixel, tamanoPixel)
        params.leftMargin = tamanoPixel * 4
        params.topMargin = tamanoPixel * 0

        bloque.setBackgroundResource(R.drawable.bloque_base)
        bloque.layoutParams = params

        gameBoard.addView(bloque)

        piezaActual = bloque
        filaActual = 0
    }

    private val caerRunnable = object : Runnable {
        override fun run() {
            filaActual++

            val params = piezaActual?.layoutParams as? FrameLayout.LayoutParams
            if (params != null) {
                params.topMargin = filaActual * tamanoPixel
                piezaActual?.layoutParams = params
            }

            handler.postDelayed(this, 500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(caerRunnable)
    }
}