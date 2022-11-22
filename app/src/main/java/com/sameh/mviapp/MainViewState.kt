package com.sameh.mviapp

sealed class MainViewState {

    // Idle
    object Idle : MainViewState()

    // Number
    data class Number(val number : Int) : MainViewState()

    // Error
    data class Error(val error : String) : MainViewState()

}