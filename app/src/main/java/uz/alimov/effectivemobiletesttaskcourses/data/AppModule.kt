package uz.alimov.effectivemobiletesttaskcourses.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uz.alimov.effectivemobiletesttaskcourses.data.local.CoursesDatabase
import uz.alimov.effectivemobiletesttaskcourses.data.remote.api.CoursesService
import uz.alimov.effectivemobiletesttaskcourses.data.remote.api.CoursesServiceImpl
import uz.alimov.effectivemobiletesttaskcourses.data.repository.AuthRepositoryImpl
import uz.alimov.effectivemobiletesttaskcourses.data.repository.CoursesRepositoryImpl
import uz.alimov.effectivemobiletesttaskcourses.domain.repository.AuthRepository
import uz.alimov.effectivemobiletesttaskcourses.domain.repository.CoursesRepository
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.BookmarkedCoursesUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.CheckAuthUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.CourseByIdUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.GetCoursesUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.LoginUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.LogoutUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.StartCourseUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.StartedCoursesUseCase
import uz.alimov.effectivemobiletesttaskcourses.domain.usecase.ToggleBookmarkCourseUseCase
import uz.alimov.effectivemobiletesttaskcourses.presentation.MainViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.details.DetailsViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.favorites.FavoritesViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.home.HomeViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.login.LoginViewModel
import uz.alimov.effectivemobiletesttaskcourses.presentation.screens.profile.ProfileViewModel

val appModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = CoursesDatabase::class.java,
            name = "courses_database"
        )
            .allowMainThreadQueries()
            .build()
    }
    single {
        androidContext().getSharedPreferences("auth_state", Context.MODE_PRIVATE)
    }
    single {
        get<CoursesDatabase>().coursesDao()
    }
    viewModel {
        LoginViewModel(loginUseCase = get())
    }
    viewModel {
        HomeViewModel(
            getCoursesUseCase = get(),
            toggleBookmarkCourseUseCase = get()
        )
    }
    viewModel {
        DetailsViewModel(
            courseByIdUseCase = get(),
            startCourseUseCase = get(),
            toggleBookmarkUseCase = get()
        )
    }
    viewModel {
        MainViewModel(
            checkAuthUseCase = get()
        )
    }
    viewModel {
        FavoritesViewModel(
            bookmarkedCoursesUseCase = get(),
            toggleBookmarkCourseUseCase = get()
        )
    }
    viewModel {
        ProfileViewModel(
            startedCoursesUseCase = get(),
            toggleBookmarkCourseUseCase = get(),
            logoutUseCase = get()
        )
    }
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("NetworkLogging", "log: $message")
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
        }
    }
    single<CoursesService> {
        CoursesServiceImpl(client = get())
    }
    single<CoursesRepository> {
        CoursesRepositoryImpl(
            service = get(),
            dao = get()
        )
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
            dao = get(),
            sharedPref = get()
        )
    }
    single {
        BookmarkedCoursesUseCase(repository = get())
    }
    single {
        CourseByIdUseCase(repository = get())
    }
    single {
        GetCoursesUseCase(repository = get())
    }
    single {
        StartCourseUseCase(repository = get())
    }
    single {
        StartedCoursesUseCase(repository = get())
    }
    single {
        ToggleBookmarkCourseUseCase(repository = get())
    }
    single {
        LoginUseCase(repository = get())
    }
    single {
        CheckAuthUseCase(repository = get())
    }
    single {
        LogoutUseCase(repository = get())
    }
}