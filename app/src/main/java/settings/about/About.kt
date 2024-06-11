package settings.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.example.minor1.R
import com.google.android.material.button.MaterialButton

/**
 * A simple [Fragment] subclass.
 * Use the [about.newInstance] factory method to
 * create an instance of this fragment.
 */
class about : Fragment() {
    lateinit var privacybtn: Button
    lateinit var termsbtn:Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  view :View=inflater.inflate(R.layout.fragment_about, container, false)
        privacybtn=view.findViewById(R.id.privacypolicybtn)
        termsbtn=view.findViewById(R.id.termsofservicebtn)

        privacybtn.setOnClickListener{
            replacefragment(privacypolicy())

        }
        termsbtn.setOnClickListener{
            replacefragment(termsofservice())        }



        // Inflate the layout for this fragment
        return view
    }
    fun replacefragment(fragment:Fragment){
        val fragmentManager=parentFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.linearmainlayout,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    }
