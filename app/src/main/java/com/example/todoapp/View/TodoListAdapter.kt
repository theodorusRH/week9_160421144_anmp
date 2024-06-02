package com.example.todoapp.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Model.Todo
import com.example.todoapp.databinding.TodoItemLayoutBinding

class TodoListAdapter(val todoList: ArrayList<Todo>,val adapterOnClick : (Todo) -> Unit)
    : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),
        TodoCheckedChangeListener,
        TodoEditClickListener
    {

    class TodoViewHolder(
        var binding: TodoItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.todo = todoList[position]

        holder.binding.listener = this
        holder.binding.editListener = this
        holder.binding.checkTask.isChecked = false

//        holder.binding.checkTask.text= todoList[position].title
//        holder.binding.checkTask.isChecked = false

        holder.binding.imgEdit.setOnClickListener{
            val action = TodoListFragmentDirections.actioneditTodoFragment(todoList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.checkTask.setOnCheckedChangeListener{compoundButton,
                                                    b->
            if(compoundButton.isPressed){
                adapterOnClick(todoList[position])
            }
        }
    }

    fun updateTodoList(newtodoList:List<Todo>){
        todoList.clear()
        todoList.addAll(newtodoList)
        notifyDataSetChanged()
    }


    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(cb.isPressed){
                adapterOnClick(obj)
        }
    }

    override fun onTodoEdit(v: View) {

        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actioneditTodoFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}