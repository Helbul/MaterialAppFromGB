package com.example.materialappfromgb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.materialappfromgb.databinding.ActivityMainBinding
import com.example.materialappfromgb.view.notes.NoteListFragment
import com.example.materialappfromgb.view.picture.PictureFragment
import com.example.materialappfromgb.view.settings.SettingsFragment
import com.example.materialappfromgb.view.wiki.WikiFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.IndigoTheme)
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation =  binding.bottomNavigationView

        bottomNavigation.selectedItemId = R.id.bottom_nav_picture
        loadFragment(PictureFragment())

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_notes -> {
                    loadFragment(NoteListFragment())
                    true
                }
                R.id.bottom_nav_picture -> {
                    loadFragment(PictureFragment())
                    true
                }
                R.id.bottom_nav_wiki -> {
                    loadFragment(WikiFragment())
                    true
                }
                R.id.bottom_nav_settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_navigation_container, fragment)
            .commit()
    }
}