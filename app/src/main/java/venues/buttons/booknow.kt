package venues.buttons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.minor1.R
import com.example.minor1.databinding.FragmentBooknowBinding
import java.util.Calendar

class booknow : Fragment() {

    lateinit var binding: FragmentBooknowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_booknow,container,false)

        binding.backbtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val today = Calendar.getInstance()
        binding.datePicker1.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}