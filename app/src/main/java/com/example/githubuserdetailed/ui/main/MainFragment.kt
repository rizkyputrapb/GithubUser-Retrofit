package com.example.githubuserdetailed.ui.main

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.databinding.MainFragmentBinding
import com.example.githubuserdetailed.model.User
import com.example.githubuserdetailed.ui.OnItemUserListener

class MainFragment : Fragment() {
    lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var mainAdapter: MainAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        context?.getColor(R.color.design_default_color_background)?.let {
            binding.toolbar.setTitleTextColor(
                it
            )
        }
        setHasOptionsMenu(true)
        mainViewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        progressBar = binding.progressBar
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        menu.findItem(R.id.app_bar_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                context?.let {
                    viewModel.getUserList(query)?.observe(viewLifecycleOwner, { userList ->
                        when (userList) {
                            null -> {
                                binding.errorMsg.text = "Error"
                                binding.errorMsg.visibility = View.VISIBLE
                                binding.rvSearch.visibility = View.GONE
                            }
                            else -> when (userList.total_count) {
                                0.0 -> {
                                    binding.errorMsg.text = "No user found!"
                                    binding.errorMsg.visibility = View.VISIBLE
                                    binding.rvSearch.visibility = View.GONE
                                }
                                else -> {
                                    mainAdapter.setUserList(userList = userList.items)
                                    binding.errorMsg.visibility = View.GONE
                                    binding.rvSearch.visibility = View.VISIBLE
                                }
                            }
                        }
                        mainAdapter.notifyDataSetChanged()
                    })
                    viewModel.navigatetoDetail().observe(viewLifecycleOwner, { user ->
                        if (user != null) {
                            val action: NavDirections =
                                MainFragmentDirections.actionMainFragmentToDetailFragment(user)
                            Navigation.findNavController(requireView()).navigate(action)
                            viewModel.onUserMainDetailNavigated()
                        }
                    })
                }
                progressBar.visibility = View.GONE
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSetup()
    }

    private fun rvSetup() {
        mainAdapter = MainAdapter(object : OnItemUserListener {
            override fun OnUserClicked(user: User?) {
                viewModel.onUserClicked(user)
            }
        })
        with(binding.rvSearch) {
            this.adapter = mainAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )

        }
    }

}