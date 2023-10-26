package com.example.noteapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "NOTE_VIEW_MODEL"

    //    lateinit var noteViewModel: NoteViewModel
    private val noteViewModel: NoteViewModel by viewModels()
//
//    @Inject
//    lateinit var myService: MyService

    @Inject
    lateinit var myClass: MyClass

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        Log.d(TAG, "onCreate: ${myService.getAThing()}")
        Log.d(TAG, "onCreate: ${myClass.doAnyThing()}")


//        Log.d(TAG, "MainActivity: ${noteViewModel.noteRepository} , $noteViewModel")

//        initControls()
//        initEvents()

    }

    private fun initEvents() {
        binding.btnOpenAddActivity.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initControls() {
        val adapter = NoteAdapter(this@MainActivity, onItemClick, onItemDelete)

        binding.rvNote.setHasFixedSize(true)
        binding.rvNote.layoutManager = LinearLayoutManager(this)
        binding.rvNote.adapter = adapter

        noteViewModel.getAllNote().observe(this, {
            adapter.setNotes(it)
        })

    }

    private val onItemClick: (Note) -> Unit = {
        val intent = Intent(this, UpdateNoteActivity::class.java)
        intent.putExtra("UPDATE_NOTE", it)
        startActivity(intent)

    }
    private val onItemDelete: (Note) -> Unit = {
        noteViewModel.deleteNote(it)
    }

}

// sử dụng để chỉ lấy 1 implement
//@InstallIn(SingletonComponent::class)
//@Module
//abstract class MyExampleModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindMyService(myServiceImpl: MyServiceImpl): MyService
//}

// sử dụng để lấy cả 2 implement - sẽ là impl 1 và 2

@InstallIn(SingletonComponent::class)
@Module
object MyExampleModule {

    @Provides
    @Singleton
    @Impl1
    fun provideMyServiceImpl(): MyService {
        return MyServiceImpl()
    }

    @Provides
    @Singleton
    @Impl2
    fun provideMyService2Impl() : MyService {
        return MyService2Impl()
    }
}

class MyClass @Inject constructor(
    @Impl1 val myServiceImpl: MyService,
    @Impl2 val myService2Impl: MyService,
) {

    fun doAnyThing() = "I got ${myServiceImpl.getAThing()} and ${myService2Impl.getAThing()}"
}





interface MyService {
    fun getAThing(): String
}

class MyServiceImpl @Inject constructor() : MyService {
    override fun getAThing(): String {
        return "A thing 1"
    }

}

class MyService2Impl @Inject constructor(): MyService {
    override fun getAThing(): String {
        return "A thing 2"
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2

