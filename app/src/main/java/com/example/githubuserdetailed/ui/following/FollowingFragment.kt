package com.example.githubuserdetailed.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.databinding.FragmentFollowingBinding
import com.example.githubuserdetailed.ui.followers.FollowersAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvFollowingSetup(this.username)
    }

    fun rvFollowingSetup(username: String?) {
        followerAdapter = FollowingAdapter()
        with(binding.rvFollowing){
            this.adapter = followerAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
        viewModel.getUserList(username).observe(viewLifecycleOwner, { userList ->
            when (userList) {
                null -> {
                    binding.followingErrorMsg.text = "Error"
                    binding.followingErrorMsg.visibility = View.VISIBLE
                    binding.followingErrorMsg.visibility = View.GONE
                }
                else -> when (userList.size) {
                    null -> {
                        binding.followingErrorMsg.text = "This user has no followers!"
                        binding.followingErrorMsg.visibility = View.VISIBLE
                        binding.followingErrorMsg.visibility = View.GONE
                    }
                    else -> {
                        followerAdapter.setUsersList(userList)
                        binding.followingErrorMsg.visibility = View.GONE
                        binding.rvFollowing.visibility = View.VISIBLE
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