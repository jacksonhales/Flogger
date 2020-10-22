package com.example.flogger.enumeration

enum class ExerciseType (private val friendlyName: String) {
    TIME_BASED("Time Based"), REP_BASED("Rep Based");

    var fName = friendlyName

    @Override
    override fun toString(): String {
        return fName
    }
}