package venues.buttons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minor1.R
import com.example.minor1.databinding.FragmentSupervowBinding
import com.google.firebase.firestore.FirebaseFirestore
import models.profile.cateringsinglemodel
import recyclerviews.venueadapter
import recyclerviews.venueadapter2

class supervow : Fragment() {
    lateinit var binding: FragmentSupervowBinding
    lateinit var adapter:venueadapter
    lateinit var adapter1: venueadapter2


    lateinit var venuelists: ArrayList<cateringsinglemodel>
    lateinit var venuelistsecond: ArrayList<cateringsinglemodel>

    private lateinit var firestore: FirebaseFirestore




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_supervow,container,false)
        venuelists = ArrayList()
        venuelistsecond=ArrayList()
        firestore = FirebaseFirestore.getInstance()

        binding.recyclerViewvenue.setHasFixedSize(true)
        binding.recyclerViewvenue.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)







        return binding.root
    }
    override fun onStart() {
        super.onStart()
        firestore.collection("venue").get().addOnSuccessListener {

            if (!it.isEmpty){
                for (document in it ){
                    var venue=cateringsinglemodel(
                        document.data.get("name").toString(),
                        document.data.get("location").toString(),
                        document.data.get("vegper").toString(),
                        document.data.get("rating").toString(),
                        document.data.get("imageurl").toString())
                    venuelists.add(venue)

                }

                adapter= venueadapter(venuelists)
                binding.recyclerViewvenue.adapter=adapter
                adapter.notifyDataSetChanged()


            }else{
                Toast.makeText(requireContext(),"retriving details empty , Retry ", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),"retriving details failed , Retry ", Toast.LENGTH_SHORT).show()
        }
    }

}