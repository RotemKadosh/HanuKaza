package com.example.kazagifts.home.currentraffle

import androidx.lifecycle.ViewModel

class CurrentRaffleViewModel : ViewModel() {

    fun onAccountUpdate(accountId: String?) {
        fetchAccountCurrentRaffle(accountId)
    }

    private fun fetchAccountCurrentRaffle(accountId: String?) {

    }

}