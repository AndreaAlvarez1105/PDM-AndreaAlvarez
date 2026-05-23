package com.example.burbujas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuapp.databinding.FragmentBubbleTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BubbleTaskFragment : Fragment() {

    private var _binding: FragmentBubbleTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BubbleTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBubbleTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bubbles.observe(viewLifecycleOwner) { bubbles ->
            binding.bubbleView.setBubbles(bubbles)
        }

        binding.bubbleView.onBubbleClick = { task ->
            // Navega al detalle usando tu NavGraph
            val action = BubbleTaskFragmentDirections
                .actionBubbleToDetail(taskId = task.id)
            findNavController().navigate(action)
        }

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(
                BubbleTaskFragmentDirections.actionBubbleToCreate()
            )
        }

        viewModel.loadTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}