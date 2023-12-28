package com.woosuk.disenycompose.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.woosuk.disenycompose.R
import com.woosuk.disenycompose.ui.detail.PosterDetailsScreen
import com.woosuk.disenycompose.ui.posters.HomeScreen
import com.woosuk.disenycompose.ui.posters.LibraryScreen
import com.woosuk.disenycompose.ui.posters.RadioScreen
import com.woosuk.disenycompose.ui.theme.DisenyComposeTheme

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isMainTabScreen(currentDestination)) {
                MainTopBar()
            }
        },
        bottomBar = {
            if (isMainTabScreen(currentDestination)) {
                MainBottomNavigation(
                    currentTab = DisenyTab.getTabFromRoute(
                        currentDestination?.route ?: return@Scaffold,
                    ) ?: return@Scaffold,
                    onTabClick = { route ->
                        navController.navigate(route) {
                            // popUpTo 는 뒤로가기를 설정할 수 있음.
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // 같은 탭을 클릭했을 때, 중복해서 컴포즈를 생성x
                            launchSingleTop = true
                            // 이전 상태 복원 (ex. 스크롤 상태)
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Box(modifier = modifier) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(onPosterClick = { navController.navigate("${Screen.Detail.route}/$it") })
                }
                composable(Screen.Library.route) {
                    LibraryScreen(onPosterClick = { navController.navigate("${Screen.Detail.route}/$it") })
                }
                composable(Screen.Radio.route) {
                    RadioScreen(
                        onPosterClick = { navController.navigate("${Screen.Detail.route}/$it") },
                    )
                }
                composable(
                    route = Screen.Detail.routeWithArgument,
                    arguments = listOf(
                        navArgument(
                            name = Screen.Detail.argument0,
                            builder = { type = NavType.LongType },
                        ),
                    ),
                ) { navBackStackEntry ->
                    val posterId = navBackStackEntry.arguments?.getLong(Screen.Detail.argument0)
                        ?: return@composable
                    PosterDetailsScreen(
                        id = posterId,
                        onPressBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}

private fun isMainTabScreen(navDestination: NavDestination?): Boolean {
    return (DisenyTab.getTabFromRoute(navDestination?.route ?: return false) != null)
}

@Composable
fun MainTopBar(
    title: String = "DisneyCompose",
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = Color.White,
        ),
    )
}

@Composable
fun MainBottomNavigation(
    currentTab: DisenyTab,
    onTabClick: (String) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
    ) {
        DisenyTab.values().forEach { tab ->
            NavigationBarItem(
                selected = tab == currentTab,
                onClick = { onTabClick(tab.route) },
                icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                label = { Text(text = stringResource(id = tab.title), color = Color.White) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                ),
                alwaysShowLabel = true,
            )
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("HOME", R.string.home)
    object Radio : Screen("RADIO", R.string.radio)
    object Library : Screen("LIBRARY", R.string.library)
    object Detail : Screen("PosterDetails", R.string.detail) {
        const val routeWithArgument: String = "PosterDetails/{posterId}"

        const val argument0 = "posterId"
    }
}

enum class DisenyTab(
    @StringRes val title: Int,
    val route: String,
    val icon: ImageVector,
) {
    HOME(Screen.Home.resourceId, Screen.Home.route, Icons.Filled.Home),
    RADIO(Screen.Radio.resourceId, Screen.Radio.route, Icons.Filled.Radio),
    LIBRARY(Screen.Library.resourceId, Screen.Library.route, Icons.Filled.LibraryAdd),
    ;

    companion object {
        fun getTabFromRoute(route: String): DisenyTab? {
            return when (route) {
                Screen.Radio.route -> RADIO
                Screen.Library.route -> LIBRARY
                Screen.Home.route -> HOME
                else -> null
            }
        }
    }
}

@Preview
@Composable
fun MainTopBarPreview() {
    DisenyComposeTheme {
        MainTopBar()
    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    DisenyComposeTheme {
        MainBottomNavigation(currentTab = DisenyTab.HOME, onTabClick = {})
    }
}
