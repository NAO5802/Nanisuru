package com.example.nanisuru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ItemAdapter(data: OrderedRealmCollection<Item>) :
    RealmRecyclerViewAdapter<Item, ItemAdapter.ViewHolder>(data, true){

    private var listener: ((Long?)-> Unit)? = null

    fun setOnItemClickListener(listener:(Long?)-> Unit) {
        this.listener = listener
    }

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val title: TextView = cell.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item: Item? =getItem(position)
        holder.title.text = item?.title
        holder.itemView.setOnClickListener {
            listener?.invoke(item?.id)
        }
    }

    override fun getItemId(position: Int) : Long {
        return getItem(position)?.id ?: 0
    }

}