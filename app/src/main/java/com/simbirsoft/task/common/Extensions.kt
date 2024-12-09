package com.simbirsoft.task.common

import android.content.Context
import com.simbirsoft.task.data.model.TaskDTO
import kotlinx.serialization.json.Json
import java.io.IOException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

private const val FILE_NAME = "tasks.json"

fun Context.readTasksFromJson(): List<TaskDTO> {
    val json = Json { ignoreUnknownKeys = true }
    return try {
        val jsonStr = this.assets.open(FILE_NAME)
            .bufferedReader().use { it.readText() }
        json.decodeFromString<List<TaskDTO>>(jsonStr)
    } catch (exception: IOException) {
        exception.printStackTrace()
        emptyList()
    }
}

fun LocalDateTime.toMillis(zone: ZoneId = ZoneId.systemDefault()) =
    atZone(zone)?.toInstant()?.toEpochMilli() ?: Date().time

fun Long.toLocalDate(zone: ZoneId = ZoneId.systemDefault()) =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zone)


private const val DATE_FORMAT = "EEE, MMM dd, yyyy"
private const val TIME_FORMAT = "HH:mm"
private const val DATE_TIME_FORMAT = "EEE, MMM dd, yyyy - HH:mm"

fun LocalDate.toFormatDate(): String = DateTimeFormatter.ofPattern(DATE_FORMAT).format(this)

fun LocalTime.toFormatTime(): String =
    DateTimeFormatter.ofPattern(TIME_FORMAT).format(LocalTime.of(this.hour, this.minute))

fun Long.createLocalDateFromMillis(zoneId: ZoneId = ZoneId.systemDefault()): LocalDate =
    Instant.ofEpochMilli(this).atZone(zoneId).toLocalDate()

fun LocalDateTime.toFormatLocalDateTime(): String =
    this.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))

fun LocalDateTime.toFormatLocalTime(): String =
    this.format(DateTimeFormatter.ofPattern(TIME_FORMAT))