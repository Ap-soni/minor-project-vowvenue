package caterings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minor1.R
import com.example.minor1.databinding.FragmentLatestBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import models.profile.cateringsinglemodel
import recyclerviews.cateringrecyclerviewadapter1

class latest : Fragment() {
    lateinit var cateringlists: ArrayList<cateringsinglemodel>
    lateinit var binding: FragmentLatestBinding
    lateinit var adapter:cateringrecyclerviewadapter1
    private lateinit var firestore: FirebaseFirestore



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_latest,container,false)
        // Inflate the layout for this fragment

        cateringlists = ArrayList()
        firestore = FirebaseFirestore.getInstance()
        binding.recyclerviewlatest.setHasFixedSize(true)
        binding.recyclerviewlatest.layoutManager= LinearLayoutManager(requireContext())



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
                adapter= cateringrecyclerviewadapter1(cateringlists)
                binding.recyclerviewlatest.adapter=adapter
                adapter.notifyDataSetChanged()


            }else{
                Toast.makeText(requireContext(),"retriving details empty , Retry ", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),"retriving details failed , Retry ", Toast.LENGTH_SHORT).show()
        }
    }
}