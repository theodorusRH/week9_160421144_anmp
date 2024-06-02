package com.example.todoapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.Model.Todo
import com.example.todoapp.Model.TodoDatabase
import com.example.todoapp.Util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val job = Job()

    val todoLD = MutableLiveData<Todo>()

    fun fetch(uuid:Int){
        launch {
            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    fun addTodo(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    fun updateTodo(todo: Todo){
        launch {
            buildDb(getApplication()).todoDao().UpdateTodo(todo)
        }
    }

    fun update(title:String, notes:String, priority:Int, uuid:Int){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(title,notes,priority,uuid)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}