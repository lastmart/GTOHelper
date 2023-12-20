package com.gtohelper.presentation.ui.disciplines_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.databinding.ItemDisciplineBinding
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.OnItemLongClickListener


class DisciplineAdapter(
    var disciplines: List<Discipline>,
    private val onItemClickListener: OnItemClickListener<Discipline>,
    private val onItemLongClickListener: OnItemLongClickListener<Discipline>
) : RecyclerView.Adapter<DisciplineAdapter.DisciplineViewHolder>() {

    fun setData(newDisciplines: List<Discipline>) {
        val diffUtil = DisciplineDiffUtil(disciplines, newDisciplines)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        disciplines = newDisciplines.toList()
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDisciplineBinding.inflate(inflater, parent, false)

        return DisciplineViewHolder(binding, onItemClickListener, onItemLongClickListener)
    }

    override fun onBindViewHolder(holder: DisciplineViewHolder, position: Int) {
        val discipline = disciplines[position]
        holder.bind(discipline)
    }

    override fun getItemCount(): Int = disciplines.size

    class DisciplineViewHolder(
        private val binding: ItemDisciplineBinding,
        private val onItemClickListener: OnItemClickListener<Discipline>,
        private val onItemLongClickListener: OnItemLongClickListener<Discipline>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(discipline: Discipline) {
            binding.disciplineName.text = discipline.name
            binding.disciplineImage.setImageDrawable(discipline.imageDrawable)

            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(discipline)
            }

            binding.root.setOnLongClickListener {
                onItemLongClickListener.onItemLongClicked(discipline)
            }
        }
    }
}

class DisciplineDiffUtil(
    private val oldList: List<Discipline>,
    private val newList: List<Discipline>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.name == newItem.name
                && oldItem.imageDrawable == newItem.imageDrawable
    }
}