package com.example.myapplication.views.profileFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.models.Users


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        profileViewModel.callUserProfile()

        binding.saveButton.setOnClickListener {
            saveEdit()
        }

    }

    fun observers(){
        profileViewModel.userProfileLiveData.observe(viewLifecycleOwner,{
            it?.let {
                binding.firstNameProfile.setText(it.firstName)
                binding.lastNameProfile.setText(it.lastName)
                binding.emailProvile.setText(it.email)
            }


        })

        profileViewModel.userProfileLiveDataException.observe(viewLifecycleOwner,{
            Log.d("Error", it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            profileViewModel.userProfileLiveDataException.postValue(null)
        })
    }

    fun saveEdit(){
        val firstName = binding.firstNameProfile.text.toString()
        val lastName = binding.lastNameProfile.text.toString()
        val users = Users(firstName,lastName)
        profileViewModel.saveUpdateUser(users)
    }

}