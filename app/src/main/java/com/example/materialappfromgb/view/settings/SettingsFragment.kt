package com.example.materialappfromgb.view.settings

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.materialappfromgb.R
import com.example.materialappfromgb.databinding.FragmentSettingsBinding
import com.example.materialappfromgb.viewmodel.SettingsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val KEY_SP_LOCAL = "KEY_SP_LOCAL"
private const val KEY_CURRENT_THEME_LOCAL = "KEY_CURRENT_THEME_LOCAL"
private const val ThemeOne = 0
private const val ThemeSecond = 1
private const val FIRST_THEME_FRAGMENT = 0
private const val SECOND_THEME_FRAGMENT = 1

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context : Context = ContextThemeWrapper(activity, getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewPager.adapter = ViewPagerAdapter(this)

        setTabs()

        val themeLocal = getCurrentThemeLocal()
        when (themeLocal) {
            ThemeOne -> {
                val tab: TabLayout.Tab = binding.tabLayout.getTabAt(ThemeOne)!!
                tab.select()
            }
            ThemeSecond -> {
                val tab: TabLayout.Tab = binding.tabLayout.getTabAt(ThemeSecond)!!
                tab.select()
            }
            else -> {
            }
        }


//        val themeLocal = getCurrentThemeLocal()
//        when (themeLocal) {
//            ThemeOne -> binding.tabLayout.id = ThemeOne
//            ThemeSecond -> binding.tabLayout.id = ThemeSecond
//            else -> binding.tabLayout.id = ThemeSecond
//        }
//        Log.d("OLGA!!!!", "themeLocal = $themeLocal" )
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when (tab?.position) {
//                    ThemeOne -> {
//                        setCurrentThemeLocal(ThemeOne)
//                        redrawFragment()
//                    }
//                    ThemeSecond -> {
//                        setCurrentThemeLocal(ThemeSecond)
//                        redrawFragment()
//                    }
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
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

    private fun setTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                FIRST_THEME_FRAGMENT -> {
                    getString(R.string.theme_indigo)
                }
                SECOND_THEME_FRAGMENT -> {
                    getString(R.string.theme_pink)
                }

                else -> {
                    getString(R.string.theme_indigo)
                }
            }
        }.attach()
    }
}