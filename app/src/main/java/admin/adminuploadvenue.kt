package admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.minor1.R
import com.example.minor1.databinding.FragmentAdminuploadvenueBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import models.datamodels.cateringonclickmodel

class adminuploadvenue : Fragment() {



    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var imageuri: Uri
    var string: String? =null
    lateinit var binding:FragmentAdminuploadvenueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_adminuploadvenue,container,false)


        auth=FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()

        binding
            .image1.setOnClickListener {
                open_image_picker(admin_upload_catering.PICK_PROFILE_IMAGE1_REQUEST)
            }


        binding.savedetails.setOnClickListener {

            binding.progressBar.visibility=View.VISIBLE

            val name=binding.fullname.text.toString()
            val vegper=binding.vegperplate.text.toString()
            val nonvegper=binding.nonvegperplate.text.toString()
            val mincapacity=binding.mincapacity.text.toString()
            val experience=binding.experience.text.toString()
            val description=binding.bycaterers.text.toString()
            val roomprice=binding.roomprice.text.toString()
            val decorprice=binding.decorprice.text.toString()
            val location=binding.location.text.toString()
            val rating=binding.rating.text.toString()
            val phone=binding.phonenumber.text.toString()
            val curruser=auth.currentUser


            if(TextUtils.isEmpty(name)){
                Toast.makeText(requireContext(),"Name is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(vegper)){
                Toast.makeText(requireContext(),"veg per plate price is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(nonvegper)){
                Toast.makeText(requireContext(),"non veg per plate price is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(phone)){
                Toast.makeText(requireContext(),"Phone is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(mincapacity)){
                Toast.makeText(requireContext(),"mincapacity is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(experience)){
                Toast.makeText(requireContext(),"experience is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(description)){
                Toast.makeText(requireContext(),"description is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(roomprice)){
                Toast.makeText(requireContext(),"cuisines is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(decorprice)){
                Toast.makeText(requireContext(),"workingstyle is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(location)){
                Toast.makeText(requireContext(),"location Gender",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(rating)){
                Toast.makeText(requireContext(),"rating is Empty",Toast.LENGTH_SHORT).show()
            }else{
                if (curruser!=null){
                    savevenue()

                }else{
                    Toast.makeText(requireContext(),"Please Sign up ",Toast.LENGTH_SHORT).show()
                }
            }
        }


        return binding.root
    }


    fun savevenue() {
        val name=binding.fullname.text.toString()
        val vegper=binding.vegperplate.text.toString()
        val nonvegper=binding.nonvegperplate.text.toString()
        val mincapacity=binding.mincapacity.text.toString()
        val experience=binding.experience.text.toString()
        val description=binding.bycaterers.text.toString()
        val roomprice=binding.roomprice.text.toString()
        val decorprice=binding.decorprice.text.toString()
        val location=binding.location.text.toString()
        val rating=binding.rating.text.toString()
        val phone=binding.phonenumber.text.toString()




        val storageReference= FirebaseStorage.getInstance().getReference().child("venue images").child("$name").child("image")
        storageReference.putFile(imageuri!!).addOnSuccessListener {

            storageReference.downloadUrl.addOnCompleteListener {uriTask->
                var imageuri1=uriTask.result.toString()
                val venue = cateringonclickmodel(name,location, nonvegper, vegper, roomprice, description, experience, mincapacity, decorprice, rating, phone,
                    imageuri1!!
                )

                firestore.collection("venue").add(venue).addOnSuccessListener {

                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),"Saving Successfull ", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()

                }.addOnFailureListener {
                    Toast.makeText(requireContext(),"Saving details failed , Retry ", Toast.LENGTH_SHORT).show()
                }


            }


        }.addOnFailureListener {
            Toast.makeText(requireContext(),"Saving image1  Failure ", Toast.LENGTH_SHORT).show()

        }






    }

    companion object {
        const val PICK_PROFILE_IMAGE1_REQUEST = 1

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== PICK_PROFILE_IMAGE1_REQUEST && resultCode== Activity.RESULT_OK && data!=null ){
            imageuri= data.data!!
            string=imageuri.toString()
            binding.image1.setImageURI(imageuri)
        }
    }

    fun open_image_picker(pickImageRequest: Int) {
        val intent= Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent,pickImageRequest)
    }


}