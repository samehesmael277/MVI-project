package com.sameh.mviapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvNumber: TextView
    private lateinit var btnAddNumber: Button

    private val addNumberViewModel: AddNumberViewModel by lazy {
        ViewModelProvider(this)[AddNumberViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvNumber = findViewById(R.id.tv_number)
        btnAddNumber = findViewById(R.id.btn_add_number)

        render()

        // send intent
        btnAddNumber.setOnClickListener {
            lifecycleScope.launch {
                addNumberViewModel.intentChannel.send(MainIntent.AddNumber)
            }
        }

    }

    // render
    private fun render() {
        lifecycleScope.launchWhenStarted {
            addNumberViewModel.state.collect {
                when(it) {
                    is MainViewState.Idle -> tvNumber.text = "Idle"
                    is MainViewState.Number -> tvNumber.text = it.number.toString()
                    is MainViewState.Error -> tvNumber.text = it.error
                }
            }
        }
    }

}