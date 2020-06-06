package com.matthew.carvalhodagenais.cubedcollector.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.carvalhodagenais.cubedcollector.MainActivity
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.adapters.ConsoleListRecyclerAdapter
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.ItemTouchHelperGenerator
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.RecyclerAdapterItemClickGenerator
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.RecyclerSearchQueryListenerGenerator
import kotlinx.android.synthetic.main.fragment_console_list.*

class ConsoleListFragment: Fragment() {

    private lateinit var recyclerAdapter: ConsoleListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_console_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerAdapter = ConsoleListRecyclerAdapter()
        console_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = recyclerAdapter
        }

        (activity as MainActivity).getConsoleListViewModel().getAllConsoles()
            .observe(viewLifecycleOwner, Observer {
                recyclerAdapter.submitList(it)
                recyclerAdapter.setSearchableList(it)

                if (it.count() < 1) {
                    no_consoles_layout.visibility = View.VISIBLE
                } else {
                    no_consoles_layout.visibility = View.INVISIBLE
                }

                val onClickGenerator =
                    RecyclerAdapterItemClickGenerator()
                recyclerAdapter.setItemOnClickListener(
                    onClickGenerator.generate(
                        (activity as MainActivity).getConsoleDetailViewModel(),
                        findNavController()
                    )
                )
            })

        // Make onSwipe ItemTouchHelper
        ItemTouchHelper(
            ItemTouchHelperGenerator(
                requireContext()
            ).generate(
                recyclerAdapter,
                (activity as MainActivity).getConsoleListViewModel()
            )
        ).attachToRecyclerView(console_list_recycler_view)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_console_list, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        val generator =
            RecyclerSearchQueryListenerGenerator()
        searchView.setOnQueryTextListener(generator.generateQueryTextListener(recyclerAdapter))
        searchItem.setOnActionExpandListener(generator.generateExpandListener())
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_add -> {
            val action =
                ConsoleListFragmentDirections.actionNavConsolesToConsoleAddEditFragment(
                    ConsoleAddEditFragment.ADD_REQUEST, getString(R.string.navigation_console_add_title)
                )
            (activity as MainActivity).getConsoleAddEditViewModel().clearCurrentlySelectedConsole()
            findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}