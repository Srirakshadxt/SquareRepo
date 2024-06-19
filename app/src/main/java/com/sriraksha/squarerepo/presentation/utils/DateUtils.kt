package com.sriraksha.squarerepo.presentation.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Utility class providing methods for date formatting and parsing,
 * promoting code reuse across date-related operations.
 */

class DateUtils {

    companion object {

        const val DATE_FORMAT_EEEE_DD_MMMM_YYYY = "EEE, dd MMMM, yyyy, hh:mm a"

        fun dateToString(date: String, datePattern: String): String {
            val zonedDateTime = ZonedDateTime.parse(date)
            val dateFormatter = DateTimeFormatter.ofPattern(datePattern)
            return zonedDateTime.format(dateFormatter)
        }
    }
}