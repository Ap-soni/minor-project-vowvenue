package settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.minor1.R

/**
 * A simple [Fragment] subclass.
 * Use the [languagechange.newInstance] factory method to
 * create an instance of this fragment.
 */
class languagechange : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_languagechange, container, false)
    }

}