package il.co.galex.namethatcolor.core.model

import il.co.galex.namethatcolor.core.util.hsl
import il.co.galex.namethatcolor.core.util.rgb
import il.co.galex.namethatcolor.core.util.roundTo2Decimal

class HexColor(val input: String) {

    private var alpha: String? = null
    val value: String

    init {
        var cup = input.toUpperCase()

        if (cup.isEmpty()) {
            throw IllegalArgumentException("The input cannot be an empty string")
        }

        if (cup.startsWith("#")) {
            cup = cup.substringAfter("#")
        }

        when (cup.length) {
            3 -> {
                this.value = cup[0] + cup[0] + cup[1] + cup[1] + cup[2] + cup[2]
            }

            4 -> {
                this.value = cup[1] + cup[1] + cup[2] + cup[2] + cup[3] + cup[3]
                this.alpha = cup[0] + cup[0]
            }

            6 -> {
                this.value = cup
            }

            8 -> {
                this.value = cup.substring(2)
                this.alpha = cup.substring(0, 2)
            }

            else -> throw IllegalArgumentException("Length is weird")
        }
        // if we survived till here, let's now check the format of the value itself
        if (!VALUE_REGEX.matches(value)) {
            throw IllegalArgumentException("The value $value is not of a correct format")
        }

        alpha?.let {
            if (!ALPHA_REGEX.matches(it)) {
                throw IllegalArgumentException("The alpha $alpha is not of a correct format")
            }
        }
    }

    fun rgb() = value.rgb()
    fun hsl() = value.hsl()

    fun percentAlpha(): Int? = alpha?.run { (toInt(16) / 255.0).roundTo2Decimal() }

    override fun toString(): String = PREFIX + if (alpha == null) value else "$alpha$value"

    fun inputToString() = if (input.startsWith("#")) input else "$PREFIX$input"

    companion object {
        private const val PREFIX = "#"
        private val VALUE_REGEX = Regex("[0-9a-fA-F]{6}$")
        private val ALPHA_REGEX = Regex("[0-9a-fA-F]{2}$")
    }

    private operator fun Char.plus(c: Char): String {
        return "$this$c"
    }
}


