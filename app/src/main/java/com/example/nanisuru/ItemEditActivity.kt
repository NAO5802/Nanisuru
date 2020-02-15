package com.example.nanisuru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            val titleText = titleEdit.text.toString()
            val placeText = placeEdit.text.toString()
            val memoText = memoEdit.text.toString()

            if (titleText.isEmpty()) {
                titleEdit.error = getString(R.string.requiredError)
            } else {
                realm.executeTransaction { db: Realm ->
                    val maxId = db.where<Item>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1
                    val item = db.createObject<Item>(nextId)

                    item.title = titleText
                    item.place = placeText
                    item.memo = memoText
                    when (rg.checkedRadioButtonId) {
                        R.id.publicBtn ->   item.isPrivate = false
                        R.id.privateBtn ->  item.isPrivate = true
                    }
                }
                Toast.makeText(this, "アクティビティを追加しました", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


//    // エラー文表示
//    private fun checkError() {
//        val titleText = titleEdit.text.toString()
//        if (titleText.isEmpty()) {
//            titleText.error("文字を入力してください")
//            return
//        }
//    }

}
