package Reviews

import admin.admin_upload_catering
import android.app.Activity
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
import com.example.minor1.R
import com.example.minor1.databinding.FragmentReviewvenueBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import models.datamodels.reviewmodel
import models.viewmodel.sharedviewmodel

class reviewvenue : Fragment() {
    private lateinit var firestore: FirebaseFirestore
    lateinit var binding: FragmentReviewvenueBinding
    lateinit var rating:String
    private lateinit var auth: FirebaseAuth
    lateinit var imageuri: Uri
    lateinit var reviewtitle:String
    lateinit var name:String

    var string: String? =null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_reviewvenue,container,false)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        binding.star1.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star2.setBackgroundResource(R.drawable.baseline_star)
            binding.star3.setBackgroundResource(R.drawable.baseline_star)
            binding.star4.setBackgroundResource(R.drawable.baseline_star)
            binding.star5.setBackgroundResource(R.drawable.baseline_star)
            rating="1"

        }
        binding.star2.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star2.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star3.setBackgroundResource(R.drawable.baseline_star)
            binding.star4.setBackgroundResource(R.drawable.baseline_star)
            binding.star5.setBackgroundResource(R.drawable.baseline_star)
            rating="2"

        }
        binding.star3.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star2.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star3.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star4.setBackgroundResource(R.drawable.baseline_star)
            binding.star5.setBackgroundResource(R.drawable.baseline_star)
            rating="3"

        }
        binding.star4.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star2.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star3.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star4.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star5.setBackgroundResource(R.drawable.baseline_star)
            rating="4"

        }
        binding.star5.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star2.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star3.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star4.setBackgroundResource(R.drawable.baseline_starfull)
            binding.star5.setBackgroundResource(R.drawable.baseline_starfull)
            rating="5"

        }
        binding.imagereview.setOnClickListener {
            open_image_picker(PICK_PROFILE_IMAGE1_REQUEST)


        }

        binding.submitbtn.setOnClickListener {
            binding.progressBar2.visibility=View.VISIBLE
            val review=binding.review.text.toString()
            val reviewtitle=binding.reviewtitle.text.toString()



            val viewModel = ViewModelProvider(requireActivity()).get(sharedviewmodel::class.java)
            viewModel.data.observe(viewLifecycleOwner) { value ->

                val curuser = auth.currentUser
                val curUserId = curuser?.uid
                val userDocRef = firestore.collection("userdetails").document(curUserId!!)
                userDocRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("name").toString()
                    }}
                val nameproduct=value.toString()
                val storageReference= FirebaseStorage.getInstance().getReference().child("venues images").child("${value.toString()}").child("image")

                storageReference.putFile(imageuri!!).addOnSuccessListener {

                    storageReference.downloadUrl.addOnCompleteListener {uriTask->
                        var imageuri1=uriTask.result.toString()
                        val review = reviewmodel(name,nameproduct,rating,imageuri1,reviewtitle, review )

                        firestore.collection("reviewsvenues").add(review).addOnSuccessListener {

                            binding.progressBar.visibility=View.GONE
                            Toast.makeText(requireContext(),"Posting review Successfull ", Toast.LENGTH_SHORT).show()
                            parentFragmentManager.popBackStack()
                            binding.progressBar2.visibility=View.GONE

                        }.addOnFailureListener {
                            Toast.makeText(requireContext(),"Posting review  failed , Retry ",
                                Toast.LENGTH_SHORT).show()
                        }


                    }


                }.addOnFailureListener {
                    Toast.makeText(requireContext(),"Saving image1  Failure ", Toast.LENGTH_SHORT).show()

                }



            }

        }





        return binding.root
    }
    companion object {
        const val PICK_PROFILE_IMAGE1_REQUEST = 1

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== admin_upload_catering.PICK_PROFILE_IMAGE1_REQUEST && resultCode== Activity.RESULT_OK && data!=null ){
            imageuri= data.data!!
            string=imageuri.toString()
            binding.imagereview.setImageURI(imageuri)


        }}


    fun open_image_picker(pickImageRequest: Int) {
        val intent= Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent,pickImageRequest)
    }

}