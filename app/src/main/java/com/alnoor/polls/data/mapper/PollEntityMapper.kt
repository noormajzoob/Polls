package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.PollDto
import com.alnoor.polls.domain.model.Poll
import com.alnoor.polls.domain.util.Mapper
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
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
            timestamp = formatTime(data.timestamp!!),
            duration = formatDuration(data.duration!!),
            views = data.views!!,
            urlId = data.urlId!!,
            status = data.status!!
        )
    }

    private fun formatDuration(duration: Int): String{
        val builder = StringBuilder()
        if (duration >= 24){
            val day = duration / 24
            builder.append(format(day, "day"))

            val hour = duration % 24
            if (hour > 0){
                builder.append(format(hour, "hour"))
            }
        }else{
            builder.append(format(duration, "hour"))
        }
        return builder.toString()
    }

    private fun formatTime(millis: Long): String{
        val timestamp = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
        val now = Instant.now().atZone(ZoneId.systemDefault())


        val diffYear =  now.year - timestamp.year
        if (diffYear > 0)
            return format(diffYear, "year")

        val diffMonth = now.month.value - timestamp.month.value
        if (diffMonth > 0)
            return format(diffMonth, "month")

        val diffDay = now.dayOfMonth - timestamp.dayOfMonth
        if (diffDay > 0)
            return format(diffDay, "day")

        val diffHour = now.hour - timestamp.hour
        if (diffHour > 0)
            return format(diffHour, "hour")

        val diffMinute = now.minute - timestamp.minute
        if (diffMinute > 0)
            return format(diffMinute, "minute")

        return "moment ago"
    }

    private fun format(count: Int, word: String): String{
        return if (count > 1) "$count ${word}s"
        else "$count $word"
    }

}