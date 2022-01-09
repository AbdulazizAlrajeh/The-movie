package com.example.myapplication.views.profileFragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy


class ProfileFragment : Fragment() {
    private val IMAGE_PICKER = 0
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userImageView.isEnabled = false
        observers()
        profileViewModel.callUserProfile()

        binding.progressBar.visibility = View.VISIBLE
        binding.EditButton.setOnClickListener {
            binding.EditButton.visibility = View.INVISIBLE
            binding.userImageView.isEnabled = true
            binding.firstNameProfile.isEnabled = true
            binding.lastNameProfile.isEnabled = true
            binding.cancelButton.visibility = View.VISIBLE
            binding.saveButton.visibility = View.VISIBLE


        }
        binding.saveButton.setOnClickListener {
            saveEdit()

            binding.EditButton.visibility = View.VISIBLE
            binding.userImageView.isEnabled = false
            binding.firstNameProfile.isEnabled = false
            binding.lastNameProfile.isEnabled = false
            binding.cancelButton.visibility = View.INVISIBLE
            binding.saveButton.visibility = View.INVISIBLE
        }
        binding.cancelButton.setOnClickListener {
            binding.EditButton.visibility = View.VISIBLE
            binding.userImageView.isEnabled = false
            binding.firstNameProfile.isEnabled = false
            binding.lastNameProfile.isEnabled = false
            binding.cancelButton.visibility = View.INVISIBLE
            binding.saveButton.visibility = View.INVISIBLE
        }

        binding.userImageView.setOnClickListener {
            ImagePicker()
        }
       // showImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            //  progressDialog.show()
            val imagePath = Matisse.obtainResult(data)[0]
            Log.d("xxxxxxxxxxx", imagePath.toString())
            profileViewModel.UploadPhoto(imagePath)
            binding.userImageView.setImageURI(imagePath)
        }
    }

    fun observers() {
        profileViewModel.userProfileLiveData.observe(viewLifecycleOwner, {
            it?.let {
                binding.firstNameProfile.setText(it.firstName)
                binding.lastNameProfile.setText(it.lastName)
                binding.emailProfile.setText(it.email)
                Glide.with(this)
                    .load(it.imageUsers)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.profile)
                    .into(binding.userImageView)
                binding.progressBar.animate().alpha(0f).setDuration(1000)
            }


        })

        profileViewModel.userProfileLiveDataException.observe(viewLifecycleOwner, {
            Log.d("Error", it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            profileViewModel.userProfileLiveDataException.postValue(null)
        })
    }

    fun saveEdit() {
        val firstName = binding.firstNameProfile.text.toString()
        val lastName = binding.lastNameProfile.text.toString()
        val users = Users(firstName, lastName)
        profileViewModel.saveUpdateUser(users)
    }

    fun ImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.example.myapplication"))
            .forResult(IMAGE_PICKER)
    }

    fun showImage(imageUri : String) {
       // var imageUri = "https://firebasestorage.googleapis.com/v0/b/the-movie-10cd7.appspot.com/o/${FirebaseAuth.getInstance().uid.toString()}?alt=media&token=c7dc37fa-7127-4113-8c83-0ebfcf36896c"
        Log.d("ImageUri",imageUri.toString())
        Glide.with(this)
            .load(imageUri)
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.profile)
            .into(binding.userImageView)
    }

}