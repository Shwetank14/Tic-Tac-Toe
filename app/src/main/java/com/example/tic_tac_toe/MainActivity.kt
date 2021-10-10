package com.example.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Getting the ID's of the start button and the edit text views
        val startGameButton = findViewById<Button>(R.id.btn1)
        val player1 = findViewById<EditText>(R.id.plyr1)
        val player2 = findViewById<EditText>(R.id.plyr2)


        startGameButton.setOnClickListener {

            // If the text entered in any of the edit texts spaces is empty the game will not start
            if(player1.text.toString().isEmpty() or player2.text.toString().isEmpty()){
                Toast.makeText(this,"Player Name Can't be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                // Taking the player1 and player2 name values entered by the user and
                // passing it to the GameBoard activity as a string
                val newIntent = Intent(this,GameBoard::class.java)
                newIntent.putExtra("Player1",player1.text.toString())
                newIntent.putExtra("Player2", player2.text.toString())
                startActivity(newIntent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}