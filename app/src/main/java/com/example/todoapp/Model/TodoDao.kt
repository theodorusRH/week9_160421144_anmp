package com.example.todoapp.Model

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

    @Query("SELECT * FROM todo ORDER BY priority DESC")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id:Int): Todo

    @Delete
    fun deleteTodo(todo:Todo)


    @Query("UPDATE todo SET title =:title, notes=:notes, priority=:priority WHERE uuid=:id")
    fun update(title:String, notes:String, priority:Int, id:Int) //id wajib

    @Update
    fun UpdateTodo(todo:Todo)

}
