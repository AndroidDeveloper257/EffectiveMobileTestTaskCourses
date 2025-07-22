package uz.alimov.effectivemobiletesttaskcourses.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.navigation.NavigationGraph
import uz.alimov.effectivemobiletesttaskcourses.presentation.ui.theme.EffectiveMobileTestTaskCoursesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EffectiveMobileTestTaskCoursesTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = koinViewModel()
                NavigationGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EffectiveMobileTestTaskCoursesTheme {
        NavigationGraph()
    }
}