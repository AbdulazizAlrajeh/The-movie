package com.example.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.adapterimport.WatchedAdapter
import com.example.myapplication.databinding.FragmentDetailsWatchedBinding
import com.example.myapplication.databinding.FragmentWatchedBinding
import com.example.myapplication.views.viewmodels.WatchedViewModel


class DetailsWatchedFragment : Fragment() {

    private lateinit var binding: FragmentDetailsWatchedBinding
    private val watchedViewModel: WatchedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsWatchedBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        watchedViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${it.posterPath}")
                .into(binding.detailsWatchedImageView)
            binding.detailsWatchedNameTextView.text = "Movie: ${it.title}"
            binding.destailsWatchedDescriptionTextView.text = "Overview: ${it.overview}"
        })

    }

}