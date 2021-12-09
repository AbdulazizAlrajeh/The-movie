package com.example.myapplication.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.adapterimport.MainAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.views.viewmodels.MainViewModel
import com.example.myapplication.models.Result


private const val TAG = "MainFragment"

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private var listOfMovies = listOf<Result>()

    private lateinit var mainAdapter: MainAdapter
    private val moviesViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainAdapter = MainAdapter(moviesViewModel, requireContext())
        binding.mainRecyclerview.adapter = mainAdapter

        observers()

        moviesViewModel.callGetMovies()


    }

    fun observers() {
        moviesViewModel.moviesLiveDate.observe(viewLifecycleOwner, {
            Log.d(TAG, it.toString())
            binding.mainProgressbar.animate().alpha(0f).setDuration(1000)
            mainAdapter.submitList(it)
            listOfMovies = it
            binding.mainRecyclerview.animate().alpha(1f)
            Log.d(TAG, it.toString())

        })

        moviesViewModel.moviesErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                moviesViewModel.moviesErrorLiveData.postValue(null)
            }
        })
    }


}