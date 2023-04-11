package com.cahya.githubusersapp.ui.detail.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.cahya.githubusersapp.R
import com.cahya.githubusersapp.utils.Result
import com.cahya.githubusersapp.data.local.entity.ModelUser
import com.cahya.githubusersapp.databinding.FragmentDescriptionBinding
import com.cahya.githubusersapp.ui.detail.DetailActivity
import com.cahya.githubusersapp.ui.viewmodels.*

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: DetailViewModel by viewModels {
            factory
        }

        val username = arguments?.getString(DetailActivity.EXTRA_USERNAME)

        binding.ivShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Connect with $username. \n Klik this link : https://github.com/$username")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }

        if (username != null) {
            viewModel.getUser(username).observe(viewLifecycleOwner) { result ->
                if (result != null)
                {
                    when (result)
                    {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            binding.cbFavorite.isChecked = result.data.favorite
                            setUserDescription(result.data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Log.e("DescriptionFragment", result.error)
                            Toast.makeText(context, R.string.network_error_msg, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            binding.cbFavorite.setOnClickListener {
                if (!binding.cbFavorite.isChecked)
                {
                    viewModel.removeFromFavorite(username)
                    Toast.makeText(context, "User Removed From Favorites", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    viewModel.addToFavorite(username)
                    Toast.makeText(context, "User Added To Favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUserDescription(user: ModelUser) {
        binding.apply {
            tvFullName.text = user.name ?: "-"
            tvUsername.text = user.login
            tvUsername.text = user.login
            tvCountRepo.text = user.publicRepos.toString()
            tvCountFollowers.text = user.followers.toString()
            tvCountFollowing.text = user.following.toString()
            tvLocation.text = user.location ?: "-"
            tvCompany.text = user.company ?: "-"
        }
        Glide.with(this)
            .load(user.avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_person)
            .into(binding.circleIvAvatar)
        binding.apply {
            tvFullName.visibility = View.VISIBLE
            tvUsername.visibility = View.VISIBLE
            circleIvAvatar.visibility = View.VISIBLE
            tvLocation.visibility = View.VISIBLE
            tvCompany.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}