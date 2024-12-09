package com.simbirsoft.task.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.simbirsoft.task.databinding.FragmentTaskListBinding
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.presentation.viewModels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.lifecycle.repeatOnLifecycle
import kotlin.collections.listOf

@AndroidEntryPoint
class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding: FragmentTaskListBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskListBinding is null")

    private val viewModel by viewModels<TaskListViewModel>()
    private val taskList = mutableListOf<Task>()
    private var currentDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getTaskList.collect {
                    taskList.clear()
                    taskList.addAll(it)
                    sortAndPassListToTaskView(taskList, currentDate)
                }
            }
        }

        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            currentDate = LocalDate.of(year, month + 1, dayOfMonth)
            sortAndPassListToTaskView(taskList, currentDate)
        }

        binding.taskView.onTaskClickListener = { task ->
            findNavController().navigate(TaskListFragmentDirections.toTaskDetailsFragment(task.id))
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(TaskListFragmentDirections.toAddTaskFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sortAndPassListToTaskView(list: List<Task>, date: LocalDate) {
        binding.taskView.setTaskList(list.filter { it.startDate.toLocalDate() == date })
    }
}