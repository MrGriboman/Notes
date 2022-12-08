package com.example.notes.other

import com.example.notes.R

class DrawablesGetter {
    companion object {
        fun getStarDrawable(isImportant: Boolean): Int {
            return when(isImportant) {
                true -> R.drawable.ic_baseline_star_24
                false -> R.drawable.ic_baseline_star_outline_24
            }
        }
    }
}