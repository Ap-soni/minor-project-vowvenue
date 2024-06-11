package caterings

import Reviews.reviewproduct
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
import com.example.minor1.databinding.FragmentCateringonclickBinding
import com.google.firebase.firestore.FirebaseFirestore
import models.datamodels.review3model
import models.viewmodel.sharedviewmodel
import recyclerviews.reviewrecyclerview
import venues.buttons.booknow

class cateringonclick : Fragment() {
    private lateinit var firestore: FirebaseFirestore
    lateinit var binding:FragmentCateringonclickBinding
    lateinit var phone:String
     var productname:String?=null
    lateinit var adapter:reviewrecyclerview


    lateinit var reviewlist: ArrayList<review3model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_cateringonclick,container,false)
        firestore = FirebaseFirestore.getInstance()

        reviewlist = ArrayList()

        val viewModel = ViewModelProvider(requireActivity()).get(sharedviewmodel::class.java)
        viewModel.data.observe(viewLifecycleOwner) { value ->
            // Handle the received value
            productname=value.toString()



        firestore.collection("caterings").whereEqualTo("name",value.toString()).get().addOnSuccessListener {

            if (!it.isEmpty){
                for (document in it ){

                    binding.cateringname.text=document.data.get("name").toString()
                    binding.location.text=document.data.get("location").toString()
                    binding.vegpricers.text=document.data.get("vegper").toString()
                    binding.rupeesveg.text=document.data.get("vegper").toString()
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
        binding.messagebutton.setOnClickListener {
            replaceFragment(booknow())
        }

        binding.callbtn.setOnClickListener {
            val intent:Intent= Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(intent)
        }
        binding.reviewbtn.setOnClickListener {
            replaceFragment(reviewproduct())
        }
        binding.reviewrecyclerview.setHasFixedSize(true)
        binding.reviewrecyclerview.layoutManager= LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        firestore.collection("reviewscaterings").whereEqualTo("productname",productname).get().addOnSuccessListener {

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