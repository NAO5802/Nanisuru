package com.example.nanisuru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_item_edit.*

class ItemEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)
        realm = Realm.getDefaultInstance()
        // ラジオボタンの初期設定
        val rg = findViewById<RadioGroup>(R.id.privateSetting)
        rg.check(R.id.publicBtn)


        // アクティビティ追加
        saveBtn.setOnClickListener{ view: View ->
            realm.executeTransaction { db: Realm ->
                val maxId = db.where<Item>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1
                val item = db.createObject<Item>(nextId)

                item.title = titleEdit.text.toString()
                item.place = placeEdit.text.toString()
                item.memo = memoEdit.text.toString()
                when (rg.checkedRadioButtonId) {
                    R.id.publicBtn -> item.private = false
                    R.id.privateBtn ->  item.private = true
                }
            }
            Toast.makeText(this, "アクティビティを追加しました", Toast.LENGTH_SHORT)
                .show()
        }

    }
}
