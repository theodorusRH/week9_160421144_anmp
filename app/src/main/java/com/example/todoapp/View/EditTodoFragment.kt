package com.example.todoapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.Model.Todo
import com.example.todoapp.R
import com.example.todoapp.ViewModel.DetailTodoViewModel
import com.example.todoapp.databinding.FragmentCreateTodoBinding
import com.example.todoapp.databinding.FragmentEditTodoBinding


class EditTodoFragment : Fragment(), RadioClickListener, TodoEditClickListener {

    private lateinit var binding: FragmentEditTodoBinding
    private lateinit var viewModel:DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        binding.txtJudul.text = "Edit Todo"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid

        viewModel.fetch(uuid)

        binding.radiolistener = this

        binding.saveListener= this

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {

            //it adalah object t0d0
            binding.todo = it
        })
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }

    override fun onTodoEdit(v: View) {
        viewModel.updateTodo(binding.todo!!)
        Toast.makeText(context,"Todo updated",Toast.LENGTH_SHORT).show()
    }
}