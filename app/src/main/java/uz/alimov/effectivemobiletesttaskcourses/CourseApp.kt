package uz.alimov.effectivemobiletesttaskcourses

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.alimov.effectivemobiletesttaskcourses.data.appModule

class CourseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CourseApp)
            modules(appModule)
        }
    }
}