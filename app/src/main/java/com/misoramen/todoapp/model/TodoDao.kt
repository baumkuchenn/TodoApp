package com.misoramen.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo:Todo)

    @Query("SELECT * FROM todo WHERE is_done= 0 ORDER BY priority DESC")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id:Int): Todo

    @Query("UPDATE todo SET title= :title, notes= :notes, priority= :priority WHERE uuid= :uuid")
    fun update(title: String, notes: String, priority: Int, uuid: Int)

    @Query("UPDATE todo SET is_done= 1 WHERE uuid= :uuid")
    fun checked(uuid: Int)

    @Update()
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo:Todo)

}