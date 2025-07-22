package uz.alimov.effectivemobiletesttaskcourses.presentation.navigation

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.MainViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.navigation.Destinations.CourseDetails
import uz.alimov.effectivemobiletesttaskcourses.presentation.navigation.Destinations.Favorites
import uz.alimov.effectivemobiletesttaskcourses.presentation.navigation.Destinations.Home
import uz.alimov.effectivemobiletesttaskcourses.presentation.navigation.Destinations.Login
import uz.alimov.effectivemobiletesttaskcourses.presentation.navigation.Destinations.Profile
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.details.DetailsScreen
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites.FavoritesScreen
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home.HomeScreen
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login.LoginScreen
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile.ProfileScreen
import java.net.URI

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val isLoggedIn = viewModel.isLoggedIn.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            BottomBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = if (isLoggedIn.value) Home else Login,
        ) {
            composable<Login> {
                LoginScreen(
                    modifier = Modifier.padding(innerPadding),
                    onLogin = {
                        navController.navigate(Home) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    },
                    openUrl = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, URI.create(url).toString().toUri())
                        context.startActivity(intent)
                    }
                )
            }
            composable<Home> {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    onCourseClicked = {
                        navController.navigate(CourseDetails(it))
                    }
                )
            }
            composable<Favorites> {
                FavoritesScreen(
                    modifier = Modifier.padding(innerPadding),
                    onCourseClicked = {
                        navController.navigate(CourseDetails(it))
                    },
                    onNavigateHomeClicked = {
                        navController.navigate(Home) {
                            popUpTo(Home) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Profile> {
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigateToDetails = {
                        navController.navigate(CourseDetails(it))
                    },
                    onNavigateHome = {
                        navController.navigate(Home) {
                            popUpTo(Home) {
                                inclusive = true
                            }
                        }
                    },
                    onLogout = {
                        navController.navigate(Login) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<CourseDetails> {
                val courseId = it.toRoute<CourseDetails>().courseId
                DetailsScreen(
                    modifier = Modifier.fillMaxSize(),
                    courseId = courseId,
                    innerPadding = innerPadding,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    /*val showBottomBar = TopLevelDestinations.entries.map { it.route::class }.any { route ->
        currentDestination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }*/

    val showBottomBar = currentDestination?.hierarchy?.none { it.hasRoute(Login::class) } == true &&
            TopLevelDestinations.entries.map { it.route::class }.any { route ->
                currentDestination.hierarchy.any { it.hasRoute(route) } == true
            }

    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically(
            initialOffsetY = {
                it
            }
        ),
        exit = slideOutVertically(
            targetOffsetY = {
                it
            }
        )
    ) {
        BottomAppBar(modifier = modifier) {
            TopLevelDestinations.entries.map { topLevelDestination ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.hasRoute(topLevelDestination.route::class) }

                if (currentDestination != null) {
                    NavigationBarItem(
                        onClick = {
                            navController.navigate(topLevelDestination.route) {
                                popUpTo(Home) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(topLevelDestination.icon),
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = true,
                        label = {
                            Text(
                                text = stringResource(topLevelDestination.label)
                            )
                        },
                        selected = isSelected == true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                }
            }
        }
    }
}