package com.example.kotlinhomework3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhomework3.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var contactsAdapter: ContactsAdapter
    private val contacts = ArrayList<ContactsItem>()

    // 宣告 ActivityResultLauncher。
    // 內部負責處理 SecActivity 回傳結果
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 取得回傳的 Intent，並從 Intent 中取得聯絡人資訊
            val intent = result.data
            val name = intent?.getStringExtra("name") ?: ""
            val phone = intent?.getStringExtra("phone") ?: ""
            // 新增聯絡人資料
            contacts.add(ContactsItem(name, phone))
            // 更新列表
            contactsAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 創建 LinearLayoutManager 物件，設定垂直排列
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = linearLayoutManager

        // 創建 MyAdapter 並連結 recyclerView
        contactsAdapter = ContactsAdapter(contacts)
        binding.recyclerView.adapter = contactsAdapter

        // 設定按鈕監聽器，使用 startForResult 前往 SecActivity
        binding.btnAdd.setOnClickListener {
            val i = Intent(requireContext(), ContactsAddActivity::class.java)
            startForResult.launch(i)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
