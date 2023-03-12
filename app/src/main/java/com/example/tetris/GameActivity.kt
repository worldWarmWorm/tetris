package com.example.tetris

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tetris.enums.Motions
import com.example.tetris.models.AppModel
import com.example.tetris.storage.AppPreferences
import com.example.tetris.view.TetrisView

class GameActivity : AppCompatActivity() {
	var tvHighScore: TextView? = null
	var tvCurrentScore: TextView? = null
	var appPreferences: AppPreferences? = null
	private val appModel: AppModel = AppModel()
	private lateinit var tetrisView: TetrisView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_game)
		appPreferences = AppPreferences(this)
		appModel.setPreferences(appPreferences)

		val btnRestart = findViewById<Button>(R.id.btn_restart)
		tvHighScore = findViewById<TextView>(R.id.tv_high_score)
		tvCurrentScore = findViewById<TextView>(R.id.tv_current_score)
		tetrisView = findViewById<TetrisView>(R.id.view_tetris)
		tetrisView.setActivity(this)
		tetrisView.setModel(appModel)
		tetrisView.setOnTouchListener(this::onTetrisViewTouch)
		btnRestart.setOnClickListener(this::btnRestartClick)

		upadateHighScore()
		updateCurrentScore()
	}

	private fun btnRestartClick(view: View) {
		appModel.restartGame()
	}

	private fun onTetrisViewTouch(view: View, event: MotionEvent): Boolean {
		if (appModel.isGameOver() || appModel.isGameAwaitingStart()) {
			appModel.startGame()
			tetrisView.setGameCommandWithDelay(Motions.DOWN)
		} else if (appModel.isGameActive()) {
			when (resolveTouchDirection(view, event)) {
				0 -> moveTetromino(Motions.LEFT)
				1 -> moveTetromino(Motions.ROTATE)
				2 -> moveTetromino(Motions.DOWN)
				3 -> moveTetromino(Motions.RIGHT)
			}
		}

		return true
	}

	private fun resolveTouchDirection(view: View, event: MotionEvent): Int {
		val x = event.x / view.width
		val y = event.y / view.height

		return if (y > x) {
			if (x > 1 - y) 2 else 0
		} else {
			if (x > 1 - y) 3 else 1
		}
	}

	private fun moveTetromino(motion: Motions) {
		if (appModel.isGameActive()) {
			tetrisView.setGameCommand(motion)
		}
	}

	private fun upadateHighScore() {
		tvHighScore?.text = "${appPreferences?.getHighScore()}"
	}

	private fun updateCurrentScore() {
		tvCurrentScore?.text = "0"
	}
}