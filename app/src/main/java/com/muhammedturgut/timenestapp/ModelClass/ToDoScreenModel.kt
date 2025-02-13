package com.muhammedturgut.timenestapp.ModelClass

data class ToDoScreenModelContinues(
     val Duty:String,
     val favorite: Boolean,
     val DutyState:Boolean
)

data class  ToDoScreenModelContinuesCompleted(
    val Duty:String,
    val favorite: Boolean,
    val DutyState:Boolean
)