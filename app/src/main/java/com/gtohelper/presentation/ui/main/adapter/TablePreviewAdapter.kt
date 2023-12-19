package com.gtohelper.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gtohelper.data.models.TablePreview
import com.gtohelper.databinding.ItemTablePreviewBinding
import com.gtohelper.presentation.ui.util.OnItemClickListener

class TablePreviewAdapter(
    val data: List<TablePreview>,
    val onItemClickListener: OnItemClickListener<TablePreview>
) : RecyclerView.Adapter<TablePreviewAdapter.TablePreviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablePreviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTablePreviewBinding.inflate(inflater, parent, false)

        return TablePreviewViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: TablePreviewViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.count()

    class TablePreviewViewHolder(
        val binding: ItemTablePreviewBinding,
        val onItemClickListener: OnItemClickListener<TablePreview>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tablePreview: TablePreview) {
            binding.textViewTitle.text = tablePreview.title
            binding.textViewDescription.text = tablePreview.description
            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(tablePreview)
            }
        }
    }
}