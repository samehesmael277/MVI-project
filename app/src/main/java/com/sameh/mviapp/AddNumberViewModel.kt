package com.sameh.mviapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel: ViewModel() {

    // number model
    private var number = 0

    // channel (take data from activity to view model)
    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    // state flow & encapsulation (take result from view model to activity)
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState> get() = _viewState

    init {
        processIntent()
    }

    // process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect() {
                when(it) {
                    is MainIntent.AddNumber -> addNumber()
                }
            }
        }
    }

    // reduce
    private fun addNumber() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Number(++number)
                }
                catch (e: Exception) {
                    MainViewState.Error(e.message!!)
                }
        }
    }

}