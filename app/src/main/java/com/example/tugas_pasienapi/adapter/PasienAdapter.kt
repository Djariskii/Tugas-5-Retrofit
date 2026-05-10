package com.example.tugas_pasienapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_pasienapi.R
import com.example.tugas_pasienapi.model.Pasien

class PasienAdapter(private val pasienList: List<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ID-ID baru yang sinkron dengan item_pasien.xml yang baru
        val ivAvatarPasien: ImageView = itemView.findViewById(R.id.ivAvatarPasien)
        val tvNama: TextView          = itemView.findViewById(R.id.tvNama)
        val tvBadgeKelamin: TextView  = itemView.findViewById(R.id.tvBadgeKelamin) // menggantikan tvKelaminTgl
        val tvTanggalLahir: TextView  = itemView.findViewById(R.id.tvTanggalLahir) // ID baru
        val tvTelepon: TextView       = itemView.findViewById(R.id.tvTelepon)
        val tvAlamat: TextView        = itemView.findViewById(R.id.tvAlamat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pasien, parent, false)
        return PasienViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = pasienList[position]

        // Nama
        holder.tvNama.text = pasien.nama

        // Tanggal Lahir (dipisah dari kelamin)
        holder.tvTanggalLahir.text = pasien.tanggal_lahir

        // Badge Jenis Kelamin — ubah tampilan & warna berdasarkan data
        if (pasien.jenis_kelamin == "L") {
            holder.tvBadgeKelamin.text = "♂  Laki-laki"
            holder.tvBadgeKelamin.setBackgroundResource(R.drawable.bg_badge_male)
            // Avatar dengan tint biru untuk pasien laki-laki
            holder.ivAvatarPasien.setBackgroundResource(R.drawable.bg_avatar_placeholder)
            holder.ivAvatarPasien.setColorFilter(
                holder.itemView.context.getColor(android.R.color.holo_blue_dark)
            )
        } else {
            holder.tvBadgeKelamin.text = "♀  Perempuan"
            holder.tvBadgeKelamin.setBackgroundResource(R.drawable.bg_badge_female)
            // Avatar dengan tint pink untuk pasien perempuan
            holder.ivAvatarPasien.setBackgroundResource(R.drawable.bg_avatar_placeholder)
            holder.ivAvatarPasien.setColorFilter(
                holder.itemView.context.getColor(android.R.color.holo_red_light)
            )
        }

        // Telepon & Alamat — logika API tidak berubah sama sekali
        holder.tvTelepon.text = pasien.no_telepon
        holder.tvAlamat.text  = pasien.alamat
    }

    override fun getItemCount(): Int = pasienList.size
}