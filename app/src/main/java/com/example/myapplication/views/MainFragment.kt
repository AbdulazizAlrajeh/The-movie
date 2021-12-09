package com.example.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.adapterimport.MainAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.views.viewmodels.MainViewModel
import com.example.myapplication.models.Result

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private var allProducts = listOf<Result>()

    private lateinit var mainAdapter: MainAdapter
    private val moviesViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


}