package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.PollWrapperDto
import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.domain.util.Mapper
import javax.inject.Inject

class PollWrapperMapper @Inject constructor(
    private val pollEntityMapper: PollEntityMapper,
    private val chooseEntityMapper: PollChooseEntityMapper
): Mapper<PollWrapper, PollWrapperDto> {

    override fun mapFrom(data: PollWrapperDto): PollWrapper {
        return PollWrapper(
            poll = pollEntityMapper.mapTo(data.vote),
            chooses = data.chooses.map {
                chooseEntityMapper.mapTo(it)
            }
        )
    }

    override fun mapTo(data: PollWrapper): PollWrapperDto {
        return PollWrapperDto(
            vote = pollEntityMapper.mapFrom(data.poll),
            chooses = data.chooses.map {
                chooseEntityMapper.mapFrom(it)
            }
        )
    }

}