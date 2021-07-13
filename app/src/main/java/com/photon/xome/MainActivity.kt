package com.photon.xome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.photon.xome.home.view.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val homeFragment = HomeFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.main_container,homeFragment).commit()
        }
    }
}