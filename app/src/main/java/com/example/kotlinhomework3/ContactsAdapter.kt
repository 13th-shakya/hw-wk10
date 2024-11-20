package com.example.kotlinhomework3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinhomework3.databinding.ActivityContactsAddBinding
import com.example.kotlinhomework3.databinding.AdapterContactsBinding

class ContactsAdapter(
    private val data: ArrayList<ContactsItem>
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    // 實作 RecyclerView.ViewHolder 來儲存View
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = AdapterContactsBinding.bind(v)

        fun bind(item: ContactsItem, clickListener: (ContactsItem) -> Unit) {
            binding.tvName.text = item.name
            binding.tvPhone.text = item.phone
            // 設定監聽器
            binding.imgDelete.setOnClickListener {
                // 呼叫 clickListener 回傳刪除的資料
                clickListener.invoke(item)
            }
        }
    }

    // 建立ViewHolder與Layout並連結彼此
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_contacts, viewGroup, false)
        return ViewHolder(v)
    }

    // 回傳資料數量
    override fun getItemCount() = data.size

    // 將資料指派給 ViewHolder 顯示
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position]) { item ->
            // 使用 remove() 刪除指定的資料
            data.remove(item)
            notifyDataSetChanged()
        }
    }
}
