package de.example.challenge.flickrapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.fragments.childFragments.HistoryFragment
import de.example.challenge.flickrapp.fragments.childFragments.SearchingFragment

class FlowFragment : Fragment(), IOnBackPressed {
    private lateinit var fragmentOnTheScreen: ChildFragment
    private val savedInstanceStateKey: String = "savedChildFragment"
    private val savedHistoryFragmentKey: String = "savedHistoryFragment"
    private val savedSearchingFragmentKey: String = "savedSearchFragment"
    private lateinit var historyFragment: HistoryFragment
    private lateinit var searchingFragment: SearchingFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_flow, container, false)

        if (savedInstanceState == null) {
            historyFragment = HistoryFragment()
            searchingFragment = SearchingFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.flowFrameLayout, searchingFragment)
                .commit()
            fragmentOnTheScreen = ChildFragment.SEARCHING_FRAGMENT
        } else {
            fragmentOnTheScreen =
                (savedInstanceState.getSerializable(savedInstanceStateKey) as ChildFragment?)!!
            searchingFragment = (childFragmentManager.getFragment(
                savedInstanceState,
                savedSearchingFragmentKey
            ) as SearchingFragment?)!!
            historyFragment = (childFragmentManager.getFragment(
                savedInstanceState,
                savedHistoryFragmentKey
            ) as HistoryFragment?) ?: HistoryFragment()
        }
        val searchButton: Button = view.findViewById(R.id.searchButton)
        val historyButton: Button = view.findViewById(R.id.historyButton)

        searchButton.setOnClickListener {
            if (fragmentOnTheScreen != ChildFragment.SEARCHING_FRAGMENT) {
                replaceSearchingFragment()
            }
        }

        historyButton.setOnClickListener {
            if (fragmentOnTheScreen != ChildFragment.HISTORY_FRAGMENT) {
                replaceHistoryFragment()
            }
        }
        return view
    }

    private fun replaceHistoryFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.flowFrameLayout, historyFragment)
            .addToBackStack(null)
            .commit()
        fragmentOnTheScreen = ChildFragment.HISTORY_FRAGMENT
    }

    private fun replaceSearchingFragment() {
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        childFragmentManager.beginTransaction()
            .replace(R.id.flowFrameLayout, searchingFragment)
            .commit()
        fragmentOnTheScreen = ChildFragment.SEARCHING_FRAGMENT
    }

    override fun onBackPressed(): Boolean {
        if (fragmentOnTheScreen == ChildFragment.HISTORY_FRAGMENT) {
            replaceSearchingFragment()
            return false
        }
        return fragmentOnTheScreen == ChildFragment.SEARCHING_FRAGMENT
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(savedInstanceStateKey, fragmentOnTheScreen)
        childFragmentManager.putFragment(outState, savedSearchingFragmentKey, searchingFragment)
        if (fragmentOnTheScreen == ChildFragment.HISTORY_FRAGMENT) {
            childFragmentManager.putFragment(outState, savedHistoryFragmentKey, historyFragment)

        }
    }

    internal enum class ChildFragment {
        SEARCHING_FRAGMENT, HISTORY_FRAGMENT
    }
}