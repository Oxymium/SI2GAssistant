package com.oxymium.si2gassistant.utils

class CapitalizeFirstLetter {
    companion object {

        @JvmStatic
        fun capitalizeFirstLetter(input: String): String {
            if (input.isEmpty()) {
                return input
            }
            return input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        }
    }
}