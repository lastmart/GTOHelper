package com.gtohelper.presentation.ui.competitors_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.data.models.Competitor
import com.gtohelper.databinding.ItemCompetitorPreviewBinding
import com.gtohelper.presentation.ui.util.OnItemClickListener

class CompetitorsAdapter(
    val data: List<Competitor>,
    val onItemClickListener: OnItemClickListener<Competitor>
) : RecyclerView.Adapter<CompetitorsAdapter.CompetitorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitorsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCompetitorPreviewBinding.inflate(inflater, parent, false)

        return CompetitorsViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CompetitorsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size

    class CompetitorsViewHolder(
        val binding: ItemCompetitorPreviewBinding,
        val onItemClickListener: OnItemClickListener<Competitor>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Competitor) {
            binding.textViewCompetitorId.text = item.id.toString()
            binding.textViewCompetitorName.text = item.name
            binding.textViewCompetitorTeam.text = item.team
            binding.textViewCompetitorDegree.text = item.degree
            binding.textViewCompetitorGender.text = item.gender

            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(item)
            }

            binding.buttonEdit.setOnClickListener {
                println("Edit")
            }

            binding.buttonDelete.setOnClickListener {
                println("Delete")
            }
        }
    }
}