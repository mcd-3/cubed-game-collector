package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.AccessoryListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.helpers.generators.RecyclerAdapterItemClickGenerator
import kotlinx.android.synthetic.main.fragment_accessory_list.*

class AccessoryListFragment: Fragment() {

    private lateinit var recyclerAdapter: AccessoryListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_accessory_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerAdapter = AccessoryListRecyclerAdapter()
        accessory_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = recyclerAdapter
        }

        (activity as MainActivity).getAccessoryListViewModel().getAllAccessories()
            .observe(viewLifecycleOwner, Observer {
                recyclerAdapter.submitList(it)

                if (it.count() < 1) {
                    no_accessories_layout.visibility = View.VISIBLE
                } else {
                    no_accessories_layout.visibility = View.INVISIBLE
                }

                val onClickGenerator = RecyclerAdapterItemClickGenerator()
                recyclerAdapter.setItemOnClickListener(
                    onClickGenerator.generate(
                        (activity as MainActivity).getAccessoryDetailViewModel(),
                        findNavController()
                    )
                )
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_accessory_list, menu)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_add -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}