package com.simbirsoft.task.presentation.taskDetails

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.simbirsoft.task.R
import com.simbirsoft.task.common.toFormatLocalDateTime
import com.simbirsoft.task.databinding.FragmentTaskDetailsBinding
import com.simbirsoft.task.domain.model.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding: FragmentTaskDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskDetailsBinding is null")

    private val args by navArgs<TaskDetailsFragmentArgs>()
    private val viewModel by viewModels<TaskDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTaskById(args.taskId)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.task.collect { task ->
                    task?.let { setInfo(it) }
                }
            }
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.task.value?.let { task -> createDeleteDialogAlert(task) }
        }
    }

    private fun setInfo(task: Task) {
        binding.tvDetailsTaskName.text = task.title
        binding.tvDetailsTaskDescription.text = if (task.content == "") "No description" else task.content
        binding.tvDetailsStartDateTime.text = task.startDate.toFormatLocalDateTime()
        binding.tvDetailsEndDateTime.text = task.endDate.toFormatLocalDateTime()
        binding.colorCircle.backgroundTintList = ColorStateList.valueOf(task.color)
    }

    private fun createDeleteDialogAlert(task: Task) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_task_title, task.title))
            .setMessage(resources.getString(R.string.delete_task_dialog_description))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                viewModel.removeTask(task.id)
                findNavController().navigateUp()
                Toast.makeText(requireContext(), "Task removed", Toast.LENGTH_LONG).show()
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}