package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.PollDto
import com.alnoor.polls.domain.model.Poll
import com.alnoor.polls.domain.util.Mapper
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PollEntityMapper @Inject constructor(): Mapper<PollDto, Poll> {

    override fun mapFrom(data: Poll): PollDto {
        return PollDto(
            title = data.title,
            duration = data.duration.toInt(),
            user = data.user
        )
    }

    override fun mapTo(data: PollDto): Poll {
        return Poll(
            id = data.id!!,
            title = data.title!!,
            user = data.user!!,
            timestamp = kotlin.run {
                Instant.ofEpochMilli(data.timestamp!!).atZone(ZoneId.systemDefault()).toLocalDateTime().atOffset(
                    ZoneOffset.UTC)
                    .format(DateTimeFormatter.RFC_1123_DATE_TIME)
            },
            duration = kotlin.run {
                val builder = StringBuilder()
                if (data.duration!! >= 24){
                    val day = data.duration / 24
                    builder.append(day)
                    if (day > 1) builder.append(" days")
                    else builder.append(" day")

                    val hour = data.duration % 24
                    if (hour > 0){
                        builder.append(hour)
                        if (hour > 1) builder.append(" hours")
                        else builder.append(" hour")
                    }
                }else{
                    builder.append(data.duration)
                    if (data.duration > 1) builder.append(" hours")
                    else builder.append(" hour")
                }

                ""
            },
            views = data.views!!,
            urlId = data.urlId!!,
            status = data.status!!
        )
    }

}