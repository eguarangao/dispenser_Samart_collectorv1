package com.example.proyect01.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyect01.OnClickListener
import com.example.proyect01.R

import com.example.proyect01.databinding.ItemDispenserBinding
import com.example.proyect01.entity.DispenserEntity

class DispenserDapter(
    private var dispenseres: MutableList<DispenserEntity>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<DispenserDapter.ViewHolder>() {
    private lateinit var nContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        nContext = parent.context
        val view = LayoutInflater.from(nContext).inflate(R.layout.item_dispenser, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dispenser = dispenseres.get(position)
        with(holder) {
            setListener(dispenser)
            binding.tvName.text = dispenser.name
        }
    }

    override fun getItemCount(): Int = dispenseres.size
    fun add(dispenser: DispenserEntity) {
        if (!dispenseres.contains(dispenser)) {
            dispenseres.add(dispenser)
            System.out.println(dispenser.toString() + "ebert")
            notifyItemInserted(dispenseres.size - 1)
        }

    }

    fun update(dispenser: DispenserEntity) {
        val index = dispenseres.indexOf(dispenser)
        if (index != -1) {
            dispenseres.set(index, dispenser)
            notifyItemChanged(index)

        }
    }

    fun setDispenseres(dispenseres: MutableList<DispenserEntity>) {
        this.dispenseres = dispenseres
        notifyDataSetChanged()
    }

    fun delete(dispenserEntity: DispenserEntity) {
        val index = dispenseres.indexOf(dispenserEntity)
        if (index != -1) {
            dispenseres.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDispenserBinding.bind(view)

        fun setListener(dispenserEntity: DispenserEntity) {
            with(binding.root)
            {
                setOnClickListener {
                    listener.onCLick(dispenserEntity.id)
                }
                setOnLongClickListener {
                    listener.onDeleteDispenser(dispenserEntity)
                    true
                }
            }
        }

    }

}






