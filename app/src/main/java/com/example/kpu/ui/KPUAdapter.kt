package com.example.kpu.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kpu.R
import com.example.kpu.database.KPU

class KPUAdapter(
    private val context: Context,
    private val kpuList: List<KPU>,
    private val onDeleteClick: (KPU) -> Unit,
    private val onEditClick: (KPU) -> Unit,
    private val onViewClick: (KPU) -> Unit
) : RecyclerView.Adapter<KPUAdapter.KPUViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KPUViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_data_kpu, parent, false)
        return KPUViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KPUViewHolder, position: Int) {
        val kpu = kpuList[position]
        holder.nomor.text = (position + 1).toString()
        holder.nama.text = kpu.nama

        // Set OnClickListener untuk icon edit, delete, dan view
        holder.ikonDelete.setOnClickListener {
            onDeleteClick(kpu) // Menghapus data
        }

        holder.ikonEdit.setOnClickListener {
            onEditClick(kpu) // Mengarahkan ke halaman edit
        }

        holder.ikonView.setOnClickListener {
            onViewClick(kpu) // Mengarahkan ke halaman view
        }
    }

    override fun getItemCount(): Int = kpuList.size

    // ViewHolder untuk item data
    class KPUViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomor: TextView = itemView.findViewById(R.id.nomor)
        val nama: TextView = itemView.findViewById(R.id.nama)
        val ikonDelete: ImageView = itemView.findViewById(R.id.ikon_delete)
        val ikonEdit: ImageView = itemView.findViewById(R.id.ikon_edit)
        val ikonView: ImageView = itemView.findViewById(R.id.ikon_view)
    }
}
