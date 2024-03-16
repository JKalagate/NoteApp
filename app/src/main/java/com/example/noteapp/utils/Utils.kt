package com.example.noteapp.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Utils {
    private val colors = listOf(
        Color(0xFFffffff),
        Color(0xFFF44336),
        Color(0xFFE91E63),
        Color(0xFF9C27B0),
        Color(0xFF3F51B5),
        Color(0xFF2180F3),
        Color(0xFF03A9F4),
        Color(0xFF00BCD4),
        Color(0xFF4CAF50),
        Color(0xFF8BC34A),
        Color(0xFFCDDC39),
        Color(0xFFFFEB3B),
        Color(0xFFFFC107),
        Color(0xFFFF9800),
    )

    private fun lightenColor(color: Color, amount: Float): Color {
        return Color(
            red = (color.red + amount).coerceIn(0f, 1f),
            green = (color.green + amount).coerceIn(0f, 1f),
            blue = (color.blue + amount).coerceIn(0f, 1f),
            alpha = color.alpha
        )
    }

    val gradients: List<Brush>
        get() {
            return colors.map { color ->
                Brush.linearGradient(listOf(color, lightenColor(color, 0.5f)))
            }
        }
}