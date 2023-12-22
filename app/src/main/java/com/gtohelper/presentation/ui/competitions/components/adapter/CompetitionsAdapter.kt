package com.gtohelper.presentation.ui.competitions.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.databinding.ItemTablePreviewBinding
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.disciplines_list.adapter.DisciplineDiffUtil
import com.gtohelper.presentation.ui.util.OnItemClickListener

class CompetitionsAdapter(
    private var data: List<Competition>,
    private val onItemClickListener: OnItemClickListener<Competition>
) : RecyclerView.Adapter<CompetitionsAdapter.CompetitionViewHolder>() {

    fun setData(newCompetitions: List<Competition>) {
        val diffUtil = CompetitionDiffUtil(data, newCompetitions)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        data = newCompetitions.toList()
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTablePreviewBinding.inflate(inflater, parent, false)

        return CompetitionViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.count()

    class CompetitionViewHolder(
        val binding: ItemTablePreviewBinding,
        val onItemClickListener: OnItemClickListener<Competition>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(competition: Competition) {
            binding.textViewTitle.text = competition.name
            binding.textViewDescription.text = competition.description
            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(competition)
            }
        }
    }
}

class CompetitionDiffUtil(
    private val oldList: List<Competition>,
    private val newList: List<Competition>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}