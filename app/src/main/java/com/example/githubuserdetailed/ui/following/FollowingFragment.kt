package com.example.githubuserdetailed.ui.following

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.api.Status
import com.example.githubuserdetailed.databinding.FragmentFollowingBinding
import com.example.githubuserdetailed.ui.followers.FollowersAdapter

private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    private var username: String? = null
    private lateinit var viewModel: FollowingViewModel
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followerAdapter: FollowingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        followerAdapter = FollowingAdapter()
        vmSetup()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvFollowingSetup(this.username)
    }

    private fun vmSetup() {
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun rvFollowingSetup(username: String?) {
        with(binding.rvFollowing) {
            this.adapter = followerAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
        oberserverSetup(username)
    }

    private fun oberserverSetup(query: String?) {
        viewModel.getUserList(query).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { userList ->
                            when {
                                userList.isNotEmpty() -> {
                                    binding.rvFollowing.visibility = View.VISIBLE
                                    binding.progressBar2.visibility = View.GONE
                                    followerAdapter.setUsersList(userList)
                                    followerAdapter.notifyDataSetChanged()
                                }
                                else -> {
                                    binding.rvFollowing.visibility = View.GONE
                                    binding.progressBar2.visibility = View.GONE
                                    binding.followingErrorMsg.visibility = View.VISIBLE
                                    binding.followingErrorMsg.text =
                                        getString(R.string.errorNoFollowing)
                                }
                            }
                            Log.d(
                                "STATUS",
                                "SUCCESS: data retrieved: ${userList.size}"
                            )
                        }
                    }
                    Status.LOADING -> {
                        binding.rvFollowing.visibility = View.VISIBLE
                        binding.progressBar2.visibility = View.VISIBLE
                        Log.d("STATUS", "LOADING....")
                    }
                    Status.ERROR -> {
                        binding.rvFollowing.visibility = View.VISIBLE
                        binding.progressBar2.visibility = View.VISIBLE
                        binding.followingErrorMsg.visibility = View.VISIBLE
                        binding.followingErrorMsg.text = it.message
                        Log.d("STATUS", "ERROR: ${it.message}")
                    }
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(username: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}