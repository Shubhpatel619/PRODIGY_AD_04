package com.example.tictactoe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var tvStatus: TextView
    private lateinit var btnReset: Button

    private var playerTurn = true // true for Player X, false for Player O
    private var roundCount = 0

    private var boardStatus = Array(3) { IntArray(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        btnReset = findViewById(R.id.btnReset)

        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }

        btnReset.setOnClickListener {
            resetBoard()
        }

        resetBoard()
    }

    private fun initButton(row: Int, col: Int): Button {
        val btn: Button = findViewById(resources.getIdentifier("btn$row$col", "id", packageName))
        btn.setOnClickListener {
            onButtonClick(btn, row, col)
        }
        return btn
    }

    private fun onButtonClick(btn: Button, row: Int, col: Int) {
        if (btn.text.isNotEmpty()) {
            return
        }

        if (playerTurn) {
            btn.text = "X"
            boardStatus[row][col] = 1
        } else {
            btn.text = "O"
            boardStatus[row][col] = 2
        }

        roundCount++

        if (checkForWin()) {
            if (playerTurn) {
                updateStatus("Player X wins!")
            } else {
                updateStatus("Player O wins!")
            }
        } else if (roundCount == 9) {
            updateStatus("It's a draw!")
        } else {
            playerTurn = !playerTurn
            if (playerTurn) {
                updateStatus("Player X's turn")
            } else {
                updateStatus("Player O's turn")
            }
        }
    }

    private fun checkForWin(): Boolean {
        // Check rows
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2] && boardStatus[i][0] != 0) {
                return true
            }
        }

        // Check columns
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i] && boardStatus[0][i] != 0) {
                return true
            }
        }

        // Check diagonals
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2] && boardStatus[0][0] != 0) {
            return true
        }

        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0] && boardStatus[0][2] != 0) {
            return true
        }

        return false
    }

    private fun updateStatus(text: String) {
        tvStatus.text = text
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = 0
                buttons[i][j].text = ""
            }
        }
        roundCount = 0
        playerTurn = true
        updateStatus("Player X's turn")
    }
}
