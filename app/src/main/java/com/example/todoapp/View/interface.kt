package com.example.todoapp.View

import android.view.View
import android.widget.CompoundButton
import com.example.todoapp.Model.Todo

interface TodoCheckedChangeListener{

    fun onCheckedChange(cb:CompoundButton,isChecked:Boolean,obj:Todo)

}

interface TodoEditClickListener{

    fun onTodoEdit(v:View)
}

interface RadioClickListener{
    fun onRadioClick(v:View)
}