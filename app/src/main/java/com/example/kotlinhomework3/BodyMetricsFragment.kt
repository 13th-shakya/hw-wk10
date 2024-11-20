package com.example.kotlinhomework3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlinhomework3.databinding.FragmentBodyMetricsBinding
import kotlin.math.pow

class BodyMetricsFragment : Fragment() {
    private var _binding: FragmentBodyMetricsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBodyMetricsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCalculate.setOnClickListener {
            when {
                binding.edHeight.text.isEmpty() -> showToast("請輸入身高")
                binding.edWeight.text.isEmpty() -> showToast("請輸入體重")
                binding.edAge.text.isEmpty() -> showToast("請輸入年齡")
                else -> runThread() // 執行 runThread 方法
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

    // 用 Thread 模擬檢測過程
    private fun runThread() {
        // 顯示進度條
        binding.llProgress.visibility = View.VISIBLE

        Thread {
            var progress = 0
            // 建立迴圈執行一百次共延長五秒
            while (progress < 100) {
                // 執行緒延遲 50ms 後執行
                Thread.sleep(50)
                // 計數加一
                progress++
                // 切換到 Main Thread 執行進度更新
                runOnUiThread {
                    binding.progressBar.progress = progress
                    binding.tvProgress.text = "$progress%"
                }
            }

            val height = binding.edHeight.text.toString().toDouble() // 身高
            val weight = binding.edWeight.text.toString().toDouble() // 體重
            val age = binding.edAge.text.toString().toDouble() // 年齡
            val bmi = weight / ((height / 100).pow(2)) // BMI
            // 計算男女的體脂率並使用 Pair 類別進行解構宣告
            val (standWeight, bodyFat) = if (binding.btnBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            // 切換到 Main Thread 更新畫面
            runOnUiThread {
                binding.llProgress.visibility = View.GONE
                binding.tvWeightResult.text = "標準體重 \n${String.format("%.2f", standWeight)}"
                binding.tvFatResult.text = "體脂肪 \n${String.format("%.2f", bodyFat)}"
                binding.tvBmiResult.text = "BMI \n${String.format("%.2f", bmi)}"
            }
        }.start()
    }
}
}
