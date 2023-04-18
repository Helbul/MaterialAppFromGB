package com.example.materialappfromgb.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.materialappfromgb.MainActivity
import com.example.materialappfromgb.R
import com.example.materialappfromgb.databinding.FragmentPictureBinding
import com.example.materialappfromgb.view.settings.SettingsFragment
import com.example.materialappfromgb.viewmodel.AppState
import com.example.materialappfromgb.viewmodel.PictureOfTheDayViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PictureFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private var _binding: FragmentPictureBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root

    }

    val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner
        ) {
            renderData(it)
        }

        viewModel.sendRequest()
    }

    private fun renderData (appState: AppState) {
        when(appState){
            is AppState.Error -> TODO()
            AppState.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            is AppState.Success -> {
                binding.imageView.load(appState.pictureOfTheDayResponseData.url)
                //Нстроить загрузку изображения erorr() placeholder()
                binding.textviewFirst.text = appState.pictureOfTheDayResponseData.title
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}