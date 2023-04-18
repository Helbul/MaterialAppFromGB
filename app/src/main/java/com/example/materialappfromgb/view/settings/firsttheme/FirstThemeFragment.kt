package com.example.materialappfromgb.view.settings.firsttheme

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.materialappfromgb.FirstThemeViewModel
import com.example.materialappfromgb.R
import com.example.materialappfromgb.databinding.FragmentFirstThemeBinding
import com.example.materialappfromgb.databinding.FragmentSettingsBinding
import com.example.materialappfromgb.view.settings.*

private const val KEY_SP_LOCAL = "KEY_SP_LOCAL"
private const val KEY_CURRENT_THEME_LOCAL = "KEY_CURRENT_THEME_LOCAL"
private const val ThemeOne = 0
private const val ThemeSecond = 1

class FirstThemeFragment : Fragment() {

    private var _binding: FragmentFirstThemeBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FirstThemeFragment()
    }

    private lateinit var viewModel: FirstThemeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context : Context = ContextThemeWrapper(activity, R.style.IndigoTheme)
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentFirstThemeBinding.inflate(localInflater)
        return binding.root
//        return inflater.inflate(R.layout.fragment_first_theme, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//            setCurrentThemeLocal(ThemeOne)
//            redrawFragment()
//
//    }

    override fun onStart() {
        super.onStart()
        setCurrentThemeLocal(ThemeOne)
        redrawFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstThemeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setCurrentThemeLocal(currentTheme: Int) {
        val sharedPreferences = requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME_LOCAL, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences = requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int) : Int {
        return when (currentTheme) {
            ThemeOne -> R.style.IndigoTheme
            ThemeSecond -> R.style.PinkTheme
            else -> {R.style.Theme_MaterialAppFromGB}
        }
    }

    private fun redrawFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottom_navigation_container, SettingsFragment.newInstance())
            .commit()
    }

}