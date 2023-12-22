package com.gtohelper.presentation.ui.disciplines_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.databinding.ItemDisciplineBinding
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.OnItemLongClickListener


class DisciplineAdapter(
    private var disciplines: List<DisciplinePresentation>,
    private val onItemClickListener: OnItemClickListener<DisciplinePresentation>,
    private val onItemLongClickListener: OnItemLongClickListener<DisciplinePresentation>
) : RecyclerView.Adapter<DisciplineAdapter.DisciplineViewHolder>() {

    fun setData(newDisciplines: List<DisciplinePresentation>) {
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
        private val onItemClickListener: OnItemClickListener<DisciplinePresentation>,
        private val onItemLongClickListener: OnItemLongClickListener<DisciplinePresentation>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(discipline: DisciplinePresentation) {
            binding.disciplineName.text = discipline.name
            binding.disciplineImage.setImageResource(discipline.imageResource)

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
                && oldItem.imageResource == newItem.imageResource
    }
}