
package login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.minor1.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class loginstart : Fragment() {
    lateinit var adapter1 :fragmentsstateadapter
    lateinit var tabLayout: TabLayout
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tabItem= arrayOf("Sign In","Sign Up")
        val view :View=inflater.inflate(R.layout.fragment_loginstart, container, false)
        var viewPager2:ViewPager2=view.findViewById(R.id.viewpager2)
        adapter1=fragmentsstateadapter(parentFragmentManager,lifecycle)
        adapter1.addfragmenttolist(signin())
        adapter1.addfragmenttolist(signup())
        viewPager2.adapter=adapter1
        viewPager2.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        tabLayout=view.findViewById(R.id.tablayout)
        TabLayoutMediator(tabLayout,viewPager2){
            tab,position->tab.text=tabItem[position]

        }.attach()
        // Inflate the layout for this fragment
        return view
    }

    }
