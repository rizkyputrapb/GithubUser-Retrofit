package com.example.gitfav

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitfav.databinding.ConsumerFavFragmentBinding

class ConsumerFavFragment : Fragment() {

    companion object {
        fun newInstance() = ConsumerFavFragment()
    }

    private lateinit var viewModel: ConsumerFavViewModel
    private lateinit var binding: ConsumerFavFragmentBinding
    private lateinit var consumerAdapter: ConsumerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.consumer_fav_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ConsumerFavViewModel::class.java)
        activity?.applicationContext?.let { observerSetup(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSetup()
    }

    fun rvSetup() {
        consumerAdapter = ConsumerAdapter()
        with(binding.rvConsumer) {
            this.adapter = consumerAdapter
            this.layoutManager = GridLayoutManager(
                activity?.applicationContext,
                2,
                GridLayoutManager.VERTICAL,
                false
            )
            this.setHasFixedSize(true)
        }
    }

    fun cursorMapping(it: Cursor?): ArrayList<FavConsumer> {
        val list = ArrayList<FavConsumer>()
        while (it?.moveToNext() == true) {
            it?.apply {
                list.add(
                    FavConsumer(
                        getString(getColumnIndexOrThrow(FavConsumer.USERNAME)),
                        getString(getColumnIndexOrThrow(FavConsumer.AVATAR_URL)),
                        getString(getColumnIndexOrThrow(FavConsumer.NAME))
                    )
                )
            }
        }
        it?.close()
        return list
    }

    fun observerSetup(context: Context) {
        viewModel.setFavList(context).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let {
                            viewModel.getFavList().observe(viewLifecycleOwner, { cursor ->
                                val list = cursorMapping(cursor)
                                consumerAdapter.setConsumerList(list)
                                consumerAdapter.notifyDataSetChanged()
                                Log.i("ContentResolver", "Data retrieved successfully")
                            })
                        }
                    }
                    Status.ERROR -> {
                        Log.w("Favorite", "Error retrieving favorites: ${resource.message}")
                    }
                }
            }
        })

    }

}