package login

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

class signin : Fragment() {
     lateinit var auth: FirebaseAuth
     lateinit var signinbtn:Button
     lateinit var signinemail:TextInputEditText
     lateinit var signinpassword: TextInputEditText
    lateinit var progressBar: ProgressBar
    lateinit var registerbtn:Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View=inflater.inflate(R.layout.fragment_signin, container, false)
        // Inflate the layout for this fragment
        auth= FirebaseAuth.getInstance()
        signinemail=view.findViewById(R.id.signinemaileditext)
        signinpassword=view.findViewById(R.id.signinpasswordedittext)
        signinbtn=view.findViewById(R.id.signinpgbtn)
        progressBar=view.findViewById(R.id.progress_signin)
        registerbtn=view.findViewById(R.id.register)



        progressBar.visibility=View.GONE

        signinbtn.setOnClickListener{

            val email:String=signinemail.text.toString()
            val password:String=signinpassword.text.toString()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(requireContext(),"Fill the Empty Fields",Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(password)){
                Toast.makeText(requireContext(),"Fill the Empty Fields",Toast.LENGTH_SHORT).show()
            }


            progressBar.visibility=View.VISIBLE
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener (requireActivity()){ task->
                if(task.isSuccessful){
                    val user=auth.currentUser
                    progressBar.visibility=View.GONE
                    parentFragmentManager.popBackStack()
                    Toast.makeText(requireContext(),"successful login",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),
                        "Wrong password",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
        }
        registerbtn.setOnClickListener {
            Toast.makeText(requireContext(),"Click on SignUp Tab",Toast.LENGTH_SHORT).show()
        }

        return view
    }

}