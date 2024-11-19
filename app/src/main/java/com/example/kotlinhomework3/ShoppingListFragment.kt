package com.example.kotlinhomework3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.kotlinhomework3.databinding.FragmentShoppingListBinding

class ShoppingListFragment : Fragment() {
    private var _binding: FragmentShoppingListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 儲存購買數量資訊
        val count = ArrayList<String>()
        // 儲存水果資訊
        val item = ArrayList<ShoppingListItem>()
        // 建立價格範圍
        val priceRange = IntRange(10, 100)
        // 從 R 類別讀取圖檔
        val array = resources.obtainTypedArray(R.array.image_list)
        for (index in 0 until array.length()) {
            // 水果圖片 Id
            val photo = array.getResourceId(index, 0)
            // 水果名稱
            val name = "水果${index + 1}"
            // 亂數產生價格
            val price = priceRange.random()
            // 新增可購買數量資訊
            count.add("${index + 1}個")
            // 新增水果資訊
            item.add(ShoppingListItem(photo, name, price))
        }
        // 釋放圖檔資源
        array.recycle()

        val context = requireContext()

        // 建立 ArrayAdapter 物件，並傳入字串與 simple_list_item_1.xml
        binding.spinner.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, count)

        binding.gridView.apply {
            // 設定橫向顯示列數
            numColumns = 3
            // 建立 MyAdapter 物件，並傳入 adapter_vertical 作為畫面
            adapter = ShoppingListAdapter(context, item, R.layout.adapter_vertical)
        }

        // 建立 MyAdapter 物件，並傳入 adapter_horizontal 作為畫面
        binding.listView.adapter = ShoppingListAdapter(context, item, R.layout.adapter_horizontal)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
