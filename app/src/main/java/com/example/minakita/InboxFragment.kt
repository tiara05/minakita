package com.example.minakita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.minakita.inbox.BeritaFragment
import com.example.minakita.inbox.NotifikasiFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FinanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InboxFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inbox, container, false)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val vp =  view.findViewById<ViewPager2>(R.id.vp)
        vp.adapter = activity?.let { ViewPagerAdapter(it) }

        TabLayoutMediator(tabs, vp,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Notifikasi"
                    1 -> tab.text = "Berita"
                    else -> {
                    }
                }
            }).attach()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FinanceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InboxFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class ViewPagerAdapter internal constructor(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return BeritaFragment()
                1 -> return NotifikasiFragment()
            }
            return NotifikasiFragment()
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}