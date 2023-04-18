package com.example.materialappfromgb.view.settings.firsttheme

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
    }

    override fun onStart() {
        super.onStart()

        val themeLocal = getCurrentThemeLocal()
        if (themeLocal != ThemeOne) {
            setCurrentThemeLocal(ThemeOne)
            redrawFragment()
        }
        Log.d("OLGA", "onStart: ThemeOne")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstThemeViewModel::class.java)
    }

    private fun setCurrentThemeLocal(currentTheme: Int) {
        val sharedPreferences = requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME_LOCAL, currentTheme)
        editor.apply()
    }


    private fun redrawFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottom_navigation_container, SettingsFragment.newInstance())
            .commit()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }

}