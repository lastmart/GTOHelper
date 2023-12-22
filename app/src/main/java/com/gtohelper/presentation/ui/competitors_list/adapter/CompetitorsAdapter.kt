package com.gtohelper.presentation.ui.competitors_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.databinding.ItemCompetitorPreviewBinding
import com.gtohelper.domain.models.Competitor
import com.gtohelper.presentation.ui.util.OnItemClickListener

class CompetitorsAdapter(
    private var data: List<Competitor>,
    private val onItemClickListener: OnItemClickListener<Competitor>
) : RecyclerView.Adapter<CompetitorsAdapter.CompetitorsViewHolder>() {

    fun setData(newCompetitors: List<Competitor>) {
        val diffUtil = CompetitorDiffUtil(data, newCompetitors)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        data = newCompetitors.toList()
        diffResults.dispatchUpdatesTo(this)
    }


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
        private val binding: ItemCompetitorPreviewBinding,
        private val onItemClickListener: OnItemClickListener<Competitor>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Competitor) {
            binding.textViewCompetitorId.text = item.number.toString()
            binding.textViewCompetitorName.text = item.name
            binding.textViewCompetitorTeam.text = item.teamName
            binding.textViewCompetitorDegree.text = item.degree.toString()
            binding.textViewCompetitorGender.text = item.gender.string

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
class CompetitorDiffUtil(
    private val oldList: List<Competitor>,
    private val newList: List<Competitor>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}