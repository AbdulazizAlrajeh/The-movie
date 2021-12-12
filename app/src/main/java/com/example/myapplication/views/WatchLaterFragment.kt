package com.example.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.adapterimport.MainAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.databinding.FragmentWatchLaterBinding
import com.example.myapplication.models.Result


class WatchLaterFragment : Fragment() {

    private lateinit var binding: FragmentWatchLaterBinding

    private var listOfMovies = listOf<Result>()

    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}