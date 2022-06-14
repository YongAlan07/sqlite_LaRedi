package com.app.sqlite_laredi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adpmatkul: Adaptermatakuliah
    private lateinit var dataMatkul: ArrayList<matakuliah>
    private lateinit var lvMataKuliah: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvMataKuliah = findViewById(R.id.lvMataKuliah)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataMatkul = ArrayList()
        adpmatkul = Adaptermatakuliah(this@MainActivity, dataMatkul)

        lvMataKuliah.adapter = adpmatkul

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent (this@MainActivity, entrymatakuliahactivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) refresh()
    }

    private  fun refresh() {
        val db = dbhelper(this@MainActivity)
        val data = db.tampil()
        repeat(dataMatkul.size) { dataMatkul.removeFirst()}
        if(data.count>0) {
            while (data.moveToNext()) {
                val matkul = matakuliah(
                    data.getString(0),
                    data.getString(1),
                    data.getInt(2),
                    data.getString(3),
                )
                adpmatkul.add(matkul)
                adpmatkul.notifyDataSetChanged()
            }
            lvMataKuliah.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE

        } else {
            lvMataKuliah.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}