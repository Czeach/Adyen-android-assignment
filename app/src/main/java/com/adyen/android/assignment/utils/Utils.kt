package com.adyen.android.assignment.utils

import com.adyen.android.assignment.api.model.AstronomyPicture
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {

    fun formatDateString(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)

        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(outputFormatter)
    }

    fun sortAPODSByDate(apods: List<AstronomyPicture>): List<AstronomyPicture> {
        return apods.sortedWith(compareByDescending {
            val (day, month, year) = it.date.split("/")
            LocalDate.of(year.toInt(), month.toInt(), day.toInt()).toEpochDay()
        })
    }

    fun sortAPODsByTitle(apods: List<AstronomyPicture>): List<AstronomyPicture> {
        return apods.sortedWith(compareBy { it.title })
    }

}