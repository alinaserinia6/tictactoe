package com.example.app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.app.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    enum class Turn {
        NOUGHT,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = firstTurn
    private val timerDuration = 30000L
    private lateinit var binding: ActivityMainBinding
    private var boardList = mutableListOf<Button>()
    private val bigSize = 60F

    private val timer = object : CountDownTimer(timerDuration, 1000) {
        override fun onTick(millisUntillFinished: Long) {
            val secondsReaming = millisUntillFinished / 1000
            binding.result.text = secondsReaming.toString()
        }

        override fun onFinish() {
            result("Draw")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            AppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                    Text(
//                        text = "In the name of God",
//                        modifier = Modifier
//                    )
//                }
//            }
//        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.gameName)
        val drawerLayout: DrawerLayout = binding.drawer
        val actionBar = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawerOpen,
            R.string.drawerClose
        )
        drawerLayout.setDrawerListener(actionBar)
        actionBar.syncState()
        initBoard()
        startSeconds()
    }


    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    private fun startSeconds() {
        timer.start()
    }

    fun boardTapped(view: View) {
        if (view !is Button)
            return
        addToBoard(view)
        if (checkResult(NOUGHT)) {
            result("O win")
        } else if (checkResult(CROSS)) {
            result("X win")
        } else if (fullBoard()) {
            result("Draw")
        }
    }

//    fun switchThemeTapped(view: View) {
//        // TODO("WHAT THE HELL")
//    }

    @SuppressLint("SetTextI18n")
    fun restartTapped(view: View) {
        if (view !is ImageButton)
            return
        for (x in boardList) {
            x.text = ""
            x.textSize = 40F
            x.isEnabled = true
            x.isClickable = true
        }
        timer.cancel()
        timer.start()
        currentTurn = firstTurn
        if (firstTurn == Turn.CROSS) {
            binding.turnTV.text = "Turn $CROSS"
        } else {
            binding.turnTV.text = "Turn $NOUGHT"
        }
    }

    private fun checkResult(person: String): Boolean {
        if (binding.a1.text.equals(person) && binding.a2.text.equals(person) && binding.a3.text.equals(
                person
            )
        ) {
            binding.a1.textSize = bigSize
            binding.a2.textSize = bigSize
            binding.a3.textSize = bigSize
            return true
        }
        if (binding.b1.text.equals(person) && binding.b2.text.equals(person) && binding.b3.text.equals(
                person
            )
        ) {
            binding.b1.textSize = bigSize
            binding.b2.textSize = bigSize
            binding.b3.textSize = bigSize
            return true
        }
        if (binding.c1.text.equals(person) && binding.c2.text.equals(person) && binding.c3.text.equals(
                person
            )
        ) {
            binding.c1.textSize = bigSize
            binding.c2.textSize = bigSize
            binding.c3.textSize = bigSize
            return true
        }
        if (binding.a1.text.equals(person) && binding.b1.text.equals(person) && binding.c1.text.equals(
                person
            )
        ) {
            binding.a1.textSize = bigSize
            binding.b1.textSize = bigSize
            binding.c1.textSize = bigSize
            return true
        }
        if (binding.a2.text.equals(person) && binding.b2.text.equals(person) && binding.c2.text.equals(
                person
            )
        ) {
            binding.a2.textSize = bigSize
            binding.b2.textSize = bigSize
            binding.c2.textSize = bigSize
            return true
        }
        if (binding.a3.text.equals(person) && binding.b3.text.equals(person) && binding.c3.text.equals(
                person
            )
        ) {
            binding.a3.textSize = bigSize
            binding.b3.textSize = bigSize
            binding.c3.textSize = bigSize
            return true
        }
        if (binding.a1.text.equals(person) && binding.b2.text.equals(person) && binding.c3.text.equals(
                person
            )
        ) {
            binding.a1.textSize = bigSize
            binding.b2.textSize = bigSize
            binding.c3.textSize = bigSize
            return true
        }
        if (binding.a3.text.equals(person) && binding.b2.text.equals(person) && binding.c1.text.equals(
                person
            )
        ) {
            binding.a3.textSize = bigSize
            binding.b2.textSize = bigSize
            binding.c1.textSize = bigSize
            return true
        }
        return false
    }

    private fun result(title: String) {
        timer.cancel()
        binding.result.text = title
        for (x in boardList) {
            x.isEnabled = false
            x.isClickable = false
        }
        binding.turnTV.text = ""
    }

    private fun fullBoard(): Boolean {
        for (button in boardList)
            if (button.text == "")
                return false
        return true
    }

    private fun addToBoard(button: Button) {
        if (button.text != "")
            return
        if (currentTurn == Turn.NOUGHT) {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
            button.setTextColor(Color.parseColor("#f79817"))
        } else if (currentTurn == Turn.CROSS) {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
            button.setTextColor(Color.parseColor("#FF000000"))
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if (currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if (currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"
        binding.turnTV.text = turnText
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }


    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "GoodBye $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AppTheme {
//        Greeting("Android")
//    }
//}