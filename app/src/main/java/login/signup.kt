package login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.minor1.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import profile.editprofile

class signup : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var signupbtn: Button
    lateinit var fullname: TextInputEditText
    lateinit var signuppassword: TextInputEditText
    lateinit var signupemail: TextInputEditText
    lateinit var progressBar: ProgressBar
    lateinit var alreadybtn:Button


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View=inflater.inflate(R.layout.fragment_signup, container, false)
        // Inflate the layout for this fragment
        auth= FirebaseAuth.getInstance()
        signupemail=view.findViewById(R.id.emailsignupeditext)
        signuppassword=view.findViewById(R.id.signuppasswordedittext)
        signupbtn=view.findViewById(R.id.signuppgbtn)
        fullname=view.findViewById(R.id.fullname)
        progressBar=view.findViewById(R.id.progress_signup)
        alreadybtn=view.findViewById(R.id.alreadyuser)



        progressBar.visibility=View.GONE


        signupbtn.setOnClickListener{
            val email:String=signupemail.text.toString()
            val name:String=fullname.text.toString()
            val password:String=signuppassword.text.toString()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(requireContext(),"Fill the Empty Fields",Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(password)){
                Toast.makeText(requireContext(),"Fill the Empty Fields",Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(name)){
                Toast.makeText(requireContext(),"Fill the Empty Fields",Toast.LENGTH_SHORT).show()
            }


            progressBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(requireActivity()) { task->
                if (task.isSuccessful){
                    progressBar.visibility=View.GONE
                    val user = auth.currentUser

                    val profilesupdate=UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        user?.updateProfile(profilesupdate)?.addOnCompleteListener {task->
                            if (task.isSuccessful){
                                val fragmentManager = parentFragmentManager
                                val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
                                fragmentTransaction.replace(R.id.linearmainlayout, editprofile())
                                fragmentTransaction.commit()
                            Toast.makeText(requireContext(), "Authentication successful", Toast.LENGTH_SHORT).show() }
                        }
                    } else{
                     if (password.toCharArray().size<6){
                        Toast.makeText(requireContext(),"Low password strength",Toast.LENGTH_SHORT).show()
                    } else{
                            Toast.makeText(requireContext(), "Authentication signup failed.", Toast.LENGTH_SHORT).show() }

                }

            }
        }
        alreadybtn.setOnClickListener {
            Toast.makeText(requireContext(), " Click on SignIn Tab", Toast.LENGTH_SHORT).show() }



        return view
    }


}