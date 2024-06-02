package com.example.todoapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.ViewModel.ListTodoViewModel
import com.example.todoapp.databinding.FragmentTodoListBinding


class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var viewModel:ListTodoViewModel
    private val adapter = TodoListAdapter(arrayListOf(),{item->viewModel.ClearTask(item)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodoListBinding.inflate(inflater,container,false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        binding.revViewTodo.layoutManager = LinearLayoutManager(context)
        binding.revViewTodo.adapter = adapter

        binding.btnFab.setOnClickListener {
            val action = TodoListFragmentDirections.actioncreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            adapter.updateTodoList(it)
            if(it.isEmpty()) {
                binding.revViewTodo?.visibility = View.GONE
                binding.txtError.setText("Error")
            } else {
                binding.revViewTodo?.visibility = View.VISIBLE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressLoad?.visibility = View.GONE
            } else {
                binding.progressLoad?.visibility = View.VISIBLE
            }
        })

        viewModel.todoLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.VISIBLE
            }
        })

    }

}