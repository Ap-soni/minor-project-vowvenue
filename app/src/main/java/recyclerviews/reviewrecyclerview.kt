package recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minor1.R
import models.datamodels.review3model

class reviewrecyclerview(val reviewlist:ArrayList<review3model>):
    RecyclerView.Adapter<reviewrecyclerview.myviewholder>()  {
    inner class myviewholder(var itemview: View):
        RecyclerView.ViewHolder(itemview){
        val review: TextView
        val username: TextView
        val rating: TextView
        init {
            username=itemview.findViewById(R.id.reviewusername)
            review=itemview.findViewById(R.id.reviewtext)
            rating= itemView.findViewById(R.id.ratingreview)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reviewrecyclerview.myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.reviewsinglemodel,parent,false)
        return myviewholder(view)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        holder.review.text = reviewlist[position].review
        holder.username.text = reviewlist[position].username
        holder.rating.text = reviewlist[position].rating

    }

    override fun getItemCount(): Int {
        return reviewlist.size
    }




}