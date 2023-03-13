package com.alnoor.polls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.alnoor.polls.data.preference.UserPreferenceStore
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.presentation.navigation.PollAppNavigation
import com.alnoor.polls.presentation.screen.login.LoginScreen
import com.alnoor.polls.presentation.screen.signup.SignupScreen
import com.alnoor.polls.ui.theme.PollsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var userPreferenceStore: UserPreferenceStore
    private val isLogged = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            isLogged.value = userPreferenceStore.isLogged()
        }
        setContent {
            PollsTheme {
                PollAppNavigation(rememberNavController(), isLogged = isLogged.value)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PollsTheme {
        Greeting("Alnoor")
    }
}