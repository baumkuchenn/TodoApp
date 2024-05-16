package com.misoramen.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.misoramen.todoapp.model.Todo
import com.misoramen.todoapp.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application)
    :AndroidViewModel(application), CoroutineScope{

    private val job = Job()
    fun addTodo(list: List<Todo>){
        launch {
            val db = TodoDatabase.buildDatabase(
                getApplication()
            )
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}