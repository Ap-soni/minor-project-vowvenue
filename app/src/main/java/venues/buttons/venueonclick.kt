package venues.buttons

import Reviews.reviewvenue
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.minor1.R
import com.example.minor1.databinding.FragmentVenueonclickBinding
import com.google.firebase.firestore.FirebaseFirestore
import models.datamodels.review3model
import models.viewmodel.sharedviewmodel
import recyclerviews.reviewrecyclerview

class venueonclick : Fragment() {
    private lateinit var firestore: FirebaseFirestore
    lateinit var binding: FragmentVenueonclickBinding
    lateinit var phone:String
    lateinit var size:String

    var productname:String?=null
    lateinit var adapter: reviewrecyclerview


    lateinit var reviewlist: ArrayList<review3model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_venueonclick,container,false)
        firestore = FirebaseFirestore.getInstance()

        reviewlist = ArrayList()

        val viewModel = ViewModelProvider(requireActivity()).get(sharedviewmodel::class.java)
        viewModel.data.observe(viewLifecycleOwner) { value ->
            // Handle the received value
            productname=value.toString()



            firestore.collection("venue").whereEqualTo("name",value.toString()).get().addOnSuccessListener {

                if (!it.isEmpty){
                    for (document in it ){

                        binding.venuename.text=document.data.get("name").toString()
                        binding.location.text=document.data.get("location").toString()
                        binding.vegpricers.text=document.data.get("vegper").toString()
                        binding.rupeesveg.text=document.data.get("vegper").toString()
                        binding.roomprice.text=document.data.get("cuisines").toString()
                        binding.decorprice.text=document.data.get("workingstyle").toString()
                        binding.Description.text= document.data.get("description").toString()
                        binding.rupeesnonveg.text=document.data.get("nonvegper").toString()
                        binding.mincapacity.text= document.data.get("mincapacity").toString()
                        binding.rating.text=document.data.get("rating").toString()
                        phone=document.data.get("phone").toString()
                        Glide.with(requireContext())
                            .load(document.data.get("imageurl"))
                            .placeholder(R.drawable.selectingimage) // Placeholder image while loading
                            .error(R.drawable.placeholderimage) // Error image if loading fails
                            .into(binding.cateringonimage)


                    }

                }else{
                    Toast.makeText(requireContext(),"retriving details empty , Retry ", Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"retriving details failed , Retry ", Toast.LENGTH_SHORT).show()
            } }

        binding.callbtn.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(intent)
        }
        binding.reviewbtn.setOnClickListener {
            replaceFragment(reviewvenue())
        }
        binding.reviewrecyclerview.setHasFixedSize(true)
        binding.reviewrecyclerview.layoutManager= LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)


        binding.messagebutton.setOnClickListener {
            replaceFragment(booknow())
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        firestore.collection("reviewsvenues").whereEqualTo("productname",productname).get().addOnSuccessListener {

            if (!it.isEmpty){
                for (document in it ){
                    var review= review3model(
                        document.data.get("username").toString(),
                        document.data.get("rating").toString(),
                        document.data.get("review").toString(),
                    )
                    reviewlist.add(review)

                }
                val listsize=reviewlist.size.toString()

                adapter= reviewrecyclerview(reviewlist)
                binding.reviewno.text= "$listsize reviews"
                binding.reviewrecyclerview.adapter=adapter
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