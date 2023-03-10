package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.PollChooseDto
import com.alnoor.polls.domain.model.PollChoose
import com.alnoor.polls.domain.util.Mapper

class PollChooseEntityMapper: Mapper<PollChooseDto, PollChoose> {

    override fun mapFrom(data: PollChoose): PollChooseDto {
        return PollChooseDto(
            content = data.content
        )
    }

    override fun mapTo(data: PollChooseDto): PollChoose {
        return PollChoose(
            id = data.id!!,
            pollId = data.pollId!!,
            content = data.content!!
        )
    }

}