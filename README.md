# 🎮 My Tetris - Android Game

<p align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
  <img src="https://img.shields.io/badge/Material_Design-757575?style=for-the-badge&logo=material-design&logoColor=white" alt="Material Design">
</p>

<p align="center">
  <strong>Una implementación completa del clásico juego Tetris para Android con controles táctiles intuitivos y sistema de niveles progresivo</strong>
</p>

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Instalación](#-instalación)
- [Cómo Jugar](#-cómo-jugar)
- [Sistema de Puntuación](#-sistema-de-puntuación)
- [Contribuir](#-contribuir)
- [Licencia](#-licencia)
- [Contacto](#-connect-with-me)

---

## ✨ Características

### 🎯 Jugabilidad
- ✅ **7 Piezas Clásicas de Tetris**: I, O, T, S, Z, J, L con colores únicos
- ✅ **Rotación Completa**: Sistema de rotación con 4 orientaciones y wall-kick automático
- ✅ **Controles Táctiles Intuitivos**: Desliza para mover, tap para rotar, desliza abajo para caída rápida
- ✅ **Detección de Colisiones**: Motor físico preciso para movimiento y aterrizaje de piezas
- ✅ **Eliminación de Líneas**: Detección automática de líneas completas con animación de caída

### 📊 Sistema de Progresión
- ✅ **Sistema de Niveles Progresivo**: Cada 10 líneas eliminadas aumenta el nivel
- ✅ **Velocidad Dinámica**: La velocidad aumenta gradualmente con cada nivel
- ✅ **Puntuación Multiplicada**: Los puntos se multiplican por el nivel actual
- ✅ **Bonus por Caída Rápida**: Puntos extra por usar drop rápido
- ✅ **Estadísticas en Tiempo Real**: Puntaje, nivel y líneas eliminadas siempre visibles

### 🎨 Interfaz de Usuario
- ✅ **Diseño Material 3**: UI moderna con tema oscuro
- ✅ **Panel de Estadísticas**: Información clara del progreso del jugador
- ✅ **Colores Distintivos**: Cada pieza tiene su color único para fácil identificación
- ✅ **Feedback Visual**: Notificaciones al subir de nivel

---

## 📥 Instalación

### Para Usuarios

1. **Descargar el APK**
   ```bash
   # Descarga la última versión desde Releases
   https://github.com/Sebas18Rodriguez18/AS-Projects/releases
   ```

2. **Instalar en tu dispositivo**
   - Habilita "Fuentes desconocidas" en Configuración → Seguridad
   - Abre el archivo APK descargado
   - Toca "Instalar"
   - Disfruta del juego

### Para Desarrolladores

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Sebas18Rodriguez18/AS-Projects.git
   cd AS-Projects
   ```

2. **Abrir en Android Studio**
   ```bash
   # Opción 1: Desde la terminal
   studio .
   
   # Opción 2: File → Open → Selecciona la carpeta del proyecto
   ```

3. **Sincronizar dependencias**
   ```bash
   # Gradle sincronizará automáticamente
   # O manualmente: Tools → Android → Sync Project with Gradle Files
   ```

4. **Compilar y ejecutar**
   ```bash
   # Opción 1: Desde Android Studio
   Run → Run 'app' (Shift + F10)
   
   # Opción 2: Desde terminal
   ./gradlew installDebug
   ```

---

## 🎮 Cómo Jugar

### Controles

| Acción | Gesto | Descripción |
|--------|-------|-------------|
| **Mover Izquierda** | ⬅️ Deslizar hacia la izquierda | Mueve la pieza una columna a la izquierda |
| **Mover Derecha** | ➡️ Deslizar hacia la derecha | Mueve la pieza una columna a la derecha |
| **Rotar** | 👆 Tap/Toque simple | Rota la pieza 90° en sentido horario |
| **Caída Rápida** | ⬇️ Deslizar hacia abajo | La pieza cae instantáneamente hasta el fondo |

### Objetivo del Juego

1. **Completar Líneas Horizontales**: Llena completamente una fila para eliminarla
2. **Evitar Llegar Arriba**: Si las piezas alcanzan la parte superior, es Game Over
3. **Maximizar Puntos**: Elimina múltiples líneas simultáneamente para bonus
4. **Subir de Nivel**: Cada 10 líneas eliminadas aumenta la dificultad y los puntos

### Estrategias

- 🎯 **Tetris (4 líneas)**: Intenta hacer "Tetris" eliminando 4 líneas simultáneamente para 800 puntos × nivel
- 🏗️ **Construcción Eficiente**: Evita dejar huecos que dificulten futuras jugadas
- ⚡ **Uso del Drop**: Usa la caída rápida cuando tengas clara la posición final
- 🔄 **Rotación Anticipada**: Rota la pieza antes de que llegue a su posición para ganar tiempo

---

## 🏗️ Arquitectura del Proyecto

```
AS-Projects/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/mytetris/
│   │   │   │   ├── MainActivity.kt          # Actividad principal y lógica del juego
│   │   │   │   └── Pieza.kt                 # Modelo de datos de las piezas
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml    # Layout de la UI
│   │   │   │   ├── drawable/
│   │   │   │   │   └── bloque_base.xml      # Diseño de bloques
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── mipmap/                  # Iconos de la app
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   └── test/                            # Tests unitarios
│   │
│   └── build.gradle.kts                     # Configuración del módulo
│
├── gradle/
│   └── libs.versions.toml                   # Catálogo de versiones
│
├── build.gradle.kts                         # Configuración raíz
├── settings.gradle.kts
├── gradle.properties
├── LICENSE
└── README.md
```

### Componentes Principales

#### 📄 MainActivity.kt
**Responsabilidades:**
- Gestión del ciclo de vida del juego
- Renderizado de piezas en el tablero
- Manejo de controles táctiles (GestureDetector)
- Sistema de colisiones y física
- Lógica de eliminación de líneas
- Sistema de puntuación y niveles
- Actualización de UI

**Propiedades Clave:**
```kotlin
- piezaActual: Pieza?                         // Pieza que está cayendo
- bloquesPiezaActual: MutableList<View>       // Views visuales de la pieza
- filaActual, columnaActual: Int              // Posición en el tablero
- tableroOcupado: Array<BooleanArray>         // Matriz de estado del tablero
- bloquesEnTablero: MutableMap<Pair, View>    // Mapeo de bloques fijos
- nivel, lineasEliminadas, puntuacion: Int    // Estado del juego
- velocidadCaida: Long                        // Velocidad actual en ms
```

#### 📄 Pieza.kt
**Responsabilidades:**
- Definición de tipos de piezas (enum TipoPieza)
- Gestión de coordenadas (data class Coordenada)
- Generación de formas según rotación
- Asignación de colores únicos
- Lógica de rotación

**Métodos Principales:**
```kotlin
- obtenerForma(): List<Coordenada>     // Devuelve la forma actual
- rotar()                              // Incrementa rotación
- obtenerFormaI/O/T/S/Z/J/L()         // Formas específicas por tipo
```

---

## 🛠️ Tecnologías Utilizadas

### Core
- **Lenguaje**: Kotlin 2.1.0
- **Plataforma**: Android SDK 24-36
- **Build System**: Gradle 8.13 con Kotlin DSL

### UI/UX
- **Layout**: ConstraintLayout 2.2.1
- **Theme**: Material Design 3 (Material 1.13.0)
- **Views**: Android View System tradicional
- **Gestures**: GestureDetectorCompat

### Dependencias Principales

```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.activity:activity:1.9.3")
}
```

---

## 🏆 Sistema de Puntuación

### Puntos Base por Líneas Eliminadas

| Líneas | Nombre | Puntos Base | Puntos × Nivel 3 |
|--------|--------|-------------|------------------|
| 1 línea | Single | 100 | 300 |
| 2 líneas | Double | 300 | 900 |
| 3 líneas | Triple | 500 | 1,500 |
| 4 líneas | **Tetris** | 800 | 2,400 |

### Fórmula de Puntuación
```
Puntos Finales = Puntos Base × Nivel Actual
```

### Bonus Adicionales
- **Drop Rápido**: +1 punto por cada fila que la pieza cae instantáneamente
- **Ejemplo**: Drop de 10 filas = +10 puntos extra

### Progresión de Niveles

```
Nivel = (Líneas Eliminadas ÷ 10) + 1
```

| Nivel | Líneas Requeridas | Velocidad (ms) |
|-------|-------------------|----------------|
| 1 | 0-9 | 500 |
| 2 | 10-19 | 460 |
| 3 | 20-29 | 420 |
| 4 | 30-39 | 380 |
| 5 | 40-49 | 340 |
| 10 | 90-99 | 140 |
| 11+ | 100+ | 100 (mínimo) |

**Fórmula de Velocidad:**
```kotlin
velocidadCaida = (500 - ((nivel - 1) × 40)).coerceAtLeast(100)
```

---

## ⚙️ Mecánicas del Juego

### 1. Generación de Piezas
- **Aleatoria**: Cada pieza se selecciona aleatoriamente de los 7 tipos
- **Posición Inicial**: Aparece en la fila 0, columna 3 (parte superior centro)
- **Rotación Inicial**: Siempre comienza en rotación 0

### 2. Sistema de Colisiones

#### Detección de Colisión Vertical (Caída)
```kotlin
fun puedeCaer(): Boolean {
    // Verifica para cada bloque de la pieza:
    // - No sobrepasa el límite inferior (fila 20)
    // - No colisiona con bloques ya fijados
    // - Está dentro de los límites horizontales
}
```

#### Detección de Colisión Horizontal (Movimiento)
```kotlin
fun puedeMover(deltaFila: Int, deltaColumna: Int): Boolean {
    // Valida que el movimiento no cause:
    // - Salida del tablero (columnas 0-9)
    // - Colisión con bloques fijos
}
```

### 3. Sistema de Rotación

#### Wall Kick (Ajuste de Pared)
Cuando una pieza no puede rotar en su posición actual, el sistema intenta automáticamente:

1. **Ajuste izquierda** (-1 columna)
2. **Ajuste derecha** (+1 columna)
3. **Ajuste doble izquierda** (-2 columnas) - Para pieza I

Si ningún ajuste funciona, la rotación se cancela.

### 4. Eliminación de Líneas

#### Algoritmo de Verificación
```kotlin
1. Recorrer cada fila del tablero (0-19)
2. Si todas las columnas están ocupadas:
   - Agregar fila a lista de eliminación
3. Para cada línea eliminada:
   - Remover bloques visuales
   - Hacer caer bloques superiores
   - Actualizar matriz de estado
4. Calcular puntos según número de líneas
5. Verificar subida de nivel
```

### 5. Game Over

**Condiciones:**
- La nueva pieza no puede generarse porque el espacio está ocupado
- Se detecta cuando `tableroOcupado[0]` o `tableroOcupado[1]` tienen bloques

---

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Si deseas mejorar el proyecto:

### Proceso de Contribución

1. **Fork el repositorio**
2. **Crea una rama para tu feature**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Realiza tus cambios y commitea**
   ```bash
   git commit -m 'feat: add amazing feature'
   ```
4. **Push a tu fork**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Abre un Pull Request**

### Convenciones de Código

- **Kotlin Style Guide**: Seguir las convenciones oficiales de Kotlin
- **Comentarios**: Documentar funciones complejas
- **Commits**: Usar mensajes descriptivos (feat/fix/docs/refactor)
- **Testing**: Incluir tests para nuevas funcionalidades

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

## 📊 Estadísticas del Proyecto

```
📦 Tamaño del APK:     ~8 MB
⚡ Tiempo de carga:     < 2 segundos
🎯 FPS objetivo:        60 FPS
📱 Dispositivos:        Android 7.0+
🌍 Idioma:              Español (UI)
```

---

## 🙏 Agradecimientos

- **The Tetris Company**: Por el diseño original del juego
- **Material Design**: Por los componentes UI
- **Android Developers**: Por la documentación y herramientas
- **Comunidad de Kotlin**: Por el excelente lenguaje

---

## 📞 Connect with Me

<p align="center">
 <img src="https://github.com/Sebas18Rodriguez18/Sebas18Rodriguez18/blob/main/logo-page.png" width="210">
</p>

<p align="center">
  <a href="mailto:sr1290853@gmail.com" target="_blank">
    <img src="https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Email">
  </a>
  &nbsp;
  <a href="https://t.me/SebasDevCruz26" target="_blank">
    <img src="https://img.shields.io/badge/Telegram-26A5E4?style=for-the-badge&logo=telegram&logoColor=white" alt="Telegram">
  </a>
  &nbsp;
  <a href="https://www.instagram.com/025___sr/" target="_blank">
    <img src="https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white" alt="Instagram">
  </a>
  &nbsp;
  <a href="https://www.linkedin.com/in/sebastian-cruz-43b733343/" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn">
  </a>
  &nbsp;
  <a href="https://github.com/Sebas18Rodriguez18" target="_blank">
    <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub">
  </a>
</p>

<p align="center">
  <img src="https://media.giphy.com/media/jpVnC65DmYeyRL4LHS/giphy.gif" width="300">
</p>

---

<p align="center">
  <strong>⭐ Si te gusta este proyecto, dale una estrella en GitHub ⭐</strong>
</p>

<p align="center">
  Made with ❤️ by <a href="https://github.com/Sebas18Rodriguez18">SebasDevCruz</a>
</p>

<p align="center">
  <img src="https://img.shields.io/github/stars/Sebas18Rodriguez18/AS-Projects?style=social" alt="GitHub stars">
  <img src="https://img.shields.io/github/forks/Sebas18Rodriguez18/AS-Projects?style=social" alt="GitHub forks">
  <img src="https://img.shields.io/github/watchers/Sebas18Rodriguez18/AS-Projects?style=social" alt="GitHub watchers">
</p>
