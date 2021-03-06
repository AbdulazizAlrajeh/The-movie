package com.example.myapplication.views.mainFragment

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.myapplication.models.Result

class MainAdapter(val viewmodel: MainViewModel, val context: Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.main_and_watched_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.nameOfMovie.text = item.title
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
            .into(holder.moviePicture)

        holder.itemView.setOnClickListener {
            viewmodel.selectedItemMutableLiveData.postValue(item)
            it.findNavController().navigate(R.id.action_mainFragment2_to_detailsItemFragment)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Result>) {
        differ.submitList(list)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePicture: ImageView = itemView.findViewById(R.id.main_item_imageview)
        val nameOfMovie: TextView = itemView.findViewById(R.id.main_item_textview)
    }
}