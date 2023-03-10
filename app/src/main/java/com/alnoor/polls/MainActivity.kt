package com.alnoor.polls

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.alnoor.polls.data.preference.UserPreferenceStore
import com.alnoor.polls.domain.model.*
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.ui.theme.PollsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var pollRepository: PollRepository
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var userPreferenceStore: UserPreferenceStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PollsTheme {
                // A surface container using the 'background' color mapFrom the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        lifecycleScope.launch {

            //userRepository.login(email = "alnoor10@gmail.com", password = "alnoor")

            Log.d("TAG", "onCreate: ${userPreferenceStore.isLogged()}")
            val resource = pollRepository.postVote(
                1, 3
            )


            when(resource){
                is Resource.Success -> Log.d("TAG", "onCreate: ${resource.data}")
                is Resource.Error -> Log.d("TAG", "onCreate: ${resource.message}")
                is Resource.Failure -> Log.d("TAG", "onCreate: ${resource.e.message}")
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