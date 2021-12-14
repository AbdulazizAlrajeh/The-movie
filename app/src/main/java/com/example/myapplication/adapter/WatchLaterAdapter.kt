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
import com.bumptech.glide.Glide
import com.example.myapplication.models.Result
import com.example.myapplication.views.viewmodels.WatchLaterViewModel
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchLaterAdapter(val viewmodel: WatchLaterViewModel, val context: Context) :
    RecyclerView.Adapter<WatchLaterAdapter.ViewModeler>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

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
        val newResult = mutableMapOf<String, Any>()

        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
            .into(holder.imageMove)
        holder.movieName.text = "Movie: ${item.title}"
        holder.descriptionMovie.text = "Overview: ${item.overview}"

        holder.watchedButton.setOnClickListener {

            newResult["watched"] = true
            newResult["iswatchedLater"] = false
            viewmodel.updateItem(item, newResult)
        }

        holder.deleteButton.setOnClickListener {
            item.iswatchedLater = false
            viewmodel.deleteItem(item)
        }

    }

    fun submitList(list: List<Result>) {
        differ.submitList(list)


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewModeler(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageMove: ImageView = itemView.findViewById(R.id.imageView)
        val movieName: TextView = itemView.findViewById(R.id.login_title_textView)
        val descriptionMovie: TextView = itemView.findViewById(R.id.description_textView)
        val watchedButton: ImageButton = itemView.findViewById(R.id.eye_imageButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_imageButton)
    }
}