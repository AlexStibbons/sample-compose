package com.chomp.library.data

import android.content.Context
import androidx.navigation.NavHostController
import com.chomp.R

data class Faker(
    val id: Int,
    val title: String,
    val subtitle: String,
    val drawableRes: Int
)

val FAKE_DATA: List<Faker> by lazy { createFakeData() }

internal fun createFakeData(): List<Faker> {
    val res = mutableListOf<Faker>()
    repeat(25) { index ->
        res.add(
            Faker(
                index,
                "Title ${index + 1}",
                "Subtitle ${index + 1}",
                R.drawable.ic_star
            )
        )
    }
    return res.toList()
}

 class FakeHost(context: Context) : NavHostController(context)