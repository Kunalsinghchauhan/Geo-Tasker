package com.ksc.geotasker.repository

import androidx.lifecycle.LiveData
import com.ksc.geotasker.database.TodoDao
import com.ksc.geotasker.model.Todo
import com.ksc.geotasker.database.TodoDatabase

class TodoRepository(private val todoDao: TodoDao) {

    val getAllTask: LiveData<List<Todo>> = todoDao.getAllTasks()

    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: Todo) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }

    suspend fun getTaskById(id: Int): Todo {
        return todoDao.getTaskById(id)
    }


}