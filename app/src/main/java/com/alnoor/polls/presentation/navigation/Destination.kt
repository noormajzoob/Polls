package com.alnoor.polls.presentation.navigation

import com.alnoor.polls.domain.model.Poll
import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.presentation.commons.toJson

sealed class Destination(val route: String){
    object AuthNav: Destination("auth")
    object Login: Destination("login")
    object Signup: Destination("signup")
    object HomeNav: Destination("home.nav")
    object Home: Destination("home")
    object CreatePoll: Destination("create.poll")
    object ViewPoll: Destination("view/{poll}"){

        fun getRoute(poll: PollWrapper): String{
            return route.replace("{poll}", poll.toJson())
        }

    }

    object VotePoll: Destination("poll/{id}")
}
