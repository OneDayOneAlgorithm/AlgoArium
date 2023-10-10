package com.d204.algo.base

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.d204.algo.ui.adapter.Identifiable

abstract class BaseAdapter<T : Identifiable> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected abstract val differ: AsyncListDiffer<T>

    val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    var list: List<T>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position in list.indices) {
            (holder as Binder<T>).bind(list[position])
        }
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun getItemCount(): Int {
        return list.size
    }

    protected var onItemClickListener: ((T) -> Unit)? = null

    fun setItemClickListener(listener: (T) -> Unit) {
        onItemClickListener = listener
    }

    interface Binder<in T> {
        fun bind(item: T)
    }
}
