package com.example.nanisuru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_item_index.*

class ItemIndexActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_index)

        realm = Realm.getDefaultInstance()
        list.layoutManager = LinearLayoutManager(this)

        //　アイテム一覧を取得・表示
        val items = realm.where<Item>().findAll()
        val adapter = ItemAdapter(items)
        list.adapter = adapter


        // 個別のアイテムがクリックされた時
        adapter.setOnItemClickListener { id->
            val intent = Intent(this, ItemEditActivity::class.java)
                .putExtra("item_id", id)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}
