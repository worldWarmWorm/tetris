package com.example.tetris.enums

import android.graphics.Color

enum class BlockColor(private val rgbValue: Int, private val byteValue: Byte) {
	PINK(
		Color.rgb(205, 105, 180),
		2
	),
	GREEN(
		Color.rgb(0, 128, 0),
		3
	),
	ORANGE(
		Color.rgb(255, 140, 0),
		4
	),
	YELLOW(
		Color.rgb(255, 255, 0),
		5
	),
	CYAN(
		Color.rgb(0, 255, 255),
		6
	);

	fun getRgbValue(): Int {
		return this.rgbValue
	}

	fun getByteValue(): Byte {
		return this.byteValue
	}
}