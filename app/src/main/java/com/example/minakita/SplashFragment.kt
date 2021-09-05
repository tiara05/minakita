package com.example.minakita

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.fragment_third_screen.view.*

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({
            if(onBoardingFinished()){
                startActivity(Intent(context, HomeActivity::class.java))
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 3000)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}