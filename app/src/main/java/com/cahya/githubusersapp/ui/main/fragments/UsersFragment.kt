package com.cahya.githubusersapp.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahya.githubusersapp.databinding.FragmentUsersBinding
import com.cahya.githubusersapp.ui.viewmodels.ViewModelFactory
import com.cahya.githubusersapp.ui.adapters.UserAdapter
import com.cahya.githubusersapp.ui.viewmodels.MainViewModel
import com.cahya.githubusersapp.utils.Result
import com.cahya.githubusersapp.ui.detail.DetailActivity
import com.cahya.githubusersapp.ui.search.SearchActivity

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: MainViewModel by viewModels {
            factory
        }

        viewModel.getThemeSetting().observe(viewLifecycleOwner) { isNightMode ->
            setTheme(isNightMode)
        }

        binding.cbAppTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }

        setupRecyclerView()

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        userAdapter.setOnItemClickListener { user ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
            startActivity(intent)
        }

        viewModel.getUsers().observe(viewLifecycleOwner) { result ->
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
                        userAdapter.differ.submitList(users)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e("UsersFragment", result.error)
                        Toast.makeText(
                            context,
                            "Error!, Please Check Your Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setTheme(isNightMode: Boolean){
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.cbAppTheme.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.cbAppTheme.isChecked = false
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvListUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}