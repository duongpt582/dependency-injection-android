package com.example.noteapp.di

import android.app.Application
import com.example.noteapp.activities.AddNoteActivity
import com.example.noteapp.activities.MainActivity
import com.example.noteapp.activities.UpdateNoteActivity
import com.example.noteapp.viewmodel.NoteViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
// chú thích cho app đây là component --> từ đây thì dagger sẽ tự tạo ra container cho chúng ta.
interface AppComponent {

    fun getNoteViewModel(): NoteViewModel
    fun inject(activity: MainActivity)
    fun inject(activity: AddNoteActivity)
    fun inject(activity: UpdateNoteActivity)

    fun getAuthComponentFactory(): AuthComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        // thông báo cho dagger biết cần thêm đối tượng application vào bên trong biểu đồ
        // và khi nào yêu cầu cần đối tượng này thì cung cấp cho nơi cần.
        fun application(application: Application) :Builder
        fun build(): AppComponent
    }
}