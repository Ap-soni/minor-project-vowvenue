package Reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minor1.R
import com.example.minor1.databinding.FragmentOwnerreviewBinding
import com.google.firebase.firestore.FirebaseFirestore
import models.datamodels.review3model
import recyclerviews.reviewrecyclerview

class ownerreview : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    lateinit var binding: FragmentOwnerreviewBinding
    lateinit var phone:String
    var productname:String?=null
    lateinit var adapter: reviewrecyclerview
    lateinit var reviewlist: ArrayList<review3model>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_ownerreview,container,false)
        firestore = FirebaseFirestore.getInstance()

        reviewlist = ArrayList()

        binding.reviewbtn.setOnClickListener {
            replaceFragment(reviewproduct())
        }
        binding.reviewrecycler.setHasFixedSize(true)
        binding.reviewrecycler.layoutManager= LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)


        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        firestore.collection("reviewscaterings").get().addOnSuccessListener {

            if (!it.isEmpty){
                for (document in it ){
                    var review= review3model(
                        document.data.get("username").toString(),
                        document.data.get("rating").toString(),
                        document.data.get("review").toString(),
                    )
                    reviewlist.add(review)

                }

                adapter= reviewrecyclerview(reviewlist)
                binding.reviewrecycler.adapter=adapter
                adapter.notifyDataSetChanged()


            }else{
                Toast.makeText(requireContext(),"retriving reviews empty , Retry ", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),"retriving reviews failed , Retry ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
        fragmentTransaction.replace(R.id.linearmainlayout, fragment)
        fragmentTransaction.commit()
    }
}