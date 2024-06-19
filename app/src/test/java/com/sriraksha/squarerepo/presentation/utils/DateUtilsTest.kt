package com.sriraksha.squarerepo.presentation.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {
    @Test
    fun `dateToString should format date correctly`() {
        val date = "2024-01-01T12:00:00Z"
        val expected = "Mon, 01 January, 2024, 12:00 pm"
        val actual = DateUtils.dateToString(date, DateUtils.DATE_FORMAT_EEEE_DD_MMMM_YYYY)
        assertEquals(expected, actual)
    }

    @Test
    fun `dateToString should handle different date patterns`() {
        val date = "2024-01-01T12:00:00Z"
        val datePattern = "yyyy-MM-dd HH:mm"
        val expected = "2024-01-01 12:00"
        val actual = DateUtils.dateToString(date, datePattern)
        assertEquals(expected, actual)
    }

    @Test(expected = java.time.format.DateTimeParseException::class)
    fun `dateToString should throw exception for invalid date format`() {
        val date = "invalid-date-format"
        DateUtils.dateToString(date, DateUtils.DATE_FORMAT_EEEE_DD_MMMM_YYYY)
    }

    @Test
    fun `dateToString should format date with different time zones`() {
        val date = "2024-01-01T12:00:00+02:00"
        val datePattern = "EEE, dd MMMM, yyyy, hh:mm a"
        val expected = "Mon, 01 January, 2024, 12:00 pm" // Adjusted for timezone
        val actual = DateUtils.dateToString(date, datePattern)
        assertEquals(expected, actual)
    }
}
