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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //　アイテム追加ボタン
        addBtn.setOnClickListener { view ->
            val intent = Intent(this, ItemEditActivity::class.java)
            startActivity(intent)
        }

        // 一覧表示ボタン
        indexBtn.setOnClickListener { view ->
            val intent = Intent(this, ItemIndexActivity::class.java)
            startActivity(intent)
        }
    }



}
