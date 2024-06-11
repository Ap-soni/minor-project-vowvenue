package admin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.minor1.R
import com.example.minor1.databinding.FragmentAdminsetofferBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import models.datamodels.offermodel

class adminsetoffer : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    lateinit var binding: FragmentAdminsetofferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_adminsetoffer,container,false)

        auth=FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()


        binding.savedetails.setOnClickListener {
            val code=binding.offercode.text.toString()
            val percent=binding.perrcent.text.toString()

            if(TextUtils.isEmpty(code)){
                Toast.makeText(requireContext(),"code is Empty", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(percent)){
                Toast.makeText(requireContext(),"Offer percentage is Empty", Toast.LENGTH_SHORT).show()
            }else{
                val curruser=auth.currentUser
                if (curruser!=null){
                    saveofferr()

                }else{
                    Toast.makeText(requireContext(),"Please Sign up ",Toast.LENGTH_SHORT).show()
                }
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun saveofferr() {
        val code=binding.offercode.text.toString()
        val percent=binding.perrcent.text.toString()
        val asset=binding.DealsOn.text.toString()
        val offer= offermodel(code,percent,asset)
        firestore.collection("offer").document("caterings").set(offer).addOnSuccessListener {
            binding.progressBar.visibility=View.GONE
            Toast.makeText(requireContext(),"Offer Set Successfully ",Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()

        }.addOnFailureListener {
            Toast.makeText(requireContext(),"Offer Setting failed , Retry ",Toast.LENGTH_SHORT).show()
        }

    }

}