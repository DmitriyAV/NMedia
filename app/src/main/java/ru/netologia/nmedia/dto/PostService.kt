package ru.netologia.nmedia.dto

import java.math.RoundingMode
import java.text.DecimalFormat

object PostService {

    fun checkCounter(counter: Int): String {
        return when(counter) {
            in 0..999 -> "$counter"

            in 1000..1099 -> "1k"
            in 1100..1999 -> division(counter.toDouble()) + "k"

            in 2000..2099 -> "2k"
            in 2100..2999 -> division(counter.toDouble()) + "k"

            in 3000..3099 -> "3k"
            in 3100..3999 -> division(counter.toDouble()) + "k"

            in 4000..4099 -> "4k"
            in 4100..4999 -> division(counter.toDouble()) + "k"

            in 5000..5099 -> "5k"
            in 5100..5999 -> division(counter.toDouble()) + "k"

            in 6000..6099 -> "6k"
            in 6100..6999 -> division(counter.toDouble()) + "k"

            in 7000..7099 -> "7k"
            in 7100..7999 -> division(counter.toDouble()) + "k"

            in 8000..8099 -> "8k"
            in 8100..8999 -> division(counter.toDouble()) + "k"

            in 9000..9099 -> "9k"
            in 9000..9999 -> division(counter.toDouble()) + "k"

            in 10000..1000000 -> "${counter/100}" + "k"
            else -> "${counter/1000}" + "m"
        }
    }

    private fun division(counter: Double): String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(counter/1000).toString()
    }
}