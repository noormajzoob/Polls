package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.PollSelectionWrapperDto
import com.alnoor.polls.data.dto.SelectedVoteDto
import com.alnoor.polls.domain.model.PollSelection
import com.alnoor.polls.domain.util.Mapper
import javax.inject.Inject

class PollSelectionMapper @Inject constructor(
    private val userEntityMapper: UserEntityMapper,
    private val pollChooseEntityMapper: PollChooseEntityMapper
): Mapper<PollSelectionWrapperDto, PollSelection> {

    override fun mapFrom(data: PollSelection): PollSelectionWrapperDto {
        return PollSelectionWrapperDto(
            user = userEntityMapper.mapFrom(data.user),
            choose = pollChooseEntityMapper.mapFrom(data.choose)
        )
    }

    override fun mapTo(data: PollSelectionWrapperDto): PollSelection {
        return PollSelection(
            user = userEntityMapper.mapTo(data.user),
            choose = pollChooseEntityMapper.mapTo(data.choose)
        )
    }

    fun selectionToPostDto(pollSelection: PollSelection): SelectedVoteDto{
        return SelectedVoteDto(
            user = pollSelection.user.id,
            choose = pollSelection.choose.id
        )
    }

}