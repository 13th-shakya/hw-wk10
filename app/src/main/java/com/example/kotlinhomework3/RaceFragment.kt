package com.example.kotlinhomework3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlinhomework3.databinding.FragmentRaceBinding

class RaceFragment : Fragment() {
    private var _binding: FragmentRaceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var rabbitProgress = 0
    private var turtleProgress = 0

    private val handler = Handler(Looper.myLooper()!!) { message: Message ->
        when (message.what) {
            1 -> binding.sbRabbit.progress = rabbitProgress
            2 -> binding.sbTurtle.progress = turtleProgress
        }
        if (rabbitProgress >= 100 && turtleProgress < 100) {
            Toast.makeText(requireContext(), "兔子勝", Toast.LENGTH_SHORT).show()
            binding.btnStart.isEnabled = true
        } else if (turtleProgress >= 100 && rabbitProgress < 100) {
            Toast.makeText(requireContext(), "烏龜勝", Toast.LENGTH_SHORT).show()
            binding.btnStart.isEnabled = true
        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnStart.setOnClickListener {
            binding.btnStart.isEnabled = false;
            rabbitProgress = 0;
            turtleProgress = 0;
            binding.sbRabbit.progress = 0;
            binding.sbTurtle.progress = 0;
            runRabbit();
            runTurtle();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun runRabbit() {
        Thread {
            while (rabbitProgress <= 100 && turtleProgress < 100) {
                Thread.sleep(100)
                if (Math.random() > (1.0 / 3)) {
                    Thread.sleep(300)
                }

                rabbitProgress += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle() {
        Thread {
            while (turtleProgress <= 100 && rabbitProgress < 100) {
                Thread.sleep(100)

                turtleProgress++
                val msg = Message()
                msg.what = 2
                handler.sendMessage(msg)
            }
        }.start()
    }
}
