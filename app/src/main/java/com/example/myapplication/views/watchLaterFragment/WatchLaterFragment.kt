package com.example.myapplication.views.watchLaterFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentWatchLaterBinding
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "WatchLaterFragment"

class WatchLaterFragment : Fragment() {

    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private lateinit var binding: FragmentWatchLaterBinding


    private lateinit var watchLaterAdapter: WatchLaterAdapter

    private val watchLaterViewModel: WatchLaterViewModel by activityViewModels()

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

        watchLaterAdapter = WatchLaterAdapter(watchLaterViewModel, requireContext())
        binding.watchLaterRecyclerView.adapter = watchLaterAdapter

        observers()

        watchLaterViewModel.getMoviesWatchLater(userId)

    }


    fun observers() {
        watchLaterViewModel.watchLaterLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                Log.d("ListElse", it.toString())
                binding.watchLaterProgressBar.animate().alpha(0f).setDuration(1000)
                binding.emptyTextView.visibility = View.VISIBLE

            }else{
                Log.d("List", it.toString())
                binding.watchLaterProgressBar.animate().alpha(0f).setDuration(1000)
                watchLaterAdapter.submitList(it)
                binding.watchLaterRecyclerView.animate().alpha(1f)
                Log.d("List", it.toString())
            }

        })

        watchLaterViewModel.watchLaterMoviesLiveDataException.observe(viewLifecycleOwner, {
            it?.let {
                Log.d("Error", it)
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                watchLaterViewModel.watchLaterMoviesLiveDataException.postValue(null)
            }
        })
    }

}