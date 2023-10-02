package com.example.noteapp.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.database.dao.NoteDao
import com.example.noteapp.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// đánh dấu cho biết là cái class NoteRepository này chỉ cho dagger biết chỉ được khởi tạo 1 lần
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun insertNote(note:Note) = noteDao.insertNote(note)
    suspend fun updateNote(note:Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note:Note) = noteDao.deleteNote(note)

    fun getAllNote():LiveData<List<Note>> = noteDao.getAllNote()

}