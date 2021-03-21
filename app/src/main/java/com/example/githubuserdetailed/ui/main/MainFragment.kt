package com.example.githubuserdetailed.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.databinding.MainFragmentBinding
import com.example.githubuserdetailed.model.User

class MainFragment : Fragment() {
    lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var userList: List<User>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        setHasOptionsMenu(true)
        mainViewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.option_menu, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        menu.findItem(R.id.app_bar_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                rvSetup(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvSearch
        adapter = MainAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    fun rvSetup(query: String) {
        viewModel.getUserList(query).observe(viewLifecycleOwner, { userList ->
            userList.items
            this.userList = userList.items
        })
        viewModel.listUserLiveData().observe(viewLifecycleOwner, { userList ->
            adapter.setUserList(
                userList
            )
        })
        adapter.notifyDataSetChanged()
    }

}