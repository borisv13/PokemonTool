package com.boris.pokemontool;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.EnumMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OpponentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OpponentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpponentFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_TYPE_DARK = "isDark";
    private static final String ARG_HP_ACTIVE = "hpActive";
    private static final String ARG_ASLEEP = "isAsleep";
    private static final String ARG_CONFUSED = "isConfused";
    private static final String ARG_PARALYZED = "isParalyzed";
    private static final String ARG_BURNED = "isBurned";
    private static final String ARG_POISONED = "isPoisoned";
    private static final String ARG_HP_BENCHED1 = "hpBenched1";
    private static final String ARG_HP_BENCHED2 = "hpBenched2";
    private static final String ARG_HP_BENCHED3 = "hpBenched3";
    private static final String ARG_HP_BENCHED4 = "hpBenched4";
    private static final String ARG_HP_BENCHED5 = "hpBenched5";
    private static final String ARG_BENCH_VISIBLE = "isBenchVisible";
    private static final String ARG_CHK_BENCHED1 = "isCheckedBenched1";
    private static final String ARG_CHK_BENCHED2 = "isCheckedBenched2";
    private static final String ARG_CHK_BENCHED3 = "isCheckedBenched3";
    private static final String ARG_CHK_BENCHED4 = "isCheckedBenched4";
    private static final String ARG_CHK_BENCHED5 = "isCheckedBenched5";


    private Boolean mIsDark;
    private int mHpActive;
    private Boolean mIsAsleep;
    private Boolean mIsConfused;
    private Boolean mIsParalyzed;
    private Boolean mIsBurned;
    private Boolean mIsPoisoned;
    private int mHpBenched1;
    private int mHpBenched2;
    private int mHpBenched3;
    private int mHpBenched4;
    private int mHpBenched5;
    private Boolean mIsBenchVisible;
    private Boolean mIsCheckedBenched1;
    private Boolean mIsCheckedBenched2;
    private Boolean mIsCheckedBenched3;
    private Boolean mIsCheckedBenched4;
    private Boolean mIsCheckedBenched5;


    private OnFragmentInteractionListener mListener;


    private enum CONDITION {ASLEEP, CONFUSED, PARALYZED, BURNED, POISONED}
    private EnumMap<CONDITION, ToggleButton> conditions = new EnumMap<CONDITION, ToggleButton>(CONDITION.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isDark determines fragment appearance; if true == black upside-down fragment
     * @param hpActive active Pokemon health point counter, large numbers
     * @param isAsleep state of the Asleep special condition button
     *        ...
     * @param isPoisoned state of the Poisoned special condition button
     * @param hpBenched1 health point counter of the first benched Pokemon
     *        ...
     * @param hpBenched5 health point counter of the fifth benched Pokemon
     * @param isBenchVisible Bench panel visible/hidden
     *
     * @return A new instance of fragment OpponentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpponentFragment newInstance(Boolean isDark,
                                            int hpActive, Boolean isAsleep, Boolean isConfused, Boolean isParalyzed, Boolean isBurned, Boolean isPoisoned,
                                            int hpBenched1, int hpBenched2, int hpBenched3, int hpBenched4, int hpBenched5,
                                            Boolean isBenchVisible,
                                            Boolean isCheckedBenched1, Boolean isCheckedBenched2, Boolean isCheckedBenched3, Boolean isCheckedBenched4, Boolean isCheckedBenched5) {
        OpponentFragment fragment = new OpponentFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TYPE_DARK, isDark);
        args.putInt(ARG_HP_ACTIVE, hpActive);
        args.putBoolean(ARG_ASLEEP, isAsleep);
        args.putBoolean(ARG_CONFUSED, isConfused);
        args.putBoolean(ARG_PARALYZED, isParalyzed);
        args.putBoolean(ARG_BURNED, isBurned);
        args.putBoolean(ARG_POISONED, isPoisoned);
        args.putInt(ARG_HP_BENCHED1, hpBenched1);
        args.putInt(ARG_HP_BENCHED2, hpBenched2);
        args.putInt(ARG_HP_BENCHED3, hpBenched3);
        args.putInt(ARG_HP_BENCHED4, hpBenched4);
        args.putInt(ARG_HP_BENCHED5, hpBenched5);
        args.putBoolean(ARG_BENCH_VISIBLE, isBenchVisible);
        args.putBoolean(ARG_CHK_BENCHED1, isCheckedBenched1);
        args.putBoolean(ARG_CHK_BENCHED2, isCheckedBenched2);
        args.putBoolean(ARG_CHK_BENCHED3, isCheckedBenched3);
        args.putBoolean(ARG_CHK_BENCHED4, isCheckedBenched4);
        args.putBoolean(ARG_CHK_BENCHED5, isCheckedBenched5);
        fragment.setArguments(args);
        return fragment;
    }

    public OpponentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsDark = getArguments().getBoolean(ARG_TYPE_DARK);
            mHpActive = getArguments().getInt(ARG_HP_ACTIVE);
            mIsAsleep = getArguments().getBoolean(ARG_ASLEEP);
            mIsConfused = getArguments().getBoolean(ARG_CONFUSED);
            mIsParalyzed = getArguments().getBoolean(ARG_PARALYZED);
            mIsBurned = getArguments().getBoolean(ARG_BURNED);
            mIsPoisoned = getArguments().getBoolean(ARG_POISONED);
            mHpBenched1 = getArguments().getInt(ARG_HP_BENCHED1);
            mHpBenched2 = getArguments().getInt(ARG_HP_BENCHED2);
            mHpBenched3 = getArguments().getInt(ARG_HP_BENCHED3);
            mHpBenched4 = getArguments().getInt(ARG_HP_BENCHED4);
            mHpBenched5 = getArguments().getInt(ARG_HP_BENCHED5);
            mIsBenchVisible = getArguments().getBoolean(ARG_BENCH_VISIBLE);
            mIsCheckedBenched1 = getArguments().getBoolean(ARG_CHK_BENCHED1);
            mIsCheckedBenched2 = getArguments().getBoolean(ARG_CHK_BENCHED2);
            mIsCheckedBenched3 = getArguments().getBoolean(ARG_CHK_BENCHED3);
            mIsCheckedBenched4 = getArguments().getBoolean(ARG_CHK_BENCHED4);
            mIsCheckedBenched5 = getArguments().getBoolean(ARG_CHK_BENCHED5);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_opponent, container, false);

        conditions.put(CONDITION.ASLEEP, (ToggleButton) v.findViewById(R.id.buttonAsleep));
        conditions.put(CONDITION.CONFUSED, (ToggleButton)v.findViewById(R.id.buttonConfused));
        conditions.put(CONDITION.PARALYZED, (ToggleButton)v.findViewById(R.id.buttonParalyzed));
        conditions.put(CONDITION.BURNED, (ToggleButton)v.findViewById(R.id.buttonBurned));
        conditions.put(CONDITION.POISONED, (ToggleButton)v.findViewById(R.id.buttonPoisoned));

        //customize top opponent's fieldView appearance == dark condition buttons, white HP text
        if (mIsDark) {
            v.setBackgroundColor(Color.parseColor("#303030"));
            v.setRotation(180);

            for(EnumMap.Entry<CONDITION, ToggleButton> condition : conditions.entrySet())
                condition.getValue().setBackground(getActivity().getDrawable(R.drawable.disk_dark));

            ((TextView) v.findViewById(R.id.textHitPoints)).setTextColor(Color.WHITE);
            ((TextView) v.findViewById(R.id.buttonMinus)).setTextColor(Color.WHITE);
            ((TextView) v.findViewById(R.id.buttonPlus)).setTextColor(Color.WHITE);
            //appearance customized
        }

        for(EnumMap.Entry<CONDITION, ToggleButton> condition : conditions.entrySet())
            condition.getValue().setOnClickListener(this);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void onSpecialConditionRaised(int condition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAsleep:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(1);
                    }
                    conditions.get(CONDITION.CONFUSED).setChecked(false);
                    conditions.get(CONDITION.PARALYZED).setChecked(false);
                }
                break;

            case R.id.buttonConfused:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(2);
                    }
                    conditions.get(CONDITION.ASLEEP).setChecked(false);
                    conditions.get(CONDITION.PARALYZED).setChecked(false);
                }
                break;

            case R.id.buttonParalyzed:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(3);
                    }
                    conditions.get(CONDITION.ASLEEP).setChecked(false);
                    conditions.get(CONDITION.CONFUSED).setChecked(false);
                }
                break;

            case R.id.buttonBurned:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(4);
                    }
                }
                break;

            case R.id.buttonPoisoned:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(5);
                    }
                }
                break;

            default:
                break;
        }

    }

}
