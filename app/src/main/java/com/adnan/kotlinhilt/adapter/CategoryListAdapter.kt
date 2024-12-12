package com.adnan.kotlinhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adnan.kotlinhilt.databinding.HomeItemBinding
import com.adnan.kotlinhilt.interfaces.AdapterItemClick
import com.adnan.kotlinhilt.model.responseModel.CategoryItem


class CategoryListAdapter(
    private val list: ArrayList<CategoryItem>,
    private val itemClick: AdapterItemClick
) :
    RecyclerView.Adapter<CategoryListAdapter.AccountsVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeItemBinding.inflate(layoutInflater, parent, false)
        return AccountsVH(binding)
    }

    override fun onBindViewHolder(holder: AccountsVH, position: Int) {
        holder.bind(list[position])

        holder.binding.lyModule.setOnClickListener {
            itemClick.onItemClick(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AccountsVH(val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryItem) {
            binding.tvTitle.text = "Category Id= #".plus(item.Id)
            binding.tvDescription.text = "Total recipes = ".plus(item.RecipeCount)
        }
    }

    fun updateData(newData: List<CategoryItem>) {
        val diffResult = DiffUtil.calculateDiff(CategoryDiffCallback(list, newData))
        list.clear()
        list.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class CategoryDiffCallback(
        private val oldList: List<CategoryItem>,
        private val newList: List<CategoryItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].Id == newList[newItemPosition].Id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}