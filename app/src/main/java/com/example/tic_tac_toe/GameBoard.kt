package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class GameBoard : AppCompatActivity(), View.OnClickListener {
    // Current Player status
    // Shows which player is currently is going for its move
    // Initially playerX will start the game
    private var playerX = false
    private var player0 = true

    // Player Names
    private lateinit var playerXName: String
    private lateinit var player0Name: String

    // Count of Records of each players win
    private var playerXWinCount = 0
    private var player0WinCount = 0


    // Total Number of moves performed on the board
    private var totalMoves = 0


    // Logic Board
    private var boardStatus = Array(3){IntArray(3)}

    // Display board
    private lateinit var board : Array<Array<Button>>



    // @SuppressLint("SetTextI18n")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_board)

        // Getting ID's of all the buttons of the board -----------------------------------------------------------
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val resetButton = findViewById<Button>(R.id.reset)
        val home = findViewById<Button>(R.id.home)
        val resetScore = findViewById<Button>(R.id.resetScore)
        // ----------------------------------------------------------------------------------------------------------

        // Text View IDs
        // Getting the names of the players from the home page
        val player1 = intent.getStringExtra("Player1")
        val player2 = intent.getStringExtra("Player2")


        // Displaying the player names on to the screen
        val player1TextView = findViewById<TextView>(R.id.tv1)
        val player2TextView = findViewById<TextView>(R.id.tv2)

        player1TextView.text = "$player1: "
        player2TextView.text = "$player2: "

        playerXName = player1.toString()
        player0Name = player2.toString()


        // Current Player Text
        val currentPlayerText = findViewById<TextView>(R.id.mainTextView)
        currentPlayerText.text = playerXName


        // board Processing -----------------------------------------------------------------

        board = arrayOf(
            arrayOf(button1,button2,button3),
            arrayOf(button4,button5,button6),
            arrayOf(button7,button8,button9)
        )
        // Initializing the logic board
        for(row in 0..2){
            for(col in 0..2){
                boardStatus[row][col] = -1
            }
        }

        // Setting the Onclick Listener on grid buttons
        for(i in board){
            for(button in i){
                button.setOnClickListener(this)
            }
        }

        // Reset Board Button
        // Reset the board without changing the score
        resetButton.setOnClickListener {
            totalMoves = 0
            initializeBoard()
        }

        // Home button
        // Take back to the home page of the game
        home.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        // Reset Score Button
        // Resets the players score as wel as the board
        resetScore.setOnClickListener {
            playerXWinCount = 0
            player0WinCount = 0
            totalMoves = 0
            updateScores(playerXName)
            updateScores(player0Name)
            initializeBoard()
        }

    }

    // InitializeBoard Method initializes the board
    private fun initializeBoard() {
        for(row in 0..2){
            for(col in 0..2){
                board[row][col].isEnabled = true
                board[row][col].text = ""
                boardStatus[row][col] = -1
            }
        }
    }

    // OnClick method will be called when
    // the grid buttons are pressed (board keys)
    override fun onClick(view: View) {

        // Changing the state of player after each button press
        // at each press on Player state becomes true and others state becomes false
        // the player having the true state will perform its move
        playerX = !playerX
        player0 = !player0

        // Updating the current Player text
        val boardText = if (!playerX){
            playerXName
        } else{
            player0Name
        }
        val currentPlayerText = findViewById<TextView>(R.id.mainTextView)
        currentPlayerText.text = boardText


        // Getting the Button id to perform the task on
        // On Each Grid button press the updateValue method would be called
        // which will update the grid text as "X" or "0" according to the player
        when(view.id){
            R.id.button1 -> updateValue(0,0)
            R.id.button2 -> updateValue(0,1)
            R.id.button3 -> updateValue(0,2)
            R.id.button4 -> updateValue(1,0)
            R.id.button5 -> updateValue(1,1)
            R.id.button6 -> updateValue(1,2)
            R.id.button7 -> updateValue(2,0)
            R.id.button8 -> updateValue(2,1)
            R.id.button9 -> updateValue(2,2)
        }

        // Increment Total Moves count after each button press and check the
        // Winning status that if any player has won or the game has drawn
        totalMoves += 1
        checkWinStatus()
    }

    // updateValueMethod will update the value at board[row][col] as "X" or "0"
    private fun updateValue(row: Int, col: Int) {
        val currentVal: Int
        val boardText: String
        if (playerX){
            boardText = "X"
            currentVal = 1
        } else{
            boardText = "0"
            currentVal = 0
        }
        board[row][col].text = boardText
        board[row][col].isEnabled = false

        boardStatus[row][col] = currentVal
    }


    // CheckWinStatus Method
    // Will check if any player as won
    private fun checkWinStatus() {
        // The Method will check in all possible direction for the win or draw
        // For each outcome a Toast Message would be displayed

        // Diagonal Check
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if(boardStatus[0][0] == 1){
                disableButton()
                popOutToast(boardStatus[0][0])
            }
            else if(boardStatus[0][0] == 0){
                disableButton()
                popOutToast(boardStatus[0][0])
            }
            return
        }

        // Anti Diagonal Check
        else if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0])  {
            if(boardStatus[0][2] == 1){
                disableButton()
                popOutToast(boardStatus[0][2])
            }
            else if(boardStatus[0][2] == 0){
                disableButton()
                popOutToast(boardStatus[0][2])
            }
            return
        }


        // Row Check
        for(i in 0..2){
            if(boardStatus[i][0] == boardStatus[i][1]  && boardStatus[i][0] == boardStatus[i][2]){
                if(boardStatus[i][0] == 1){
                    disableButton()
                    popOutToast(boardStatus[i][0])
                }else if(boardStatus[i][0] == 0){
                    disableButton()
                    popOutToast(boardStatus[i][0])
                }
                return
            }
        }


        // Column Check
        for(i in 0..2){
            if(boardStatus[0][i] == boardStatus[1][i]  && boardStatus[0][i] == boardStatus[2][i]){
                if(boardStatus[0][i] == 1){
                    disableButton()
                    popOutToast(boardStatus[0][i])

                }else if(boardStatus[0][i] == 0){
                    disableButton()
                    popOutToast(boardStatus[0][i])
                }
                return
            }
        }

        // If all boxes filled and No outcome i.e totalMoves has become 9 and no winner is there
        if(totalMoves >= 9){
            Toast.makeText(this,"Game draw please reset the board",Toast.LENGTH_SHORT).show()
        }
    }


    // The Method Displays a toast message for the win or draw case
    // called inside the checkWinStatus method
    private fun popOutToast(playerValue: Int) {
        val currWinner: String
        if(playerValue == 1) {
            playerXWinCount += 1
            currWinner = playerXName
        }
        else{
            player0WinCount += 1
            currWinner = player0Name
        }
        updateScores(currWinner)
        Toast.makeText(this, "$currWinner wins the game", Toast.LENGTH_SHORT).show()

    }

    // Will Update the player score after each game
    private fun updateScores(currWinner: String) {
        val score1 = findViewById<TextView>(R.id.score1tv)
        val score2 = findViewById<TextView>(R.id.score2tv)
        if(currWinner == playerXName){
            score1.text = playerXWinCount.toString()
        }
        else if(currWinner == player0Name){
            score2.text = player0WinCount.toString()
        }
    }

    // If anyone has won the game this method will disable
    // the remaining empty grid cells buttons
    private fun disableButton() {
        for(i in board){
            for(button in i){
                button.isEnabled = false
            }
        }
    }


}