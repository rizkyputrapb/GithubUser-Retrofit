package com.example.githubuserdetailed.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.api.Status
import com.example.githubuserdetailed.databinding.MainFragmentBinding
import com.example.githubuserdetailed.model.User

class MainFragment : Fragment() {
    lateinit var binding: MainFragmentBinding

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
        vmSetup()
        progressBar = binding.progressBar
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_settings -> {
                val action = MainFragmentDirections
                    .actionMainFragmentToSettingsFragment()
                view?.findNavController()?.navigate(action)
                true
            }
            R.id.app_bar_favorites -> {
                val action = MainFragmentDirections
                    .actionMainFragmentToFavoritesFragment()
                view?.findNavController()?.navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
                observerSetup(query)
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

        }
    }

    private fun vmSetup() {
        mainViewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun observerSetup(query: String) {
        viewModel.getUserList(query).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { userList ->
                            when (userList.total_count) {
                                0 -> {
                                    binding.rvSearch.visibility = View.GONE
                                    progressBar.visibility = View.GONE
                                    binding.errorMsg.visibility = View.VISIBLE
                                    binding.errorMsg.text = getString(R.string.errorNoUserFound)
                                }
                                else -> {
                                    binding.rvSearch.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                    binding.errorMsg.visibility = View.GONE
                                    mainAdapter.setUserList(userList = userList.items)
                                    mainAdapter.notifyDataSetChanged()
                                    binding.inspectocat.visibility = View.GONE
                                    binding.searchMotto.visibility = View.GONE
                                }
                            }
                            Log.d(
                                "STATUS",
                                "SUCCESS: data retrieved: ${userList.total_count}"
                            )
                        }
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        binding.rvSearch.visibility = View.GONE
                        binding.inspectocat.visibility = View.GONE
                        binding.searchMotto.visibility = View.GONE
                        Log.d("STATUS", "LOADING....")
                    }
                    Status.ERROR -> {
                        binding.errorMsg.visibility = View.VISIBLE
                        binding.errorMsg.text = it.message
                        binding.inspectocat.visibility = View.GONE
                        binding.searchMotto.visibility = View.GONE
                        Log.d("STATUS", "ERROR: ${it.message}")
                    }
                }
            }
        })
        viewModel.navigatetoDetail().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                val action: NavDirections =
                    MainFragmentDirections.actionMainFragmentToDetailFragment2(user)
                Navigation.findNavController(requireView()).navigate(action)
                viewModel.onUserMainDetailNavigated()
            }
        })
    }

}