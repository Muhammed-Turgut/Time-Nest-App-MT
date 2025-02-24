package com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass

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