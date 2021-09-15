package com.example.newsly

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=ins&apiKey=f1e970b8cb7c4fb3be9e3ae10017646a
//https://newsapi.org/v2/everything?q=apple&from=2021-09-05&to=2021-09-05&sortBy=popularity&apiKey=f1e970b8cb7c4fb3be9e3ae10017646a

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "f1e970b8cb7c4fb3be9e3ae10017646a"
interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country:String ,@Query("page") page: Int):Call<News>
}

object NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)

    }
}