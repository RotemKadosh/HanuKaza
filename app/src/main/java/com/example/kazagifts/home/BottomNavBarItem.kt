package com.example.kazagifts.home

import com.example.kazagifts.R

sealed class BottomNavBarItem(var title:String, var icon:Int, var screen_route:String){
    object CurrentRaffleScreen: BottomNavBarItem("Current Raffle", R.drawable.shopping_cart, "current raffle")
    object RafflesHistoryScreen: BottomNavBarItem("History", R.drawable.ic_history ,"history")
}
