package de.example.challenge.flickrapp.fragments.childFragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.adapters.recycler.AdapterItem
import de.example.challenge.flickrapp.adapters.recycler.DataAdapter
import de.example.challenge.flickrapp.adapters.recycler.OnHistoryItemListener
import de.example.challenge.flickrapp.fragments.childFragments.search.SearchViewModel
import kotlin.math.roundToInt

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        searchViewModel =
            ViewModelProvider(requireParentFragment()).get(SearchViewModel::class.java)
        val historyAdapter: DataAdapter =
            DataAdapter(listOf<AdapterItem>(), object : OnHistoryItemListener {
                override fun onItemClicked(requestText: String) {
                    searchViewModel.searchFor(requestText)
                }

                override fun deleteButtonClicked(requestText: String) {
                    historyViewModel.deleteRequest(requestText)
                }
            })
        val historyRecyclerView: RecyclerView = view.findViewById(R.id.historyRecyclerView)
        historyRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        historyViewModel.getRequestsHistory().observe(viewLifecycleOwner, Observer {
            historyAdapter.notifyDataChanged(it)
        })
        historyRecyclerView.adapter = historyAdapter
        view.findViewById<LinearLayout>(R.id.clearDbButton).apply {
            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5.0f).apply {
                        val px = convertDpToPx(requireContext(), 4)
                        setMargins(px, px, px,px)
                }
            }
            setOnClickListener {
                historyViewModel.clearRequestHistory()
            }
        }
        return view
    }

    private fun convertDpToPx(context: Context, valueInDp: Int): Int{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            valueInDp.toFloat(),
            context.resources.displayMetrics
        ).roundToInt()
    }

}