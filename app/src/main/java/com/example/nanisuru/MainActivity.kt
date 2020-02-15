package com.example.nanisuru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import java.io.LineNumberReader


class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()
        list.layoutManager = LinearLayoutManager(this)

        val items = realm.where<Item>().findAll()
        val adapter = ItemAdapter(items)
        list.adapter = adapter

        //　アイテム追加ボタン
        addBtn.setOnClickListener { view ->
            val intent = Intent(this, ItemEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}
