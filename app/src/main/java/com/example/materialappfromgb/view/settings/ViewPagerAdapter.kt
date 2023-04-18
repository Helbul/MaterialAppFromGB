package com.example.materialappfromgb.view.settings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.materialappfromgb.view.settings.firsttheme.FirstThemeFragment
import com.example.materialappfromgb.view.settings.secondtheme.SecondThemeFragment

class ViewPagerAdapter (fragment: Fragment)
    : FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(FirstThemeFragment(), SecondThemeFragment())

    companion object {
        private const val FIRST_THEME_FRAGMENT = 0
        private const val SECOND_THEME_FRAGMENT = 1
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[FIRST_THEME_FRAGMENT]
            1 -> fragments[SECOND_THEME_FRAGMENT]
            else -> fragments[FIRST_THEME_FRAGMENT]
        }
    }
}