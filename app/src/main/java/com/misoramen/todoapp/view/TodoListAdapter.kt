package com.misoramen.todoapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misoramen.todoapp.databinding.TodoItemLayoutBinding
import com.misoramen.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){
    class TodoViewHolder(var binding: TodoItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.checkTask.text = todoList[position].title

        holder.binding.checkTask.setOnCheckedChangeListener() {
            compoundButton, b ->
            if(compoundButton.isPressed){
                adapterOnClick(todoList[position])
            }
        }
    }

    fun updateTodoList(newTodoList: List<Todo>){
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}