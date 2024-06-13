package com.misoramen.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.misoramen.todoapp.model.Todo

interface TodoCheckedChangeListener {
    fun onCheckedChanged(cb: CompoundButton, isChecked:Boolean, obj: Todo)
}

interface TodoEditClick {
    fun onTodoEditClick(v: View)
}

interface RadioClickListener {
    fun onRadioClick(v:View, priority:Int, obj:Todo)
}

interface RadioClick {
    fun onRadioClick(v: View)
}

interface TodoSaveChangesClick {
    fun onTodoSaveChangesClick(v: View, obj: Todo)
}

interface DateClickListener {
    fun onDateClick(v:View)
}

interface TimeClickListener {
    fun onTimeClick(v:View)
}