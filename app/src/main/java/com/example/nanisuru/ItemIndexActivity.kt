package com.example.nanisuru

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_item_index.*


class ItemIndexActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_index)

        realm = Realm.getDefaultInstance()
        list.layoutManager = LinearLayoutManager(this)

        // 表示するアイテムを取得
        finishedSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // nothing
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val isFinished = finishedSpinner.selectedItemPosition
                    val items = if (isFinished == 0 ) {
                        realm.where<Item>().equalTo("finished", false).findAll()
                    } else {
                        realm.where<Item>().equalTo("finished", true).findAll()
                    }
                    val adapter = ItemAdapter(items)
                    list.adapter = adapter

                    // アイテムがクリックされた時
                    adapter.setOnItemClickListener { id->
                        val intent = Intent(this@ItemIndexActivity, ItemEditActivity::class.java)
                            .putExtra("item_id", id)
                        startActivity(intent)
                    }
                }
            }


        // アイテムがスワイプされた時
        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val itemId = viewHolder.getItemId()

                    realm.executeTransaction { db: Realm ->
                        val item = db.where<Item>().equalTo("id", itemId).findFirst()
                        when (item?.finished) {
                            false -> item?.finished = true
                            true -> item?.finished =false
                        }
                    }
                }
            })
        itemTouchHelper.attachToRecyclerView(list)
        list.addItemDecoration(itemTouchHelper)


    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }




}

