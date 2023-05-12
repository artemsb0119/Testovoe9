package com.example.testovoe9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FactsAdapter (val facts: List<Facts>) : RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fact = facts[position]
        holder.textViewFact.text = fact.title
        Glide.with(holder.itemView.context).load(fact.image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewFact: TextView = itemView.findViewById(R.id.textViewFact)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}