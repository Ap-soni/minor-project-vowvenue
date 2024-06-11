package bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minor1.R
import com.example.minor1.databinding.FragmentCateringBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import models.profile.cateringsinglemodel
import recyclerviews.cateringrecyclerviewadapter1
import recyclerviews.cateringrecyclerviewadapter2

class catering : Fragment() {

    lateinit var binding:FragmentCateringBinding
    lateinit var adapter:cateringrecyclerviewadapter1
    lateinit var adapter1: cateringrecyclerviewadapter2

    lateinit var cateringlists: ArrayList<cateringsinglemodel>
    lateinit var cateringlistsecond: ArrayList<cateringsinglemodel>

    private lateinit var firestore: FirebaseFirestore


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_catering,container,false)

        cateringlists = ArrayList()
        cateringlistsecond=ArrayList()

        firestore = FirebaseFirestore.getInstance()

        val dialog=BottomSheetDialog(requireContext())
        val view=layoutInflater.inflate(R.layout.fragment_bottomfilter,null)
        dialog.setContentView(view)
        dialog.show()

        val userDocRef = firestore.collection("offer").document("caterings")

        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val code = document.getString("code")
                val percent = document.getString("percent")
                val offer =document.getString("asset")

                binding.offertext.text = "Get FLAT $percent% OFF"
                binding.codetext.text = code
                binding.assetdeal.text=offer

            } else {
                Toast.makeText(requireContext(), "Unable to get offer details", Toast.LENGTH_SHORT).show()
            }
        }


    binding.sortbtn.setOnClickListener {
            val dialog=BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_sortbottoom,null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.pricebtn.setOnClickListener {
            val dialog=BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_pricebottom,null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.localitybtn.setOnClickListener {
            val dialog=BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_localitybottom,null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.filerbtn.setOnClickListener {
            val dialog=BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.fragment_filterbottom,null)
            dialog.setContentView(view)
            dialog.show()
        }


        binding.cateringrecyclerview2.setHasFixedSize(true)
        binding.cateringrecyclerview2.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        binding.cateringrecyclerview1.setHasFixedSize(true)
        binding.cateringrecyclerview1.layoutManager= LinearLayoutManager(requireContext())



        return binding.root
    }



    override fun onStart() {
        super.onStart()
        firestore.collection("caterings").get().addOnSuccessListener {

            if (!it.isEmpty){
                for (document in it ){
                    var cater=cateringsinglemodel(
                        document.data.get("name").toString(),
                        document.data.get("location").toString(),
                        document.data.get("vegper").toString(),
                        document.data.get("rating").toString(),
                        document.data.get("imageurl").toString())
                    cateringlists.add(cater)

                }
                cateringlistsecond= ArrayList(cateringlists.asReversed())
                adapter=cateringrecyclerviewadapter1(cateringlists)
                binding.cateringrecyclerview1.adapter=adapter
                adapter.notifyDataSetChanged()

                adapter1=cateringrecyclerviewadapter2(cateringlistsecond)
                binding.cateringrecyclerview2.adapter=adapter1
                adapter1.notifyDataSetChanged()
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
