package settings.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.minor1.R

class privacypolicy : Fragment() {
    lateinit var description:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View =inflater.inflate(R.layout.fragment_privacypolicy, container, false)
        // Inflate the layout for this fragment
        return view
    }

}