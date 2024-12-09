package com.simbirsoft.task.presentation.customView

import android.graphics.RectF
import android.os.Parcelable
import android.view.View

class TaskViewState(
    superSavedState: Parcelable?,
    val taskList: List<TaskViewTaskModel>,
    val fieldRect: RectF
): View.BaseSavedState(superSavedState)