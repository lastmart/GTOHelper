package com.gtohelper.presentation.ui.competitors_results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.databinding.ItemCompetitorResultsPreviewBinding
import com.gtohelper.presentation.ui.util.OnItemClickListener

class CompetitorResultsAdapter(
    val data: List<CompetitorResults>,
    val onItemClickListener: OnItemClickListener<CompetitorResults>
) : RecyclerView.Adapter<CompetitorResultsAdapter.CompetitorResultsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitorResultsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCompetitorResultsPreviewBinding.inflate(inflater, parent, false)

        return CompetitorResultsViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CompetitorResultsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.count()

    class CompetitorResultsViewHolder(
        val binding: ItemCompetitorResultsPreviewBinding,
        val onItemClickListener: OnItemClickListener<CompetitorResults>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CompetitorResults) {
            binding.textViewCompetitorId.text = item.id.toString()
            binding.textViewCompetitorName.text = item.name
            binding.textViewCompetitorTeam.text = item.team
            binding.textViewCompetitorDegree.text = item.degree
            binding.textViewCompetitorGender.text = item.gender
            binding.textViewCompetitorPoints.text = item.points.toString()

            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(item)
            }
        }
    }
}