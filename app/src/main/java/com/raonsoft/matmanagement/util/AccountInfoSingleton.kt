package com.raonsoft.matmanagement.util

class AccountInfoSingleton private constructor(val account_idx: Int) {
    companion object {
        @Volatile
        private var INSTANCE: AccountInfoSingleton? = null
        var account_idx: Int? = null
        var armyunit_idx: Int? = null
        var armyunit_name: String? = null
        var account_profile: String? = null


        fun getInstance(account_idx: Int): AccountInfoSingleton =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AccountInfoSingleton(account_idx).also {
                    INSTANCE = it
                    this.account_idx = account_idx
                }
            }

        fun setAccountInfo(
            armyunit_idx: Int,
            armyunit_name: String,
            account_profile: String?
        ) {

            this.armyunit_idx = armyunit_idx
            this.armyunit_name = armyunit_name
            this.account_profile = account_profile

        }


        /*fun getInstance(account_idx: Int): AccountInfoSingleton =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AccountInfoSingleton(account_idx).also {
                    INSTANCE = it
                    this.account_idx = account_idx
                }
            }*/
    }


}