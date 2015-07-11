package com.boris.pokemontool;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private int mTextColor;


    private OnFragmentInteractionListener mListener;

    private TextView activeHP;
    private EnumMap<CONDITION, ToggleButton> conditions = new EnumMap<CONDITION, ToggleButton>(CONDITION.class);
    private View benchContainer;
    private View conditionsContainer;
    private View mainMinusButton;
    private View mainPlusButton;
    private View resetButton;

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

            mTextColor = (mIsDark) ? Color.WHITE : Color.BLACK;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_opponent, container, false);

        activeHP = (TextView) v.findViewById(R.id.textHitPoints);
        activeHP.setText(String.valueOf(mHpActive));
        mainMinusButton = v.findViewById(R.id.buttonMinus);
        mainPlusButton = v.findViewById(R.id.buttonPlus);
        resetButton = v.findViewById(R.id.buttonReset);

        conditionsContainer = v.findViewById(R.id.conditionButtonsLayout);

        conditions.put(CONDITION.ASLEEP, (ToggleButton) conditionsContainer.findViewById(R.id.buttonAsleep));
        conditions.put(CONDITION.CONFUSED, (ToggleButton) conditionsContainer.findViewById(R.id.buttonConfused));
        conditions.put(CONDITION.PARALYZED, (ToggleButton) conditionsContainer.findViewById(R.id.buttonParalyzed));
        conditions.put(CONDITION.BURNED, (ToggleButton) conditionsContainer.findViewById(R.id.buttonBurned));
        conditions.put(CONDITION.POISONED, (ToggleButton) conditionsContainer.findViewById(R.id.buttonPoisoned));

        conditions.get(CONDITION.ASLEEP).setChecked(mIsAsleep);
        conditions.get(CONDITION.CONFUSED).setChecked(mIsConfused);
        conditions.get(CONDITION.PARALYZED).setChecked(mIsParalyzed);
        conditions.get(CONDITION.BURNED).setChecked(mIsBurned);
        conditions.get(CONDITION.POISONED).setChecked(mIsPoisoned);


        benchContainer = v.findViewById(R.id.benchLayout);
        setBenchVisibility(mIsBenchVisible);



        //customize top opponent's fieldView appearance == dark condition buttons, white HP text
        if (mIsDark) {
            v.setBackgroundColor(Color.parseColor("#303030"));
            v.setRotation(180);

            for(EnumMap.Entry<CONDITION, ToggleButton> condition : conditions.entrySet())
                condition.getValue().setBackground(getActivity().getDrawable(R.drawable.disk_dark));

            ((TextView) v.findViewById(R.id.textHitPoints)).setTextColor(mTextColor);
            ((TextView) v.findViewById(R.id.buttonMinus)).setTextColor(mTextColor);
            ((TextView) v.findViewById(R.id.buttonPlus)).setTextColor(mTextColor);
            //appearance customized
        }

        setChildrenOnclickListener(v);

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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        //TODO: Save fragment state; don't forget about the Activity
        // http://stackoverflow.com/questions/15608709/using-onsaveinstancestate-with-fragments-in-backstack
        // +pg.32
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(ARG_TYPE_DARK, mIsDark);
        savedInstanceState.putBoolean(ARG_ASLEEP, conditions.get(CONDITION.ASLEEP).isChecked());
        savedInstanceState.putBoolean(ARG_CONFUSED, conditions.get(CONDITION.CONFUSED).isChecked());
        savedInstanceState.putBoolean(ARG_PARALYZED, conditions.get(CONDITION.PARALYZED).isChecked());
        savedInstanceState.putBoolean(ARG_BURNED, conditions.get(CONDITION.BURNED).isChecked());
        savedInstanceState.putBoolean(ARG_POISONED, conditions.get(CONDITION.POISONED).isChecked());
        //TODO: continue......

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
        public void onSpecialConditionRaised(CONDITION condition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPlus:
                mHpActive = incrementHp(mHpActive, 10, activeHP, mTextColor);
                break;

            case R.id.buttonMinus:
                mHpActive = incrementHp(mHpActive, -10, activeHP, mTextColor);
                break;

            case R.id.buttonReset:
                mHpActive = 30;
                activeHP.setText(String.valueOf(mHpActive));
                activeHP.setTextColor(mTextColor);

                mIsAsleep = mIsConfused = mIsParalyzed = mIsBurned = mIsPoisoned = false;
                for(EnumMap.Entry<CONDITION, ToggleButton> condition : conditions.entrySet())
                    condition.getValue().setChecked(false);
                break;

            case R.id.buttonBench:
                mIsBenchVisible = setBenchVisibility(!mIsBenchVisible);

                break;

            case R.id.buttonAsleep:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.ASLEEP);
                    }
                    conditions.get(CONDITION.CONFUSED).setChecked(false);
                    conditions.get(CONDITION.PARALYZED).setChecked(false);
                }
                break;

            case R.id.buttonConfused:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.CONFUSED);
                    }
                    conditions.get(CONDITION.ASLEEP).setChecked(false);
                    conditions.get(CONDITION.PARALYZED).setChecked(false);
                }
                break;

            case R.id.buttonParalyzed:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.PARALYZED);
                    }
                    conditions.get(CONDITION.ASLEEP).setChecked(false);
                    conditions.get(CONDITION.CONFUSED).setChecked(false);
                }
                break;

            case R.id.buttonBurned:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.BURNED);
                    }
                }
                break;

            case R.id.buttonPoisoned:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.POISONED);
                    }
                }
                break;

            default:
                break;
        }

    }

    private void setChildrenOnclickListener(View parentView){
        if (parentView instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) parentView).getChildCount(); i++) {
                View childView = ((ViewGroup) parentView).getChildAt(i);
                if (childView.isClickable())
                    childView.setOnClickListener(this);
                if (childView instanceof ViewGroup)
                    setChildrenOnclickListener(childView);
            }
        }
    }

    private int incrementHp(int hp, int i, TextView tv, int defaultTextColor){
        int c = defaultTextColor;
        int x = hp + i;

        if (x <= 0) {
            x = 0;
            c = Color.RED;
        } else if (x > 990) {
            x = 990;
            c = Color.GREEN;
        }
        tv.setText(String.valueOf(x));
        tv.setTextColor(c);
        return x;
    }

    private Boolean setBenchVisibility(Boolean isVisible){

        if (isVisible){
            benchContainer.setVisibility(View.VISIBLE);
            conditionsContainer.setVisibility(View.INVISIBLE); //disable clicks on out-of-focus sp. conditions buttons
            mainMinusButton.setEnabled(false);
            mainPlusButton.setEnabled(false);
            resetButton.setEnabled(false);
        } else {
            benchContainer.setVisibility(View.GONE);
            conditionsContainer.setVisibility(View.VISIBLE);
            mainMinusButton.setEnabled(true);
            mainPlusButton.setEnabled(true);
            resetButton.setEnabled(true);
        }

        return (benchContainer.getVisibility() == View.VISIBLE);
    }


}
