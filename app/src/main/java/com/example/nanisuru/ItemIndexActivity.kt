package com.example.nanisuru

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        //　未完了アイテム一覧を取得・表示
        val items = realm.where<Item>().equalTo("finished", false).findAll()
        val adapter = ItemAdapter(items)
        list.adapter = adapter


        // アイテムがクリックされた時
        adapter.setOnItemClickListener { id->
            val intent = Intent(this, ItemEditActivity::class.java)
                .putExtra("item_id", id)
            startActivity(intent)
        }


        // アイテムがスワイプされた時
        val mIth = ItemTouchHelper(
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
        mIth.attachToRecyclerView(list)
        list.addItemDecoration(mIth)


    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }




}

