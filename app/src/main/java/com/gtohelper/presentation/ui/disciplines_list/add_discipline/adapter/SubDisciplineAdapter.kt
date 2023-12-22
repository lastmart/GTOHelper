package com.gtohelper.presentation.ui.disciplines_list.add_discipline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.databinding.ItemSubDisciplineBinding
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.presentation.ui.util.OnItemClickListener

class SubDisciplineAdapter(
    private var subDisciplines: List<SubDiscipline>,
    private val onItemClickListener: OnItemClickListener<SubDiscipline>
) : RecyclerView.Adapter<SubDisciplineAdapter.SubDisciplineViewHolder>() {

    fun setData(newSubDisciplines: List<SubDiscipline>) {
        val diffUtil = SubDisciplineDiffUtil(subDisciplines, newSubDisciplines)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        subDisciplines = newSubDisciplines.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubDisciplineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubDisciplineBinding.inflate(inflater, parent, false)

        return SubDisciplineViewHolder(
            binding,
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: SubDisciplineViewHolder, position: Int) {
        val subDiscipline = subDisciplines[position]
        holder.bind(subDiscipline)
    }

    override fun getItemCount(): Int = subDisciplines.size

    class SubDisciplineViewHolder(
        private val binding: ItemSubDisciplineBinding,
        private val onItemClickListener: OnItemClickListener<SubDiscipline>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subDiscipline: SubDiscipline) {
            binding.disciplineName.text = subDiscipline.name
            binding.disciplineImage.setImageResource(subDiscipline.imageResource)
            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(subDiscipline)
            }
        }
    }
}

class SubDisciplineDiffUtil(
    private val oldList: List<SubDiscipline>,
    private val newList: List<SubDiscipline>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem == newItem
    }
}