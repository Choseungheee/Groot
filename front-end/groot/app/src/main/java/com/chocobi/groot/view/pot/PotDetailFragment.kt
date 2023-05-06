package com.chocobi.groot.view.pot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.chocobi.groot.MainActivity
import com.chocobi.groot.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PotDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PotDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = "PotDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_pot_detail, container, false)
        val mActivity = activity as MainActivity
        val potId = arguments?.getInt("potId")
        val potName = arguments?.getString("potName").toString()
        val potPlant = arguments?.getString("potPlant").toString()
        Log.d(TAG, potId.toString())
        val potNameText = rootView.findViewById<TextView>(R.id.potName)
        potNameText.text = potName

        val potPlantText = rootView.findViewById<TextView>(R.id.potPlant)
        potPlantText.text = potPlant


//        다이어리 버튼 클릭시
        val potPostDiaryBtn = rootView.findViewById<ImageButton>(R.id.potPostDiaryBtn)
        potPostDiaryBtn.setOnClickListener {
            if (potId is Int) {
                mActivity.setPotId(potId)
            }
            mActivity.changeFragment("pot_diary_create")
        }
        // Inflate the layout for this fragment
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val tab1 = PotDetailTab1Fragment()
        val tab2 = PotDetailTab2Fragment()
        val tab3 = PotDetailTab3Fragment()
        val tab4 = PotDetailTab4Fragment()
        val tab5 = PotDetailTab5Fragment()

        var tabBtn1 = view.findViewById<ImageButton>(R.id.tabBtn1)
        var tabBtn2 = view.findViewById<ImageButton>(R.id.tabBtn2)
        var tabBtn3 = view.findViewById<ImageButton>(R.id.tabBtn3)
        var tabBtn4 = view.findViewById<ImageButton>(R.id.tabBtn4)
        var tabBtn5 = view.findViewById<ImageButton>(R.id.tabBtn5)
        tabBtn1.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.tab_container, tab1).commit()
        }
        tabBtn2.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.tab_container, tab2).commit()
        }
        tabBtn3.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.tab_container, tab3).commit()
        }
        tabBtn4.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.tab_container, tab4).commit()
        }
        tabBtn5.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.tab_container, tab5).commit()
        }
    }

}