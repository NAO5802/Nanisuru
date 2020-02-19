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

class ItemEditActivity : AppCompatActivity(), ItemDeleteDialog.Listener {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)
        realm = Realm.getDefaultInstance()

        val itemId = intent?.getLongExtra("item_id", -1L)
        if (itemId != -1L) {
            // DBから値をセット
            val item = realm.where<Item>().equalTo("id", itemId).findFirst()
            titleEdit.setText(item?.title)
            placeEdit.setText(item?.place)
            memoEdit.setText(item?.memo)
//　公開設定ボタン（一時使用停止）
//            val rg = findViewById<RadioGroup>(R.id.privateSetting)
//            when (item?.isPrivate) {
//                false -> rg.check(R.id.publicBtn)
//                true -> rg.check(R.id.privateBtn)
//            }
            // 削除ボタン表示
            deleteBtn.visibility = View.VISIBLE
            // タイトルテキスト(編集)
            editLabel.setText(R.string.editLabel)
        } else {
//            // ラジオボタンの初期設定
//            val rg = findViewById<RadioGroup>(R.id.privateSetting)
//            rg.check(R.id.publicBtn)
            // 削除ボタン非表示
            deleteBtn.visibility = View.INVISIBLE
            // タイトルテキスト(追加)
            editLabel.setText(R.string.addLabel)
        }

        // 保存ボタン
        saveBtn.setOnClickListener{ view: View ->
            val titleText = titleEdit.text.toString()
            val placeText = placeEdit.text.toString()
            val memoText = memoEdit.text.toString()

            when (itemId) {
                // アクティビティ追加
                -1L -> {
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
//                            val rg = findViewById<RadioGroup>(R.id.privateSetting)
//                            when (rg.checkedRadioButtonId) {
//                                R.id.publicBtn ->   item.isPrivate = false
//                                R.id.privateBtn ->  item.isPrivate = true
//                            }
                        }
                        Toast.makeText(this, "アクティビティを追加しました", Toast.LENGTH_SHORT)
                            .show()
                        this.finish()
                    }
                }
                // アクティビティ編集
                else -> {
                    if (titleText.isEmpty()) {
                        titleEdit.error = getString(R.string.requiredError)
                    } else {
                        realm.executeTransaction{db: Realm ->
                            val item = db.where<Item>().equalTo("id", itemId).findFirst()
                            item?.title = titleText
                            item?.place = placeText
                            item?.memo = memoText
//                            val rg = findViewById<RadioGroup>(R.id.privateSetting)
//                            when (rg.checkedRadioButtonId) {
//                                R.id.publicBtn ->   item?.isPrivate = false
//                                R.id.privateBtn ->  item?.isPrivate = true
//                            }
                        }
                        Toast.makeText(this, "アクティビティを修正しました", Toast.LENGTH_SHORT)
                            .show()
                        this.finish()
                    }
                }
            }
        }

        // 削除ボタン
        deleteBtn.setOnClickListener{ view: View ->
            val dialog = ItemDeleteDialog()
            dialog.show(supportFragmentManager, "item_delete_dialog")
        }

    }

    override fun deleteItem() {
        val itemId = intent?.getLongExtra("item_id", -1L)
        realm.executeTransaction{db: Realm ->
            db.where<Item>().equalTo("id", itemId)?.findFirst()
                ?.deleteFromRealm()
        }
        Toast.makeText(this, "アクティビティを削除しました", Toast.LENGTH_LONG)
            .show()
        this.finish()
    }

}
