package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R


class AsteroidAdapter(private val clickListener: OnClickListener) :
    RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {        //Adatta la lista di asteroidi in modo che posso essere mostrata dal RecyclerView
                                                                //creo il "mio" adapter (=AsteroidAdapter) come sottoclasse di RecyclerView.Adapter
                                                                //e gli dico quale VH deve usare dentro il mio adapter (=il VH che definisco sotto, dentro l'adapter stesso).

    var data = ArrayList<Asteroid>()    //the list of asteroids
                                        //(crea una variabile "data" di tipo "array di Asteroid".
                                        //quindi si può accedere ai singoli oggetti della lista tramite la posizione nella lista)

    // TODO NOTIFYDATACHANGES????

    override fun getItemCount(): Int {  //Returns the total number of items in the data set held by the adapter.
        return data.size
    }

    /** Called by RecyclerView to display the data at the specified position.
     This method updates the contents of the itemView inside the ViewHolder inside the
     RecyclerView to reflect the item at the given position.
     (mette dentro al ViewHolder i dati dell'oggetto nella posizione passata come parametro,
      cioè li "binda")
     (questo metodo si occupa di mettere i dati nella itemView, mentre onCreateViewHolder
     si occupa di creare il layout del VH)
     HOW TO ACTUALLY DRAW THE ITEM WITH THE DATA*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]
        holder.codename.text = item.codename
        holder.closeApproachDate.text = item.closeApproachDate
        if (item.isPotentiallyHazardous) {
            holder.statusIcon.setImageResource(R.drawable.ic_status_potentially_hazardous)
        } else {
            holder.statusIcon.setImageResource(R.drawable.ic_status_normal)
        }
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
    }

    /** Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    This new ViewHolder should be constructed with a new View that can represent the items
    of the given type. It inflate the new view from the item XML layout file.
    (Crea materialmente il VH inflatando il layout dell'item,
    mentre onBindViewHolder lo riempie con i dati)*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    //Definisce come è fatto il VH, attraverso il parametro view
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val codename: TextView = view.findViewById(R.id.itemCodename)
        val closeApproachDate: TextView = view.findViewById(R.id.itemCloseApproachDate)
        val statusIcon: ImageView = view.findViewById(R.id.itemStatusIcon)
    }

    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}