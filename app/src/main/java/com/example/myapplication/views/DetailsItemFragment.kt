package com.example.myapplication.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.views.viewmodels.MainViewModel
import com.example.myapplication.models.Result
import com.example.myapplication.util.DateConvert
import com.example.myapplication.views.viewmodels.SaveToFirebaseViewModel
import com.example.myapplication.views.viewmodels.WatchLaterViewModel
import com.example.myapplication.views.viewmodels.WatchedViewModel
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "DetailsItemFragment"

class DetailsItemFragment : Fragment() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val moviesViewModel: MainViewModel by activityViewModels()
    private val WatchLaterViewModel: SaveToFirebaseViewModel by activityViewModels()
    val dateConvert = DateConvert()
    private lateinit var selectItemModel: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_item, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageMovie: ImageView = view.findViewById(R.id.details_item_imageView)
        val nameOfMovie: TextView = view.findViewById(R.id.details_name_textView)
        val descriptionOfMovie: TextView = view.findViewById(R.id.details_description_textView)
        val dateMovie : TextView = view.findViewById(R.id.date_textView)
        val wathcedLater: Button = view.findViewById(R.id.details_watch_later_button)
        val watched: Button = view.findViewById(R.id.details_watched_button)
        observers()
        moviesViewModel.selectedItemMutableLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${it.posterPath}")
                .into(imageMovie)
            nameOfMovie.text = "Name The Movie: ${it.title}"
            descriptionOfMovie.text = "Description:\n\t${it.overview}"
            val date = dateConvert.convertDataUsing_YY_MM_dd_("${it.releaseDate}")
            dateMovie.text = "Date:${date}"
            selectItemModel = it
        })

        wathcedLater.setOnClickListener {
            selectItemModel.iswatchedLater = true
            selectItemModel.userId = userId
            WatchLaterViewModel.callSaveMovieToFirebase(selectItemModel)


        }
        watched.setOnClickListener {
            selectItemModel.isWatched = true
            Log.d(TAG, "${selectItemModel.isWatched}")
            selectItemModel.userId = userId
            WatchLaterViewModel.callSaveMovieToFirebase(selectItemModel)

        }
    }

    fun observers() {
        WatchLaterViewModel.saveToFirebaseLiveDataCorrect.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(TAG, it.toString())
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                WatchLaterViewModel.saveToFirebaseLiveDataCorrect.postValue(null)
                findNavController().navigate(R.id.action_detailsItemFragment_to_mainFragment2)


            }
        })

        WatchLaterViewModel.saveToFirebaseLiveDataException.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(TAG, it.toString())
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                WatchLaterViewModel.saveToFirebaseLiveDataException.postValue(null)
                findNavController().navigate(R.id.action_detailsItemFragment_to_mainFragment2)

            }

        })

    }


}