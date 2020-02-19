package com.example.nanisuru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        val items =   realm.where<Item>().equalTo("finished", false).findAll()

        // ランダム表示ボタン
        randomBtn.setOnClickListener {
            val r = Random(System.nanoTime())
            val firstRandomNumber = r.nextInt(items.size)
            val firstRandomObject: Item? = items.get(firstRandomNumber)
            mainText.text = firstRandomObject?.title.toString()
        }

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

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }



}
