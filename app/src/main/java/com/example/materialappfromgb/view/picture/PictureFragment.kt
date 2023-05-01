package com.example.materialappfromgb.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.*
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

    var isFlag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner
        ) {
            renderData(it)
        }

        viewModel.sendRequest()

        binding.imageView.setOnClickListener() {
            isFlag = !isFlag

            val params = it.layoutParams as ConstraintLayout.LayoutParams

            val transitionSet = TransitionSet()
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000L
            changeImageTransform.duration = 2000L

            transitionSet.addTransition(changeBounds)
            transitionSet.addTransition(changeImageTransform)

            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            if (isFlag) {
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = CoordinatorLayout.LayoutParams.WRAP_CONTENT
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            binding.imageView.layoutParams = params
        }
    }

    private fun renderData (appState: AppState) {
        when(appState){
            is AppState.Error -> TODO()
            AppState.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            is AppState.Success -> {
                val myAutoTransaction = TransitionSet()
                myAutoTransaction.ordering = TransitionSet.ORDERING_SEQUENTIAL
                val slide = Slide(Gravity.END)
                slide.duration = 1000L
                val changeBounds = ChangeBounds()
                changeBounds.duration = 2000L
                myAutoTransaction.addTransition(changeBounds)
                myAutoTransaction.addTransition(slide)
                TransitionManager.beginDelayedTransition(binding.root, myAutoTransaction)
                binding.imageView.visibility = ImageView.VISIBLE
                binding.imageView.load(appState.pictureOfTheDayResponseData.url)
                //Нстроить загрузку изображения erorr() placeholder()
                binding.textviewFirst.visibility = TextureView.VISIBLE
                binding.textviewFirst.text = appState.pictureOfTheDayResponseData.title

            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}