package login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class fragmentsstateadapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
         var fraglist:ArrayList<Fragment> = ArrayList()
    fun addfragmenttolist (fragment: Fragment){
        fraglist.add(fragment)
    }

    override fun getItemCount(): Int {
return fraglist.size   }

    override fun createFragment(position: Int): Fragment {
return fraglist.get(position)    }
}