package com.misoramen.todoapp.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.misoramen.todoapp.databinding.TodoItemLayoutBinding
import com.misoramen.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(), TodoCheckedChangeListener, TodoEditClick{
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
//        holder.binding.checkTask.text = todoList[position].title + " " + todoList[position].priority

        holder.binding.imgEdit.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToEditTodoFragment(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

//        holder.binding.checkTask.setOnCheckedChangeListener() {
//            compoundButton, isChecked ->
//            if(isChecked){
//                adapterOnClick(todoList[position])
//            }
//            else{
////                listener?.onTodoChecked(todoList[position].uuid)
//            }
//        }

        if (todoList[position].isDone.equals(1)) {
            holder.binding.checkTask.paintFlags = holder.binding.checkTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.checkTask.isEnabled = false
            holder.binding.checkTask.isChecked = true
        } else {
            holder.binding.checkTask.isChecked = false
        }

        holder.binding.todo = todoList[position]
        holder.binding.listener = this
        holder.binding.editListener = this
    }

    fun updateTodoList(newTodoList: List<Todo>){
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChanged(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if (isChecked) {
            cb.paintFlags = cb.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            cb.isEnabled = false
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionTodoListFragmentToEditTodoFragment(uuid)

        Navigation.findNavController(v).navigate(action)
    }
}