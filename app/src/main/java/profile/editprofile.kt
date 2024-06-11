package profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.minor1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import models.profile.profilemodel

class editprofile : Fragment() {

    lateinit var  profileimgbtn: ImageButton
    lateinit var profileimg:CircleImageView
    lateinit var fullname:EditText
    lateinit var birthdate:EditText
    lateinit var emailaddress:EditText
    lateinit var phonenumber:EditText
    lateinit var homeaddress:EditText
    lateinit var savedetails:Button
    lateinit var radiogroup:RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var radiotext:String
     var imageuri: Uri? =null
    lateinit var auth:FirebaseAuth
    lateinit var firestore: FirebaseFirestore




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_editprofile, container, false)

        profileimgbtn=view.findViewById(R.id.profileimgbtn)
        profileimg=view.findViewById(R.id.profileimg)
       fullname =view.findViewById(R.id.fullname)
        birthdate=view.findViewById(R.id.birthdate)
        emailaddress=view.findViewById(R.id.emailaddress)
        phonenumber=view.findViewById(R.id.phonenumber)
        homeaddress=view.findViewById(R.id.homeaddress)
        savedetails=view.findViewById(R.id.savedetails)
        radiogroup=view.findViewById(R.id.genderRadioGroup)
        auth=FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()



        savedetails.setOnClickListener {

            val selectedradiobuttonid:Int=radiogroup.checkedRadioButtonId
            if (selectedradiobuttonid!=-1){
                radioButton=view.findViewById(selectedradiobuttonid)
                radiotext=radioButton.text.toString()

            }


            val name   =fullname.text.toString()
            val birth  =birthdate.text.toString()
            val email  =emailaddress.text.toString()
            val phone  =phonenumber.text.toString()
            val address=homeaddress.text.toString()
            val gender=radiotext

            val curruser=auth.currentUser
            val user = profilemodel(name,birth, email, phone, address,gender)


            if(TextUtils.isEmpty(name)){
                Toast.makeText(requireContext(),"Name is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(birth)){
                Toast.makeText(requireContext(),"Birthdate is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(email)){
                Toast.makeText(requireContext(),"Email is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(phone)){
                Toast.makeText(requireContext(),"Phone is Empty",Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(address)){
                Toast.makeText(requireContext(),"Address is Empty",Toast.LENGTH_SHORT).show()
            }else if(selectedradiobuttonid==-1){
                Toast.makeText(requireContext(),"Select Gender",Toast.LENGTH_SHORT).show()
            }else{
            if (curruser!=null){
                firestore.collection("userdetails").document(curruser?.uid?:"").set(user).addOnSuccessListener {

                    Toast.makeText(requireContext(),"Saving Successfull ",Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                    parentFragmentManager.popBackStack()

                }.addOnFailureListener {
                    Toast.makeText(requireContext(),"Saving details failed , Retry ",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Please Sign up ",Toast.LENGTH_SHORT).show()
            }}
        }




        profileimgbtn.setOnClickListener{
            open_image_picker(PICK_PROFILE_IMAGE_REQUEST)

        }

        // Inflate the layout for this fragment
        return view
    }
    companion object {
        private const val PICK_PROFILE_IMAGE_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== PICK_PROFILE_IMAGE_REQUEST && resultCode== Activity.RESULT_OK && data!=null ){
             imageuri=data.data
           profileimg.setImageURI(imageuri)
            val storageReference=FirebaseStorage.getInstance().getReference("Users/"+ (auth.currentUser?.uid))
            storageReference.putFile(imageuri!!).addOnSuccessListener {
                Toast.makeText(requireContext(),"Saving profile pic succesful ",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Saving profile pic Failure ",Toast.LENGTH_SHORT).show()

            }
           }
    }

    fun open_image_picker(pickImageRequest: Int) {
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,pickImageRequest)
    }

}