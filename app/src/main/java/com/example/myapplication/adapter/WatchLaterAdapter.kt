package com.example.myapplication.adapterimport

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.models.Result
import com.example.myapplication.views.viewmodels.MainViewModel

class WatchLaterAdapter( val context: Context) :
    RecyclerView.Adapter<WatchLaterAdapter.ViewModeler>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return  oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchLaterAdapter.ViewModeler {
        return ViewModeler(
            LayoutInflater.from(parent.context).inflate(
                R.layout.watch_later__item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewModeler, position: Int) {
        val item = differ.currentList[position]

    }
    fun submitList(list: List<Result>) {
        differ.submitList(list)


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewModeler(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageMove : ImageView = itemView.findViewById(R.id.imageView)
        val movieNmae : TextView = itemView.findViewById(R.id.login_title_textView)
        val descriptionMovie : TextView = itemView.findViewById(R.id.description_textView)
        val watchedButton : ImageButton = itemView.findViewById(R.id.eye_imageButton)
        val deleteButton : ImageButton = itemView.findViewById(R.id.delete_imageButton)
    }
}