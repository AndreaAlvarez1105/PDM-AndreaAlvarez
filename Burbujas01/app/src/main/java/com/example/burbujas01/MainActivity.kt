package com.example.burbujas01


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val vm: GraphViewModel by viewModels()
    private lateinit var canvas: GraphCanvasView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabEdge: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        canvas  = findViewById(R.id.graphCanvas)
        fabAdd  = findViewById(R.id.fabAddTask)
        fabEdge = findViewById(R.id.fabEdgeMode)

        // ── Observe ViewModel ─────────────────────────────────────────────────
        vm.tasks.observe(this) { tasks ->
            vm.edges.value?.let { edges -> canvas.submitData(tasks, edges) }
        }
        vm.edges.observe(this) { edges ->
            vm.tasks.value?.let { tasks -> canvas.submitData(tasks, edges) }
        }

        // ── Canvas callbacks ──────────────────────────────────────────────────
        canvas.onTaskClick = { task ->
            TaskDialogFragment.newInstance(task.id)
                .show(supportFragmentManager, "edit_task")
        }

        canvas.onTaskLongClick = { task ->
            // Show quick actions
            val options = arrayOf("✏️ Editar", "🔗 Conectar desde aquí", "🗑️ Eliminar")
            AlertDialog.Builder(this)
                .setTitle(task.title)
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> TaskDialogFragment.newInstance(task.id).show(supportFragmentManager, "edit")
                        1 -> {
                            // Iniciamos la conexión directamente desde esta tarea
                            canvas.startEdgeFrom(task.id)
                            updateEdgeFab()
                            Toast.makeText(this, "Toca otra tarea para conectar", Toast.LENGTH_SHORT).show()
                        }
                        2 -> vm.deleteTask(task.id)
                    }
                }.show()
        }

        canvas.onTaskMoved = { id, x, y -> vm.moveTask(id, x, y) }

        canvas.onEdgeRequest = onEdgeRequest@{ fromId, toId ->
            val from = vm.getTask(fromId)?.title ?: return@onEdgeRequest
            val to   = vm.getTask(toId)?.title   ?: return@onEdgeRequest

            if (vm.hasEdge(fromId, toId)) {
                // Offer to remove existing edge
                AlertDialog.Builder(this)
                    .setTitle("Conexión existente")
                    .setMessage("¿Eliminar la conexión entre \"$from\" y \"$to\"?")
                    .setPositiveButton("Eliminar") { _, _ -> vm.removeEdge(fromId, toId) }
                    .setNegativeButton("Cancelar", null)
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Conectar tareas")
                    .setMessage("¿Conectar \"$from\" con \"$to\"?")
                    .setPositiveButton("Conectar") { _, _ -> vm.addEdge(fromId, toId) }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }

            // Exit edge mode after one action
            canvas.isEdgeMode = false
            updateEdgeFab()
        }

        // ── FABs ──────────────────────────────────────────────────────────────
        fabAdd.setOnClickListener {
            TaskDialogFragment.newInstance().show(supportFragmentManager, "new_task")
        }

        fabEdge.setOnClickListener {
            canvas.isEdgeMode = !canvas.isEdgeMode
            updateEdgeFab()
            if (canvas.isEdgeMode)
                Toast.makeText(this, "Toca dos tareas para conectarlas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateEdgeFab() {
        fabEdge.setImageResource(
            if (canvas.isEdgeMode) android.R.drawable.ic_menu_close_clear_cancel
            else android.R.drawable.ic_menu_share
        )
    }
}