package com.simbirsoft.task.presentation.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.simbirsoft.task.R
import com.simbirsoft.task.common.Colors
import com.simbirsoft.task.common.createLocalDateFromMillis
import com.simbirsoft.task.common.toFormatDate
import com.simbirsoft.task.common.toFormatTime
import com.simbirsoft.task.databinding.FragmentAddTaskBinding
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.presentation.viewModels.AddTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@AndroidEntryPoint
class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentAddTaskBinding is null")

    private val viewModel by viewModels<AddTaskViewModel>()

    private var color = 0
    private var startDate = LocalDate.now()
    private var startTime = LocalTime.now()
    private var startDateTime = LocalDateTime.of(startDate, startTime)
    private var endDate = LocalDate.now()
    private var endTime = LocalTime.of(startTime.plusHours(1).hour, startTime.minute)
    private var endDateTime = LocalDateTime.of(endDate, endTime)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChipGroup()
        setDateTimeClickListeners()
        setCurrentDateTime()

        binding.etTaskName.doOnTextChanged { text, start, count, after ->
            binding.tvTaskName.error = null
        }

        binding.buttonSave.setOnClickListener {
            if (binding.etTaskName.text.toString().isBlank()) {
                binding.tvTaskName.error = requireContext().getString(R.string.input_error)
            } else {
                addTask()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCurrentDateTime() {
        binding.startDate.text = startDate.toFormatDate()
        binding.endDate.text = endDate.toFormatDate()
        binding.startTime.text = startTime.toFormatTime()
        binding.endTime.text = endTime.toFormatTime()
    }

    private fun setDateTimeClickListeners() {
        binding.startDate.setOnClickListener { showStartDatePicker() }
        binding.endDate.setOnClickListener { showEndDatePicker() }
        binding.startTime.setOnClickListener { showStartTimePicker() }
        binding.endTime.setOnClickListener { showEndTimePicker() }
    }

    private fun showStartDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()
        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            startDate = dateInMillis.createLocalDateFromMillis()
            endDate = dateInMillis.createLocalDateFromMillis()
            binding.startDate.text = startDate.toFormatDate()
            binding.endDate.text = endDate.toFormatDate()
        }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun showStartTimePicker() {
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(startTime.hour)
                .setMinute(startTime.minute).setTitleText("Select time").build()
        timePicker.addOnPositiveButtonClickListener {
            startTime = LocalTime.of(timePicker.hour, timePicker.minute)
            endTime = LocalTime.of(timePicker.hour + 1, timePicker.minute)
            binding.startTime.text = startTime.toFormatTime()
            binding.endTime.text = endTime.toFormatTime()
            startDateTime = LocalDateTime.of(startDate, startTime)
            endDateTime = LocalDateTime.of(endDate, endTime)
        }
        timePicker.show(childFragmentManager, "timePicker")
    }

    private fun showEndDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()
        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            endDate = dateInMillis.createLocalDateFromMillis()
            binding.endDate.text = endDate.toFormatDate()
        }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun showEndTimePicker() {
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(endTime.hour)
                .setMinute(endTime.minute).setTitleText("Select time").build()
        timePicker.addOnPositiveButtonClickListener {
            if (getTimeInMinutes(timePicker.hour, timePicker.minute) < getTimeInMinutes(startTime.hour, startTime.minute)) {
                endTime = LocalTime.of(startTime.hour + 1, startTime.minute)
                binding.endTime.text = endTime.toFormatTime()
            } else {
                endTime = LocalTime.of(timePicker.hour, timePicker.minute)
                binding.endTime.text = endTime.toFormatTime()
                endDateTime = LocalDateTime.of(endDate, endTime)
            }
        }
        timePicker.show(childFragmentManager, "timePicker")
    }

    private fun getTimeInMinutes(hour: Int, minute: Int): Int = hour * 60 + minute

    private fun addTask() {
        val title = binding.etTaskName.text.toString()
        val content = binding.etTaskDescription.text.toString().ifBlank { "" }
        val task = Task(
            startDate = startDateTime,
            endDate = endDateTime,
            color = color,
            title = title,
            content = content
        )
        viewModel.addTask(task)
    }

    private fun createChipGroup() {
        val colors = Colors.entries
        colors.forEach { color ->
            val chip = Chip(requireContext())
            chip.chipStrokeWidth = 0f
            chip.chipBackgroundColor = ColorStateList.valueOf(color.hex.toColorInt())
            binding.chipGroupColor.addView(chip)
            chip.setOnClickListener { this.color = color.hex.toColorInt() }
        }
    }
}