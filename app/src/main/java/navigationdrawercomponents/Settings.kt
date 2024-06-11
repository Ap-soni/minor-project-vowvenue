package navigationdrawercomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.minor1.R
import com.google.android.material.button.MaterialButton
import settings.about.about
import settings.countrychange
import settings.currencychange
import settings.feedback
import settings.languagechange
import settings.rating

class Settings : Fragment() {
    lateinit var currencybtn: MaterialButton
    lateinit var languagebtn: MaterialButton
    lateinit var aboutbtn   : MaterialButton
    lateinit var countrybtn : MaterialButton
    lateinit var ratebtn    : MaterialButton
    lateinit var feedbackbtn: MaterialButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view:View= inflater.inflate(R.layout.fragment_settings, container, false)
        currencybtn=view.findViewById(R.id.currencybtn)
        languagebtn=view.findViewById(R.id.languagebtn)
        aboutbtn   =view.findViewById(R.id.aboutbtn)
        countrybtn =view.findViewById(R.id.countrybtn)
        ratebtn    =view.findViewById(R.id.ratebtn)
        feedbackbtn=view.findViewById(R.id.feedbackbtn)


        
         currencybtn.setOnClickListener{replacefragment(currencychange()) }
         languagebtn.setOnClickListener{replacefragment(languagechange()) }
         aboutbtn   .setOnClickListener{replacefragment(about()) }
         countrybtn .setOnClickListener{replacefragment(countrychange()) }
         ratebtn    .setOnClickListener{replacefragment(rating()) }
         feedbackbtn.setOnClickListener{replacefragment(feedback()) }
        return view
    }
    fun replacefragment(fragment: Fragment){
        val fragmentManager=parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.linearmainlayout,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
    }
