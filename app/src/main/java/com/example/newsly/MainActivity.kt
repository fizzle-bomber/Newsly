package com.example.newsly

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    lateinit var newsList: RecyclerView
    private var articles = mutableListOf<Article>()
    private lateinit var container: ConstraintLayout
    var pageNumber = 1
    var totalResult = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
        newsList = findViewById<RecyclerView>(R.id.newsList)
        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        //newsList.layoutManager = LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                if (totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    //next page
                    pageNumber++;
                    getNews()
                }
            }

        })
        newsList.layoutManager = layoutManager
        getNews()
    }

        fun getNews() {
            val news = NewsService.newsInstance.getHeadlines("in", pageNumber)
            news.enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    val news = response.body()
                    if (news != null) {
                        totalResult = news.totalResults
                        articles.addAll(news.articles)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.d("Cheezycode", "Error in fetching news", t)
                }
            })
        }
    }
