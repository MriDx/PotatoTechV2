package com.app.potatotech.ui.activity.home_ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.potatotech.R
import com.app.potatotech.ui.fragment.home_fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.home_ui.*

class HomeUI : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_ui)

        bottomNavigation?.apply {
            this.selectedItemId = R.id.home
        }.also {
            it?.setOnNavigationItemSelectedListener(this)
        }


        addFragment(HomeFragment())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.categories -> {
            }
            R.id.videos -> {
            }
            R.id.profile -> {
            }
            R.id.social -> {
            }
            else -> {
                addFragment(HomeFragment())
            }
        }
        return false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            this.replace(R.id.fragmentHolder, fragment, fragment.tag)
            this.disallowAddToBackStack()
        }.also {
            it.commit()
        }
    }

}

