package com.example.minor1

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import bottomnavigation.catering
import bottomnavigation.fragmentMain
import bottomnavigation.your_profile
import com.example.minor1.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import navigationdrawercomponents.Settings
import navigationdrawercomponents.bookings
import navigationdrawercomponents.contactcustomerservice.customerService
import navigationdrawercomponents.disputeresolution
import navigationdrawercomponents.referEarn
import navigationdrawercomponents.rewards
import navigationdrawercomponents.safetyResource
import navigationdrawercomponents.wishlist


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var title:TextView
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil. setContentView(this,R.layout.activity_main)



        auth=FirebaseAuth.getInstance()
        val curuser=auth.currentUser
        if (curuser==null){
            val dialog=BottomSheetDialog(this)
            val view=layoutInflater.inflate(R.layout.fragment_bottomsheet,null)
            dialog.setContentView(view)
            dialog.show()
        }else if (curuser!=null){

        }


        supportFragmentManager.beginTransaction().replace(R.id.linearmainlayout, fragmentMain()).commit()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener{
                when(it.itemId){
                    R.id.homebottom->  {  replaceFragment(fragmentMain())
                    true}

                    R.id.myprofile->   { replaceFragment(your_profile())
                        true }
                    R.id.helpbottom->   { replaceFragment(catering())
                        true }
                    else -> {true} } }
        supportFragmentManager.beginTransaction().replace(R.id.linearmainlayout, fragmentMain()).commit()

        setSupportActionBar(binding.toolbar)
       binding.toolbar.post(Runnable {
           run {
               binding.toolbar.setNavigationIcon(R.drawable.baseline_sort_24)
           }
       })





        toggle=ActionBarDrawerToggle(
            this@MainActivity,
           binding.drawerlayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )

        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.customtitle)
        supportActionBar?.isHideOnContentScrollEnabled
        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
      binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.homenav->{         replaceFragment(fragmentMain(),it.title.toString())}
                R.id.bookings->{        replaceFragment(bookings(),it.title.toString())}
                R.id.wishlist->{        replaceFragment(wishlist(),it.title.toString())}
                R.id.rewards->{         replaceFragment(rewards(),it.title.toString())}
                R.id.referearn->{       replaceFragment(referEarn(),it.title.toString())}
                R.id.settings->{        replaceFragment(Settings(),it.title.toString())  }
                R.id.contactcustomer->{ replaceFragment(customerService(),it.title.toString())}
                R.id.safetyresource->{  replaceFragment(safetyResource(),it.title.toString())}
                R.id.diputeresolve->{   replaceFragment(disputeresolution(),it.title.toString()) } }
            true }

    }

    private fun reload() {
        TODO("Not yet implemented")
    }


    fun replaceFragment(fragment: Fragment,title:String){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.linearmainlayout,fragment).addToBackStack(null)
        fragmentTransaction.commit()
       binding.drawerlayout.closeDrawers()

    }

    fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction().addToBackStack(null)
        fragmentTransaction.replace(R.id.linearmainlayout,fragment)
        fragmentTransaction.commit() }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item) }
}