package bottomnavigation

import Reviews.displaymain.attendeereview
import Reviews.ownerreview
import Reviews.reviewpage
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minor1.R
import com.example.minor1.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import models.profile.cateringsinglemodel
import recyclerviews.venueadapter
import recyclerviews.venueadapter2
import venues.buttons.managedvow
import venues.buttons.premiumvow
import venues.buttons.supervow
import venues.buttons.townvow
import venues.buttons.wizardvow

class fragmentMain : Fragment() {

    lateinit var binding:FragmentMainBinding
    lateinit var adapter:venueadapter
    lateinit var adapter1: venueadapter2


    lateinit var venuelists: ArrayList<cateringsinglemodel>
    lateinit var venuelistsecond: ArrayList<cateringsinglemodel>

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)

        venuelists = ArrayList()
        venuelistsecond=ArrayList()

        firestore = FirebaseFirestore.getInstance()

        val userDocRef = firestore.collection("offer").document("caterings")


        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val code = document.getString("code")
                val percent = document.getString("percent")
                val offer =document.getString("asset")


            } else {
                Toast.makeText(requireContext(), "Unable to get offer details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.ownerreviewbtn.setOnClickListener {
            replaceFragment(ownerreview())
        }
        binding.customerreviewbtn.setOnClickListener {
            replaceFragment(reviewpage())
        }
        binding.attendeereviewbtn.setOnClickListener {
            replaceFragment(attendeereview())
        }


        binding.sortbtn.setOnClickListener {
            val dialog= BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_sortbottoom,null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.pricebtn.setOnClickListener {
            val dialog= BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_pricebottom,null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.localitybtn.setOnClickListener {
            val dialog= BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_localitybottom,null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.filerbtn.setOnClickListener {
            val dialog= BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_filterbottom,null)
            dialog.setContentView(view)
            dialog.show()
        }


        binding.supervowbtn.setOnClickListener {
            replaceFragment(supervow())
        }
        binding.wizardbtn.setOnClickListener {
            replaceFragment(wizardvow())
        }
        binding.managedbtn.setOnClickListener {
            replaceFragment(managedvow())
        }
        binding.premiumbtn.setOnClickListener {
            replaceFragment(premiumvow())
        }
        binding.townbtn.setOnClickListener {
            replaceFragment(townvow())
        }


        binding.venuerecyclerview2.setHasFixedSize(true)
        binding.venuerecyclerview2.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        binding.venuerecyclerview1.setHasFixedSize(true)
        binding.venuerecyclerview1.layoutManager= LinearLayoutManager(requireContext())



        return binding.root    }
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
                venuelistsecond= ArrayList(venuelists.asReversed())
                adapter1= venueadapter2(venuelistsecond)
                binding.venuerecyclerview2.adapter=adapter1
                adapter1.notifyDataSetChanged()



                adapter= venueadapter(venuelists)
                binding.venuerecyclerview1.adapter=adapter
                adapter.notifyDataSetChanged()


            }else{
                Toast.makeText(requireContext(),"retriving details empty , Retry ", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),"retriving details failed , Retry ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
        fragmentTransaction.replace(R.id.linearmainlayout, fragment)
        fragmentTransaction.commit()
    }
}