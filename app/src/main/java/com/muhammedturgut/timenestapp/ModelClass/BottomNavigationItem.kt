package com.muhammedturgut.timenestapp.ModelClass

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    val route: String,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
