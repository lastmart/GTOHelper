package com.gtohelper.presentation.ui.disciplines_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.databinding.ItemDisciplineBinding
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.util.OnItemClickListener


class DisciplineAdapter(
    var disciplines: List<Discipline>,
    private val onItemClickListener: OnItemClickListener<Discipline>
) : RecyclerView.Adapter<DisciplineAdapter.DisciplineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDisciplineBinding.inflate(inflater, parent, false)

        return DisciplineViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: DisciplineViewHolder, position: Int) {
        val discipline = disciplines[position]
        holder.bind(discipline)
    }

    override fun getItemCount(): Int = disciplines.size

    class DisciplineViewHolder(
        private val binding: ItemDisciplineBinding,
        private val onItemClickListener: OnItemClickListener<Discipline>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(discipline: Discipline) {
            binding.disciplineName.text = discipline.name
            binding.disciplineImage.setImageDrawable(discipline.imageDrawable)

            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(discipline)
            }
        }
    }
}