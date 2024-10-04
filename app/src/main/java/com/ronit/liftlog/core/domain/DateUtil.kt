package com.ronit.liftlog.core.domain

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit


fun LocalDate.toEpochMillis():Long{

    return  this.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun Long.toLocalDate():LocalDate{

    return  Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

infix  fun LocalDate.Until(endDate:LocalDate):List<LocalDate>{

    var startDate = this
    val dateList = mutableListOf<LocalDate>()

    while (!startDate.isAfter(endDate)){
        dateList.add(startDate)
        startDate = startDate.plusDays(1)

    }

    return dateList
}