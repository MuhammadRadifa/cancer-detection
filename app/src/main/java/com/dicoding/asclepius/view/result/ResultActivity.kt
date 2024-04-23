package com.dicoding.asclepius.view.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.state.ResultState
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.utils.NewsAdapter
import com.dicoding.asclepius.view.BaseClass

class ResultActivity : BaseClass() {
    private lateinit var binding: ActivityResultBinding

    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        val result = intent.getStringExtra(EXTRA_RESULT)

        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        result?.let {
            Log.d("Result", "showResult: $it")
            binding.resultText.text = it
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutManager

        viewModel.news.observe(this){
            articles ->
            when(articles){
                is ResultState.Loading -> {
                    binding.shimmerLayout.startShimmer()
                }
                is ResultState.Success -> {
                    showRecyclerView()
                    setNewsData(articles.data)
                }
                is ResultState.Error -> {
                    Log.e("ResultActivity", "Error: $articles")
                    Toast.makeText(this, "Error: $articles", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun setNewsData(consumer:List<ArticlesItem>){
        val adapter = NewsAdapter()
        adapter.submitList(consumer)
        binding.rvNews.adapter = adapter
    }

    private fun showRecyclerView() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.rvNews.visibility = View.VISIBLE
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }

}