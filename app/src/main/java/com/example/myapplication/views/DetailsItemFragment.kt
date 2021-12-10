package com.example.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.views.viewmodels.MainViewModel
import com.example.myapplication.models.Result


class DetailsItemFragment : Fragment() {

    private val moviesViewModel: MainViewModel by activityViewModels()
    private lateinit var selectItemModel: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageMovie: ImageView = view.findViewById(R.id.details_item_imageView)
        val nameOfMovie: TextView = view.findViewById(R.id.details_name_textView)
        val descriptionOfMovie: TextView = view.findViewById(R.id.details_description_textView)
        val wathcedLater: Button = view.findViewById(R.id.details_watch_later_button)
        val watched: Button = view.findViewById(R.id.details_watched_button)

        moviesViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${it.posterPath}")
                .into(imageMovie)
            nameOfMovie.text = it.originalTitle
            descriptionOfMovie.text = it.overview
            selectItemModel = it
        })

        /* wathcedLater.setOnClickListener {
             moviesViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, Observer {
                 it.iswatchedLater = true
                 Toast.makeText(requireActivity(), "Add to watch later",
                     Toast.LENGTH_SHORT).show()
             })


         }
         watched.setOnClickListener {
             moviesViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, Observer {
                 it.isWatched = true
                 Toast.makeText(requireActivity(), "Add to watched",
                     Toast.LENGTH_SHORT).show()
             })
         }*/
    }
}