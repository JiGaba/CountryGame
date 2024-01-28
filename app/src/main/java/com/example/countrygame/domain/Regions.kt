package com.example.countrygame.domain

class Regions {
    companion object {

        const val STATES_COUNT = 207
        const val AFRICA = "africa"
        const val ASIA = "asia"
        const val ANTARTIC = "antarctic"
        const val EUROPE = "europe"
        const val OCEANIA = "oceania"
        const val SOUTH_AMERICA = "south america"
        const val NORTH_AMERICA = "north america"
        const val CENTRAL_AMERICA = "central america"

        val getRegionList: List<String> = listOf(
            "africa"
            ,"asia"
            ,"antarctic"
            ,"europe"
            ,"oceania"
            ,"south america"
            ,"central america"
            ,"north america"
        )
    }
}