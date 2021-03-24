package com.example.githubuserdetailed.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.databinding.FragmentFollowersBinding
import com.example.githubuserdetailed.databinding.ItemFollowsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "username"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var username: String? = null
    private lateinit var viewModel: FollowersViewModel
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var followerAdapter: FollowersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_followers, container, false)
        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvFollowersSetup(this.username)
    }

    fun rvFollowersSetup(username: String?) {
        followerAdapter = FollowersAdapter()
        with(binding.rvFollowers){
            this.adapter = followerAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
        viewModel.getUserList(username).observe(viewLifecycleOwner, { userList ->
            when (userList) {
                null -> {
                    binding.followersErrorMsg.text = "Error"
                    binding.followersErrorMsg.visibility = View.VISIBLE
                    binding.followersErrorMsg.visibility = View.GONE
                }
                else -> when (userList.size) {
                    null -> {
                        binding.followersErrorMsg.text = "This user has no followers!"
                        binding.followersErrorMsg.visibility = View.VISIBLE
                        binding.followersErrorMsg.visibility = View.GONE
                    }
                    else -> {
                        followerAdapter.setUsersList(userList)
                        binding.followersErrorMsg.visibility = View.GONE
                        binding.rvFollowers.visibility = View.VISIBLE
                    }
                }
            }
            followerAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}