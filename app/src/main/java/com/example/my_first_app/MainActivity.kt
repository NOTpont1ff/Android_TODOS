package com.example.my_first_app

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var backgroundVideoView: VideoView
    private lateinit var logoImageView: ImageView
    private lateinit var todoListView: ListView
    private lateinit var inputEditText: EditText
    private lateinit var addButton: ImageButton
    private lateinit var clearButton: ImageButton
    private lateinit var dateTextView: TextView

    private val todos: MutableList<String> = mutableListOf()
    private lateinit var todoListAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUIComponents()
        setupBackgroundVideo()
        setupAddButton()
        setupClearButton()
        updateDate()
    }

    private fun initializeUIComponents() {
        backgroundVideoView = findViewById(R.id.videoViewBackground)
        logoImageView = findViewById(R.id.LogoGif)
        todoListView = findViewById(R.id.listView)
        inputEditText = findViewById(R.id.user_data)
        addButton = findViewById(R.id.button)
        clearButton = findViewById(R.id.imageButton3)
        dateTextView = findViewById(R.id.date)

        todoListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todos)
        todoListView.adapter = todoListAdapter
    }

    private fun setupBackgroundVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/raw/background_video")
        backgroundVideoView.setVideoURI(videoUri)
        backgroundVideoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.apply {
                setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                isLooping = true
            }
        }
        backgroundVideoView.start()
    }

    private fun setupAddButton() {
        addButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    addButton.setImageResource(R.drawable.todosbutton5)
                    Log.d("MainActivity", "Add button pressed")
                }
                MotionEvent.ACTION_UP -> {
                    addButton.setImageResource(R.drawable.todosbutton4)
                    val inputText = inputEditText.text.toString().trim()
                    if (inputText.isNotEmpty()) {
                        addTodoItem(inputText)
                    } else {
                        Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }
    }

    private fun setupClearButton() {
        clearButton.setOnClickListener {
            Log.d("MainActivity", "Clear button pressed")
            clearTodoList()
        }
    }

    private fun addTodoItem(todo: String) {
        todos.add(0, todo)
        todoListAdapter.notifyDataSetChanged()
        inputEditText.text.clear()
        Log.d("MainActivity", "Added todo: $todo")
    }

    private fun clearTodoList() {
        todos.clear()
        todoListAdapter.notifyDataSetChanged()
        Log.d("MainActivity", "Todo list cleared")
    }

    private fun updateDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        dateTextView.text = currentDate
        Log.d("MainActivity", "Current date updated: $currentDate")
    }

    override fun onResume() {
        super.onResume()
        backgroundVideoView.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundVideoView.pause()
    }
}
