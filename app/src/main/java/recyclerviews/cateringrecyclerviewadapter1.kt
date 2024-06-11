package recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import caterings.cateringonclick
import com.bumptech.glide.Glide
import com.example.minor1.R
import models.profile.cateringsinglemodel
import models.viewmodel.sharedviewmodel

class cateringrecyclerviewadapter1 (val cateringlist:ArrayList<cateringsinglemodel>):
    RecyclerView.Adapter<cateringrecyclerviewadapter1.myviewholder>() {
    inner class myviewholder(var itemview: View):
        RecyclerView.ViewHolder(itemview){
        val price: TextView
        val cateringimage1: ImageView
        val cateringname: TextView
        val location: TextView
        val rating: TextView
        init {
            price = itemView.findViewById(R.id.pricecard)
            cateringimage1= itemView.findViewById(R.id.cateringfullimage)
            cateringname = itemView.findViewById(R.id.cateringfullname)
            location= itemView.findViewById(R.id.locationcard)
            rating= itemView.findViewById(R.id.ratingcard)
            itemview.setOnClickListener{
                val cateringName = cateringlist[adapterPosition].Name
                val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager


                val viewModel = ViewModelProvider(itemView.context as AppCompatActivity ).get(sharedviewmodel::class.java)
                viewModel.data.value = cateringName

                // Replace the current fragment with the new one
                fragmentManager.beginTransaction()
                    .replace(R.id.linearmainlayout, cateringonclick())
                    .addToBackStack(null) // Optional: Add the transaction to the back stack
                    .commit()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singlecateringmodel,parent,false)
        return myviewholder(view)
    }

    override fun getItemCount(): Int {
        return cateringlist.size
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        val price=cateringlist[position].vegper
        holder.cateringname.text = cateringlist[position].Name
        holder.price.text = " ₹ $price onwards "
        holder.rating.text = cateringlist[position].rating
        holder.location.text = cateringlist[position].location
        Glide.with(holder.itemView.context)
            .load(cateringlist[position].image1)
            .placeholder(R.drawable.selectingimage) // Placeholder image while loading
            .error(R.drawable.placeholderimage) // Error image if loading fails
            .into(holder.cateringimage1)    }




}