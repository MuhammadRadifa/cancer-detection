package com.dicoding.asclepius.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.HistoryEntity
import com.dicoding.asclepius.databinding.HistoryCardBinding
import com.dicoding.asclepius.view.result.ResultActivity


class HistoryAdapter: ListAdapter<HistoryEntity, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    class MyViewHolder(private val binding: HistoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryEntity){
            binding.textViewLabel.text = history.result
            binding.textViewTime.text = history.date

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, history.uri)
                intent.putExtra(ResultActivity.EXTRA_RESULT, history.result)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}