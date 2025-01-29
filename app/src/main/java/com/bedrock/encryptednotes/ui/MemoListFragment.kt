package com.bedrock.encryptednotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedrock.encryptednotes.misc.MemoAdapter
import com.bedrock.encryptednotes.MemoApplication
import com.bedrock.encryptednotes.data.MemoModel
import com.bedrock.encryptednotes.viewmodel.MemoViewModel
import com.bedrock.encryptednotes.viewmodel.MemoViewModelFactory
import com.bedrock.encryptednotes.R
import com.bedrock.encryptednotes.data.MemoSharedPrefs
import com.bedrock.encryptednotes.databinding.FragmentMemoListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MemoListFragment : Fragment() {

    private var _binding: FragmentMemoListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MemoViewModel by viewModels {
        MemoViewModelFactory((activity?.application as MemoApplication).repository, MemoSharedPrefs.getKey(requireContext()))
    }


    private lateinit var memoAdapter: MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoListBinding.inflate(inflater, container, false)


        memoAdapter = MemoAdapter(viewModel.items.value?.toMutableList()?: mutableListOf(), object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(item: MemoModel) {
                val memoPlainText = viewModel.getMemoPlainText(item)
                val dialogFragment = ShowMemoDialogFragment.newInstance(memoPlainText)
                dialogFragment.show(parentFragmentManager, "ShowMemoDialogFragment")
            }

            override fun onLongClick(item: MemoModel) {

                val dialogFragment = DeleteDialog.newInstance(item.title, item.id)
                dialogFragment.show(parentFragmentManager, "ShowMemoDialogFragment")
            }
        })

        _binding?.recyclerView?.let {
            it.layoutManager = LinearLayoutManager(this@MemoListFragment.context)
            it.adapter = memoAdapter
        }

        _binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterItems(newText.orEmpty())
                return false
            }
        })

        binding.fab.setOnClickListener { view ->
            val newMemoModalFragment = NewMemoModalFragment()
            newMemoModalFragment.show(childFragmentManager, "NewMemoModalFragment")
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchEditText = binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        viewModel.items.observe(viewLifecycleOwner, Observer { items ->
            memoAdapter.updateList(items?: listOf())
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}