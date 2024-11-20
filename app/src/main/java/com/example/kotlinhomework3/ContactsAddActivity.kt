package com.example.kotlinhomework3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinhomework3.databinding.ActivityContactsAddBinding

class ContactsAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityContactsAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 設定按鈕監聽器，取得輸入的姓名與電話
        binding.btnSend.setOnClickListener {
            //判斷是否輸入資料
            when {
                binding.edName.text.isEmpty() -> showToast("請輸入姓名")
                binding.edPhone.text.isEmpty() -> showToast("請輸入電話")
                else -> {
                    val b = bundleOf(
                        "name" to binding.edName.text.toString(),
                        "phone" to binding.edPhone.text.toString(),
                    )
                    //使用 setResult() 回傳聯絡人資料
                    setResult(Activity.RESULT_OK, Intent().putExtras(b))
                    finish()
                }
            }
        }
    }

    // 建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
