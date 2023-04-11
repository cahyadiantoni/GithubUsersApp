package com.cahya.githubusersapp.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahya.githubusersapp.utils.Result
import com.cahya.githubusersapp.databinding.ActivitySearchBinding
import com.cahya.githubusersapp.ui.adapters.UserAdapter
import com.cahya.githubusersapp.ui.detail.DetailActivity
import com.cahya.githubusersapp.ui.viewmodels.SearchViewModel
import com.cahya.githubusersapp.ui.viewmodels.ViewModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: SearchViewModel by viewModels {
            factory
        }

        setupRecyclerView()

        userAdapter.setOnItemClickListener { user ->
            val intent = Intent(this@SearchActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.widgetSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchUsers(query).observe(this@SearchActivity) { result ->
                    if (result != null)
                    {
                        when (result)
                        {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                val users = result.data
                                if (users.isEmpty()) {
                                    showNoResultMsg(true)
                                } else {
                                    showNoResultMsg(false)
                                }
                                userAdapter.differ.submitList(users)
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Log.e("SearchActivity", result.error)
                                Toast.makeText(
                                    this@SearchActivity,
                                    "Error Occurred, Please Check Your Internet Connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                binding.widgetSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun showNoResultMsg(isNoResult: Boolean) {
        binding.ivSearchNoResult.visibility = if (isNoResult) View.VISIBLE else View.GONE
        binding.tvSearchNoResult.visibility = if (isNoResult) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvListUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }
}