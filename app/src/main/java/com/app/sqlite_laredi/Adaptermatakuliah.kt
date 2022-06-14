package com.app.sqlite_laredi

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class Adaptermatakuliah  (
    private val getContext: Context,
    private val customListItem: ArrayList<matakuliah>
):
    ArrayAdapter <matakuliah>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.activity_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmMatkul = listLayout.findViewById(R.id.tvNmMatkul)
                tvKdMatKul = listLayout.findViewById(R.id.tvKdMatkul)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmMatkul!!.setText(listItem.nmMatkul)
        holder.tvKdMatKul!!.setText(listItem.kdMatkul)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, entrymatakuliahactivity::class.java)
            i.putExtra("kode", listItem.kdMatkul)
            i.putExtra("nama", listItem.nmMatkul)
            i.putExtra("sks", listItem.sks)
            i.putExtra("sifat", listItem.sifat)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener{
            val db = dbhelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdMatKul!!.text
            val nama = holder.tvNmMatkul!!.text
            with (alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage(""" Apakah Anda Yakin akan menghapus data ini??
                    
                    $nama[$kode]
                    
                """.trimIndent())
                setPositiveButton("Ya") {_, _->
                    if (db.hapus("$kode"))
                        Toast   .makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmMatkul: TextView? = null
        internal var tvKdMatKul: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}