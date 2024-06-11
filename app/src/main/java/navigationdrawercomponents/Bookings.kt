package navigationdrawercomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.minor1.R

/**
 * A simple [Fragment] subclass.
 * Use the [bookings.newInstance] factory method to
 * create an instance of this fragment.
 */
class bookings : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View=inflater.inflate(R.layout.fragment_bookings, container, false)
        // Inflate the layout for this fragment


        return view
    }

}
