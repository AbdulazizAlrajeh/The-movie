package com.example.myapplication.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapterimport.MainAdapter
import com.example.myapplication.views.viewmodels.MainViewModel
import com.example.myapplication.models.Result


private const val TAG = "MainFragment"

class MainFragment : Fragment() {
    var loading = true

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var listOfMovies = listOf<Result>()

    private lateinit var mainAdapter: MainAdapter
    private val moviesViewModel: MainViewModel by activityViewModels()


    private lateinit var layoutManager: GridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainAdapter = MainAdapter(moviesViewModel, requireContext())
        recyclerView = view.findViewById(R.id.main_recyclerview)
        progressBar = view.findViewById(R.id.main_progressbar)
        recyclerView.adapter = mainAdapter
        layoutManager = GridLayoutManager(requireActivity(), 3)

        recyclerView.layoutManager = layoutManager
        observers()

        moviesViewModel.callGetMovies()
        addItemScroll()

    }

    fun addItemScroll() {
        var loading = true
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = recyclerView.getChildCount()
                    totalItemCount = recyclerView.layoutManager!!.getItemCount()
                    pastVisiblesItems = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            Log.d("callMore", "Last Item Wow !")
                            progressBar.visibility = View.VISIBLE
                            moviesViewModel.callGetMovies()
                            // Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        })
    }

    fun observers() {
        moviesViewModel.moviesLiveDate.observe(viewLifecycleOwner, {
            loading = true
            Log.d(TAG, it.toString())
            progressBar.animate().alpha(0f).setDuration(1000)
            mainAdapter.submitList(it)
            listOfMovies = it
            recyclerView.animate().alpha(1f)
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