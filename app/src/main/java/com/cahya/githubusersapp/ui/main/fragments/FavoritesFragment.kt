package com.cahya.githubusersapp.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahya.githubusersapp.databinding.FragmentFavoritesBinding
import com.cahya.githubusersapp.ui.adapters.UserAdapter
import com.cahya.githubusersapp.ui.detail.DetailActivity
import com.cahya.githubusersapp.ui.viewmodels.MainViewModel
import com.cahya.githubusersapp.ui.viewmodels.ViewModelFactory

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: MainViewModel by viewModels {
            factory
        }

        setupRecyclerView()

        userAdapter.setOnItemClickListener { user ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
            startActivity(intent)
        }

        viewModel.getFavoriteUsers().observe(viewLifecycleOwner) { favoriteUsers ->
            showNoData(favoriteUsers.isNotEmpty())
            binding.progressBar.visibility = View.GONE
            userAdapter.differ.submitList(favoriteUsers)
        }
    }

    private fun showNoData(isNoData: Boolean){
        binding.ivNoFavoriteData.visibility = if (isNoData) View.GONE else View.VISIBLE
        binding.tvNoFavoriteData.visibility = if (isNoData) View.GONE else View.VISIBLE
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