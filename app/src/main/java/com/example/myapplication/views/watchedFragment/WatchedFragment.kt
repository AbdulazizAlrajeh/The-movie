package com.example.myapplication.views.watchedFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentWatchedBinding
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "WatchedFragment"

class WatchedFragment : Fragment() {

    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private lateinit var binding: FragmentWatchedBinding
    private lateinit var watchedSameMainAdapter: WatchedAdapter
    private val watchedViewModel: WatchedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchedSameMainAdapter = WatchedAdapter(watchedViewModel, requireContext())
        binding.watchedRecyclerview.adapter = watchedSameMainAdapter

        observers()
        watchedViewModel.callWatchedMovies(userId)
    }

    fun observers() {
        watchedViewModel.watchedLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                Log.d(TAG, it.toString())
                binding.emptyTextView.visibility = View.VISIBLE
            }else{
                Log.d(TAG, it.toString())
                binding.watchedProgressBar.animate().alpha(0f).setDuration(1000)
                watchedSameMainAdapter.submitList(it)
                binding.watchedRecyclerview.animate().alpha(1f)
                Log.d(TAG, it.toString())
            }

        })

        watchedViewModel.watchedMoviesLiveDataException.observe(viewLifecycleOwner, {

            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                watchedViewModel.watchedMoviesLiveDataException.postValue(null)
            }
        })
    }
}