package com.matthew.carvalhodagenais.cubedcollector.helpers

class StringHelper {
    companion object {

        /**
         * Sets a String to null if it is empty
         */
        fun setNullIfEmptyString(string: String): String? =
            if (string.trim().isEmpty()) null else string

    }
}