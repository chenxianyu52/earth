package com.xianyu.earthquake

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xianyu.earthquake.data.EarthShowData
import com.xianyu.earthquake.util.Utils
import com.xianyu.earthquake.util.Utils.MAX_LAN_LONG
import com.xianyu.earthquake.util.Utils.MIN_MAG
import com.xianyu.earthquake.view.ItemAdapter
import com.xianyu.earthquake.viewmodel.EarthViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    private val itemViewModel: EarthViewModel by inject()
    private var itemAdapter: ItemAdapter? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val marginDecoration = ItemMarginDecoration(Utils.getDpToPixel(this, 5))
        recyclerView.addItemDecoration(marginDecoration)
        itemAdapter = ItemAdapter(emptyList(), object : ItemAdapter.OnItemClickListener {
            override fun onItemClick(showData: EarthShowData) {
                val longitude = showData.longitude
                val latitude = showData.latitude
                val magnitude = showData.mag ?: MIN_MAG
                if (longitude == MAX_LAN_LONG || latitude == MAX_LAN_LONG) {
                    Toast.makeText(
                        this@MainActivity,
                        "经纬度数据异常",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
                val intent = Intent(this@MainActivity, MapActivity::class.java)
                val bundle = Bundle()
                bundle.putDouble("longitude", longitude)
                bundle.putDouble("latitude", latitude)
                bundle.putDouble("magnitude", magnitude)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        recyclerView.adapter = itemAdapter


        // 使用生命周期感知的协程，直接收集 Flow
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
                itemViewModel.getItemsFlow().collect { data ->
                    progressBar.visibility = View.GONE
                    if (data.isNullOrEmpty()) {
                        Toast.makeText(this@MainActivity, "数据为空", Toast.LENGTH_SHORT).show()
                        return@collect
                    }
                    itemAdapter?.updateItems(data)
                }
            } catch (e:Exception){
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "数据加载失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class ItemMarginDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.left = margin
            outRect.right = margin
            outRect.top = margin
            outRect.bottom = margin
        }
    }
}