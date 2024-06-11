package bottomnavigation
import admin.admin_upload_catering
import admin.adminsetoffer
import admin.adminuploadbartender
import admin.adminuploadcakes
import admin.adminuploadchaatstalls
import admin.adminuploaddrink
import admin.adminuploadvenue
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.minor1.R
import com.example.minor1.databinding.FragmentYourProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import login.loginstart
import profile.editprofile
import profile.subscriptionpage
import java.io.File

class your_profile : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var binding:FragmentYourProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_your_profile,container,false)



        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val curuser = auth.currentUser
        binding.progressBar.visibility=View.VISIBLE

        if (curuser == null) {
            binding.signinbtnprofile.visibility = View.VISIBLE
            binding.signoutbtnprofile.visibility=View.GONE
            binding.deleteaccount.visibility=View.GONE
            binding.progressBar.visibility=View.GONE
        } else {
            binding.signinbtnprofile.visibility = View.GONE

            val curUserId = curuser.uid
            val userDocRef = firestore.collection("userdetails").document(curUserId)
            val storageReference= FirebaseStorage.getInstance().getReference("Users/"+ (auth.currentUser?.uid))

            storageReference.downloadUrl
                .addOnSuccessListener { uri ->
                    val imageFile = File.createTempFile("profile_image", "jpg")
                    val downloadTask = storageReference.getFile(imageFile)
                    downloadTask.addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                        binding.profileimgyour.setImageBitmap(bitmap)


                }}.addOnCompleteListener {
                    binding.progressBar.visibility=View.GONE
                }
                .addOnFailureListener { exception ->
                    // Handle any errors
                    Toast.makeText(requireContext(), "Unable to get profile pic", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility=View.GONE

                }

            userDocRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name")
                    val email = document.getString("email")

                    binding.username.text = name
                    binding.emailaddressyour.text = email

                    if (email=="adminapoorvsoni@gmail.com"&& name=="Apoorv soni"){
                        binding.uploadvenue.visibility=View.VISIBLE
                        binding.uploadcatering.visibility=View.VISIBLE
                        binding.uploadbartender.visibility=View.VISIBLE
                        binding.uploadcakes.visibility=View.VISIBLE
                        binding.uploaddrink.visibility=View.VISIBLE
                        binding.uploadchaatandstalls.visibility=View.VISIBLE
                        binding.setoffer.visibility=View.VISIBLE

                    }else if (email=="adminakshatshrivastava@gmail.com"&& name=="Akshat shrivastava"){
                        binding.uploadvenue.visibility=View.VISIBLE
                        binding.uploadcatering.visibility=View.VISIBLE
                        binding.uploadbartender.visibility=View.VISIBLE
                        binding.uploadcakes.visibility=View.VISIBLE
                        binding.uploaddrink.visibility=View.VISIBLE
                        binding.setoffer.visibility=View.VISIBLE
                        binding.uploadchaatandstalls.visibility=View.VISIBLE
                    }else if (email=="adminayushbharne@gmail.com"&& name=="Ayush bharne"){
                        binding.uploadvenue.visibility=View.VISIBLE
                        binding.uploadcatering.visibility=View.VISIBLE
                        binding.uploadbartender.visibility=View.VISIBLE
                        binding.uploadcakes.visibility=View.VISIBLE
                        binding.uploaddrink.visibility=View.VISIBLE
                        binding.uploadchaatandstalls.visibility=View.VISIBLE
                        binding.setoffer.visibility=View.VISIBLE

                    }else if (email=="adminadityaborikar@gmail.com"&& name=="Aditya borikar"){
                        binding.uploadvenue.visibility=View.VISIBLE
                        binding.uploadcatering.visibility=View.VISIBLE
                        binding.uploadbartender.visibility=View.VISIBLE
                        binding.uploadcakes.visibility=View.VISIBLE
                        binding.uploaddrink.visibility=View.VISIBLE
                        binding.uploadchaatandstalls.visibility=View.VISIBLE
                        binding.setoffer.visibility=View.VISIBLE

                    }



                } else {
                    Toast.makeText(requireContext(), "Unable to get user details", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.editprofile.setOnClickListener {
            replaceFragment(editprofile())
        }

        binding.uploadbartender.setOnClickListener {
            replaceFragment(adminuploadbartender())
        }
        binding.uploadcakes.setOnClickListener {
            replaceFragment(adminuploadcakes())
        }
        binding.uploaddrink.setOnClickListener {
            replaceFragment(adminuploaddrink())
        }
        binding.uploadchaatandstalls.setOnClickListener {
            replaceFragment(adminuploadchaatstalls())
        }
        binding.setoffer.setOnClickListener {
            replaceFragment(adminsetoffer())
        }


        binding.signinbtnprofile.setOnClickListener {
            replaceFragment(loginstart())
        }

        binding.signoutbtnprofile.setOnClickListener {
            auth.signOut()
            Toast.makeText(requireContext(), "Sign out successful", Toast.LENGTH_SHORT).show()
            binding.signinbtnprofile.visibility = View.VISIBLE
            binding.signoutbtnprofile.visibility=View.INVISIBLE

            binding.username.text = "no_user"
            binding.emailaddressyour.text = "no_email"
            binding.profileimgyour.setImageResource(R.drawable.profilepictureicon)

        }

        binding.subscription.setOnClickListener {
            replaceFragment(subscriptionpage())
        }
        binding.uploadvenue.setOnClickListener {
            replaceFragment(adminuploadvenue())
        }
        binding.uploadcatering.setOnClickListener {
            replaceFragment(admin_upload_catering())
        }


        binding.deleteaccount.setOnClickListener {
            if (curuser!=null){
                auth.currentUser?.delete()
                Toast.makeText(requireContext(), "Account deleted  successfully", Toast.LENGTH_SHORT).show()
                binding.deleteaccount.visibility=View.GONE
            }else{
                Toast.makeText(requireContext(), "Login First", Toast.LENGTH_SHORT).show()

            }


        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
        fragmentTransaction.replace(R.id.linearmainlayout, fragment)
        fragmentTransaction.commit()
    }
}