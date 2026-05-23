package com.example.burbujas01


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

class TaskDialogFragment : DialogFragment() {

    private val vm: GraphViewModel by activityViewModels()

    companion object {
        private const val ARG_TASK_ID = "task_id"

        fun newInstance(taskId: String? = null) = TaskDialogFragment().apply {
            arguments = bundleOf(ARG_TASK_ID to taskId)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val taskId      = arguments?.getString(ARG_TASK_ID)
        val existingTask = taskId?.let { vm.getTask(it) }
        val isEdit      = existingTask != null

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_task, null)

        val etTitle       = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val spinnerPriority = view.findViewById<Spinner>(R.id.spinnerPriority)
        val spinnerStatus   = view.findViewById<Spinner>(R.id.spinnerStatus)

        // Populate spinners
        ArrayAdapter.createFromResource(requireContext(), R.array.priorities, android.R.layout.simple_spinner_item)
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerPriority.adapter = it }

        ArrayAdapter.createFromResource(requireContext(), R.array.statuses, android.R.layout.simple_spinner_item)
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerStatus.adapter = it }

        // Pre-fill if editing
        existingTask?.let { task ->
            etTitle.setText(task.title)
            etDescription.setText(task.description)
            spinnerPriority.setSelection(task.priority.ordinal)
            spinnerStatus.setSelection(task.status.ordinal)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(if (isEdit) "Editar tarea" else "Nueva tarea")
            .setView(view)
            .setPositiveButton(if (isEdit) "Guardar" else "Crear") { _, _ ->
                val title = etTitle.text.toString().trim()
                if (title.isEmpty()) { Toast.makeText(requireContext(), "El título es requerido", Toast.LENGTH_SHORT).show(); return@setPositiveButton }

                val priority = TaskPriority.values()[spinnerPriority.selectedItemPosition]
                val status   = TaskStatus.values()[spinnerStatus.selectedItemPosition]
                val desc     = etDescription.text.toString().trim()

                if (isEdit && existingTask != null) {
                    vm.updateTask(existingTask.copy(title = title, description = desc, priority = priority, status = status))
                } else {
                    vm.addTask(title, desc, priority,
                        x = (0.2f + Math.random() * 0.6f).toFloat(),
                        y = (0.2f + Math.random() * 0.6f).toFloat())
                }
            }
            .setNegativeButton("Cancelar", null)
            .apply {
                if (isEdit) setNeutralButton("Eliminar") { _, _ ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("¿Eliminar tarea?")
                        .setMessage("Se eliminarán también sus conexiones.")
                        .setPositiveButton("Eliminar") { _, _ -> vm.deleteTask(taskId!!) }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
            }
            .create()
    }
}