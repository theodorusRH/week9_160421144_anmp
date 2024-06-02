package com.example.todoapp.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.Util.DB_NAME
import com.example.todoapp.Util.MIGRATION_1_2

@Database(entities = arrayOf(Todo::class), version =  2)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        fun buildDatabase(context:Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                DB_NAME).addMigrations(MIGRATION_1_2).build()
    }

    operator fun invoke(context: Context){
        if(instance != null){
            synchronized(LOCK){
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }

}
