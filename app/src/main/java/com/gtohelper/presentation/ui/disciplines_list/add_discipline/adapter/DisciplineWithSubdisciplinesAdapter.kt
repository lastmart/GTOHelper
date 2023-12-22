package com.gtohelper.presentation.ui.disciplines_list.add_discipline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.R
import com.gtohelper.databinding.ItemDisciplineWithSubdisciplinesBinding
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.util.OnItemClickListener

class DisciplineWithSubDisciplinesAdapter(
    private var disciplines: List<DisciplinePresentation>,
    private val onSubDisciplineClickListener: OnItemClickListener<SubDiscipline>
) : RecyclerView.Adapter<DisciplineWithSubDisciplinesAdapter.DisciplineWithSubDisciplinesViewHolder>() {

    fun setData(newDisciplines: List<DisciplinePresentation>) {
        val diffUtil = DisciplineWithSubDisciplinesDiffUtil(disciplines, newDisciplines)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        disciplines = newDisciplines.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DisciplineWithSubDisciplinesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDisciplineWithSubdisciplinesBinding.inflate(inflater, parent, false)

        return DisciplineWithSubDisciplinesViewHolder(
            binding,
            onSubDisciplineClickListener
        )
    }

    override fun onBindViewHolder(holder: DisciplineWithSubDisciplinesViewHolder, position: Int) {
        val discipline = disciplines[position]
        holder.bind(discipline)
    }

    override fun getItemCount(): Int = disciplines.size

    class DisciplineWithSubDisciplinesViewHolder(
        private val binding: ItemDisciplineWithSubdisciplinesBinding,
        private val onSubDisciplineClickListener: OnItemClickListener<SubDiscipline>
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.subDisciplinesRecyclerView.layoutManager =
                LinearLayoutManager(binding.root.context)
        }

        fun bind(discipline: DisciplinePresentation) {
            val subDisciplineAdapter = SubDisciplineAdapter(
                subDisciplines = discipline.subDisciplines,
                onItemClickListener = onSubDisciplineClickListener
            )

            binding.subDisciplinesRecyclerView.adapter = subDisciplineAdapter

            binding.root.setOnClickListener {
                expandOrHide(discipline)
            }

            binding.disciplineImage.setImageResource(R.drawable.discipline_sprinting)
            binding.disciplineName.text = discipline.name
        }

        private fun expandOrHide(discipline: DisciplinePresentation) {
            binding.subDisciplinesRecyclerView.isVisible = !discipline.isExpanded
            discipline.isExpanded = !discipline.isExpanded
        }
    }

}

class DisciplineWithSubDisciplinesDiffUtil(
    private val oldList: List<DisciplinePresentation>,
    private val newList: List<DisciplinePresentation>
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
                && oldItem.subDisciplines == newItem.subDisciplines
                && oldItem.isExpanded == newItem.isExpanded
    }
}