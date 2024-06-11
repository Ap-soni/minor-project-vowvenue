package recyclerviews

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import models.searchmodel

class searchrecyclerview (val searchlist:ArrayList<searchmodel>,val context:Context): RecyclerView.Adapter<searchrecyclerview.searchviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): searchrecyclerview.searchviewholder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: searchrecyclerview.searchviewholder, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class searchviewholder(itemview: View):RecyclerView.ViewHolder(itemview) {


    }
}