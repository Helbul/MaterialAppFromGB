package com.example.materialappfromgb.view.wiki

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.materialappfromgb.databinding.FragmentWikiBinding
import com.example.materialappfromgb.databinding.FragmentWikiStartBinding
import com.example.materialappfromgb.viewmodel.WikiViewModel

class WikiFragment : Fragment() {

    companion object {
        fun newInstance() = WikiFragment()
    }

    private var _binding: FragmentWikiStartBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: WikiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWikiStartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }
}