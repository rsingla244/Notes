package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    //suspend means query will be running in background so that your ui responds while working on it is called coroutiens
    //onconflict means you are adding data which is already added will ignore that data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}