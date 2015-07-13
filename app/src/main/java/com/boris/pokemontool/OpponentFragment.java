package com.boris.pokemontool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
public class OpponentFragment extends Fragment
        implements View.OnClickListener, View.OnLongClickListener {

    private OnFragmentInteractionListener mListener;

    private static final String ARG_TYPE_DARK = "isDark";
    private static final String ARG_DC_ACTIVE = "damageCounterActive";
    private static final String ARG_ASLEEP = "isAsleep";
    private static final String ARG_CONFUSED = "isConfused";
    private static final String ARG_PARALYZED = "isParalyzed";
    private static final String ARG_BURNED = "isBurned";
    private static final String ARG_POISONED = "isPoisoned";
    private static final String ARG_DC_BENCH = "damageCounterBench";
    private static final String ARG_BENCH_VISIBLE = "isBenchVisible";
    private static final String ARG_CHK_BENCH = "isCheckedBench";

    private boolean mIsDark;
    private int mDamageCounterActive;
    private boolean mIsAsleep;
    private boolean mIsConfused;
    private boolean mIsParalyzed;
    private boolean mIsBurned;
    private boolean mIsPoisoned;
    private int mDamageCounterBench[] = new int[5];
    private boolean mIsBenchVisible;
    private boolean mIsCheckedBench[] = new boolean[5];

    private int mTextColor;

    //identify UI widgets for future reference
    private TextView widgetDcActive;
    private View widgetActiveMinus;
    private View widgetActivePlus;
    private View widgetReset;
    private View widgetBench;
    private View containerConditions;
    private EnumMap<CONDITION, ToggleButton> widgetsConditions = new EnumMap<CONDITION, ToggleButton>(CONDITION.class);
    private View containerBench;
    private View widgetBenchPlus;
    private View widgetBenchMinus;
    private ImageButton widgetBenchAction;
    private CheckBox widgetsDcBench[] = new CheckBox[5];



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isDark determines fragment appearance; if true == black upside-down fragment
     * @param damageCounterActive active Pokemon health point counter, large numbers
     * @param isAsleep state of the Asleep special condition button
     * @param isConfused state of the Confused special condition button
     * @param isParalyzed state of the Paralyzed special condition button
     * @param isBurned state of the Burned special condition button
     * @param isPoisoned state of the Poisoned special condition button
     * @param damageCounterBench benched Pokemon damage point counter array
     * @param isBenchVisible bench panel visible/hidden
     * @param isCheckedBench selection state of bench spots (check boxes)
     *
     * @return A new instance of fragment OpponentFragment.
     */
    public static OpponentFragment newInstance(Boolean isDark,
                                            int damageCounterActive, Boolean isAsleep, Boolean isConfused, Boolean isParalyzed, Boolean isBurned, Boolean isPoisoned,
                                            int[] damageCounterBench, Boolean isBenchVisible, boolean[] isCheckedBench) {
        OpponentFragment fragment = new OpponentFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TYPE_DARK, isDark);
        args.putInt(ARG_DC_ACTIVE, damageCounterActive);
        args.putBoolean(ARG_ASLEEP, isAsleep);
        args.putBoolean(ARG_CONFUSED, isConfused);
        args.putBoolean(ARG_PARALYZED, isParalyzed);
        args.putBoolean(ARG_BURNED, isBurned);
        args.putBoolean(ARG_POISONED, isPoisoned);
        args.putIntArray(ARG_DC_BENCH, damageCounterBench);
        args.putBoolean(ARG_BENCH_VISIBLE, isBenchVisible);
        args.putBooleanArray(ARG_CHK_BENCH, isCheckedBench);
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
            mDamageCounterActive = getArguments().getInt(ARG_DC_ACTIVE);
            mIsAsleep = getArguments().getBoolean(ARG_ASLEEP);
            mIsConfused = getArguments().getBoolean(ARG_CONFUSED);
            mIsParalyzed = getArguments().getBoolean(ARG_PARALYZED);
            mIsBurned = getArguments().getBoolean(ARG_BURNED);
            mIsPoisoned = getArguments().getBoolean(ARG_POISONED);
            mDamageCounterBench = getArguments().getIntArray(ARG_DC_BENCH);
            mIsBenchVisible = getArguments().getBoolean(ARG_BENCH_VISIBLE);
            mIsCheckedBench = getArguments().getBooleanArray(ARG_CHK_BENCH);
        }
        if (savedInstanceState != null){
            mIsDark = savedInstanceState.getBoolean(ARG_TYPE_DARK);
            mDamageCounterActive = savedInstanceState.getInt(ARG_DC_ACTIVE);
            mIsAsleep = savedInstanceState.getBoolean(ARG_ASLEEP);
            mIsConfused = savedInstanceState.getBoolean(ARG_CONFUSED);
            mIsParalyzed = savedInstanceState.getBoolean(ARG_PARALYZED);
            mIsBurned = savedInstanceState.getBoolean(ARG_BURNED);
            mIsPoisoned = savedInstanceState.getBoolean(ARG_POISONED);
            mDamageCounterBench = savedInstanceState.getIntArray(ARG_DC_BENCH);
            mIsBenchVisible = savedInstanceState.getBoolean(ARG_BENCH_VISIBLE);
            mIsCheckedBench = savedInstanceState.getBooleanArray(ARG_CHK_BENCH);
        }
        mTextColor = (mIsDark) ? Color.WHITE : Color.BLACK;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_opponent, container, false);

        widgetDcActive = (TextView) v.findViewById(R.id.textHitPoints);
        widgetActiveMinus = v.findViewById(R.id.buttonMinus);
        widgetActivePlus = v.findViewById(R.id.buttonPlus);
        widgetReset = v.findViewById(R.id.buttonReset);
        widgetBench = v.findViewById(R.id.buttonBench);
        containerConditions = v.findViewById(R.id.conditionButtonsLayout);
        widgetsConditions.put(CONDITION.ASLEEP, (ToggleButton) containerConditions.findViewById(R.id.buttonAsleep));
        widgetsConditions.put(CONDITION.CONFUSED, (ToggleButton) containerConditions.findViewById(R.id.buttonConfused));
        widgetsConditions.put(CONDITION.PARALYZED, (ToggleButton) containerConditions.findViewById(R.id.buttonParalyzed));
        widgetsConditions.put(CONDITION.BURNED, (ToggleButton) containerConditions.findViewById(R.id.buttonBurned));
        widgetsConditions.put(CONDITION.POISONED, (ToggleButton) containerConditions.findViewById(R.id.buttonPoisoned));
        containerBench = v.findViewById(R.id.benchLayout);
        widgetsDcBench[0] = (CheckBox) containerBench.findViewById(R.id.benched1);
        widgetsDcBench[1] = (CheckBox) containerBench.findViewById(R.id.benched2);
        widgetsDcBench[2] = (CheckBox) containerBench.findViewById(R.id.benched3);
        widgetsDcBench[3] = (CheckBox) containerBench.findViewById(R.id.benched4);
        widgetsDcBench[4] = (CheckBox) containerBench.findViewById(R.id.benched5);
        widgetBenchMinus = (Button) containerBench.findViewById(R.id.buttonBenchMinus);
        widgetBenchPlus = (Button) containerBench.findViewById(R.id.buttonBenchPlus);
        widgetBenchAction = (ImageButton) containerBench.findViewById(R.id.buttonBenchAction);

        //customize top opponent's fieldView appearance == dark condition buttons, white damage counter text
        if (mIsDark) {
            v.setBackgroundColor(Color.parseColor("#303030"));
            v.setRotation(180);

            for(EnumMap.Entry<CONDITION, ToggleButton> condition : widgetsConditions.entrySet())
                condition.getValue().setBackground(getActivity().getDrawable(R.drawable.disk_dark));

            ((TextView) v.findViewById(R.id.textHitPoints)).setTextColor(mTextColor);
            ((TextView) v.findViewById(R.id.buttonMinus)).setTextColor(mTextColor);
            ((TextView) v.findViewById(R.id.buttonPlus)).setTextColor(mTextColor);
            //appearance customized
        }

        // restore widget state
        //   some of this is redundant because android will preserve state of elements editable by user
        //   figuring out which properties are editable is pain
        widgetDcActive.setText(String.valueOf(mDamageCounterActive));
        widgetsConditions.get(CONDITION.ASLEEP).setChecked(mIsAsleep);
        widgetsConditions.get(CONDITION.CONFUSED).setChecked(mIsConfused);
        widgetsConditions.get(CONDITION.PARALYZED).setChecked(mIsParalyzed);
        widgetsConditions.get(CONDITION.BURNED).setChecked(mIsBurned);
        widgetsConditions.get(CONDITION.POISONED).setChecked(mIsPoisoned);
        for (int i = 0; i < 5; i++) {
            widgetsDcBench[i].setText(String.valueOf(mDamageCounterBench[i]));
            widgetsDcBench[i].setChecked(mIsCheckedBench[i]);
        }
        setBenchVisibility(mIsBenchVisible);
        setBenchActionButtonState();


/*  This may be an eventual enhancement -- change button orientation on screen orientation change

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            ViewGroup.LayoutParams params = widgetActivePlus.getLayoutParams();
            int w = params.width;
            params.width = params.height;
            params.height = w;
            widgetActivePlus.setLayoutParams(params);
        }
*/


        setChildrenOnclickListener(v);
        widgetActiveMinus.setOnLongClickListener(this);
        widgetActivePlus.setOnLongClickListener(this);
        widgetBenchMinus.setOnLongClickListener(this);
        widgetBenchPlus.setOnLongClickListener(this);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(ARG_TYPE_DARK, mIsDark);
        savedInstanceState.putInt(ARG_DC_ACTIVE, mDamageCounterActive);
        savedInstanceState.putBoolean(ARG_ASLEEP, mIsAsleep);
        savedInstanceState.putBoolean(ARG_CONFUSED, mIsConfused);
        savedInstanceState.putBoolean(ARG_PARALYZED, mIsParalyzed);
        savedInstanceState.putBoolean(ARG_BURNED, mIsBurned);
        savedInstanceState.putBoolean(ARG_POISONED, mIsPoisoned);
        savedInstanceState.putIntArray(ARG_DC_BENCH, mDamageCounterBench);
        savedInstanceState.putBoolean(ARG_BENCH_VISIBLE, mIsBenchVisible);
        savedInstanceState.putBooleanArray(ARG_CHK_BENCH, mIsCheckedBench);
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
//        public void onFragmentInteraction(Uri uri);
        public void onSpecialConditionRaised(CONDITION condition);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlus:
                mDamageCounterActive = incrementDC(mDamageCounterActive, 50, widgetDcActive, mTextColor);
                break;

            case R.id.buttonMinus:
                mDamageCounterActive = incrementDC(mDamageCounterActive, -50, widgetDcActive, mTextColor);
                break;

            case R.id.buttonBenchPlus:
                for (int i = 0; i < 5; i++)
                    if (widgetsDcBench[i].isChecked())
                        mDamageCounterBench[i] = incrementDC(mDamageCounterBench[i], 50, widgetsDcBench[i], Color.BLACK);
                setBenchActionButtonState();
                break;

            case R.id.buttonBenchMinus:
                for (int i = 0; i < 5; i++)
                    if (widgetsDcBench[i].isChecked())
                        mDamageCounterBench[i] = incrementDC(mDamageCounterBench[i], -50, widgetsDcBench[i], Color.BLACK);
                setBenchActionButtonState();
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPlus:
                mDamageCounterActive = incrementDC(mDamageCounterActive, 10, widgetDcActive, mTextColor);
                break;

            case R.id.buttonMinus:
                mDamageCounterActive = incrementDC(mDamageCounterActive, -10, widgetDcActive, mTextColor);
                break;

            case R.id.buttonReset:
                mDamageCounterActive = 0;
                widgetDcActive.setText(String.valueOf(mDamageCounterActive));
                widgetDcActive.setTextColor(Color.GREEN);

                clearConditions();
                break;

            case R.id.buttonBench:
                setBenchActionButtonState();
                mIsBenchVisible = setBenchVisibility(!mIsBenchVisible);
                if (!mIsBenchVisible){
                    setAllBenchIsChecked(false);
                }
                break;

            case R.id.buttonBenchPlus:
                for (int i = 0; i < 5; i++)
                    if (widgetsDcBench[i].isChecked())
                        mDamageCounterBench[i] = incrementDC(mDamageCounterBench[i], 10, widgetsDcBench[i], Color.BLACK);
                setBenchActionButtonState();
                break;

            case R.id.buttonBenchMinus:
                for (int i = 0; i < 5; i++)
                    if (widgetsDcBench[i].isChecked())
                        mDamageCounterBench[i] = incrementDC(mDamageCounterBench[i], -10, widgetsDcBench[i], Color.BLACK);
                setBenchActionButtonState();
                break;

            case R.id.buttonBenchAction:
                switch (widgetBenchAction.getDrawable().getLevel()){
                    case 0: // select / deselect all
                        boolean newState = !mIsCheckedBench[0]; // all 5 will be in the same state
                        setAllBenchIsChecked(newState);
                        break;
                    case 1: //swap
                        //find the selected spot on the bench
                        int i = 0;
                        while (!widgetsDcBench[i].isChecked()) i++;
                        //swap active and selected bench damage counters
                        int tmp = mDamageCounterActive;
                        mDamageCounterActive = incrementDC(0, mDamageCounterBench[i], widgetDcActive, mTextColor);
                        mDamageCounterBench[i] = incrementDC(0, tmp, widgetsDcBench[i], Color.BLACK);
                        // clear any special conditions
                        clearConditions();
                        break;
                }
                break;

            case R.id.benched1:
                setBenchIsChecked(0);
                setBenchActionButtonState();
                break;
            case R.id.benched2:
                setBenchIsChecked(1);
                setBenchActionButtonState();
                break;
            case R.id.benched3:
                setBenchIsChecked(2);
                setBenchActionButtonState();
                break;
            case R.id.benched4:
                setBenchIsChecked(3);
                setBenchActionButtonState();
                break;
            case R.id.benched5:
                setBenchIsChecked(4);
                setBenchActionButtonState();
                break;

            case R.id.buttonAsleep:
                if (((ToggleButton) v).isChecked()) {
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.ASLEEP);
                    }
                    widgetsConditions.get(CONDITION.CONFUSED).setChecked(false);
                    widgetsConditions.get(CONDITION.PARALYZED).setChecked(false);

                    mIsAsleep = true;
                    mIsConfused = mIsParalyzed = false;
                } else
                    mIsAsleep = false;
                break;

            case R.id.buttonConfused:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.CONFUSED);
                    }
                    widgetsConditions.get(CONDITION.ASLEEP).setChecked(false);
                    widgetsConditions.get(CONDITION.PARALYZED).setChecked(false);

                    mIsConfused = true;
                    mIsAsleep = mIsParalyzed = false;
                } else
                    mIsConfused = false;
                break;

            case R.id.buttonParalyzed:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.PARALYZED);
                    }
                    widgetsConditions.get(CONDITION.ASLEEP).setChecked(false);
                    widgetsConditions.get(CONDITION.CONFUSED).setChecked(false);

                    mIsParalyzed = true;
                    mIsAsleep = mIsConfused = false;
                } else
                    mIsParalyzed = false;
                break;

            case R.id.buttonBurned:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.BURNED);
                    }
                    mIsBurned = true;
                } else
                    mIsBurned = false;
                break;

            case R.id.buttonPoisoned:
                if (((ToggleButton) v).isChecked()){
                    if (mListener != null) {
                        mListener.onSpecialConditionRaised(CONDITION.POISONED);
                    }
                    mIsPoisoned = true;
                } else
                    mIsPoisoned = false;
                break;

            default:
                break;
        }

    }

    private void clearConditions() {
        mIsAsleep = mIsConfused = mIsParalyzed = mIsBurned = mIsPoisoned = false;
        for(EnumMap.Entry<CONDITION, ToggleButton> condition : widgetsConditions.entrySet())
            condition.getValue().setChecked(false);
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

    private int incrementDC(int damageCounter, int i, TextView tv, int defaultTextColor){
        int color = defaultTextColor;
        int x = damageCounter + i;

        if (x <= 0) {
            x = 0;
            if (damageCounter == x)
                color = Color.GREEN;
        } else if (x > 990) {
            x = 990;
            if (damageCounter == x)
                color = Color.RED;
        }
        tv.setText(String.valueOf(x));
        tv.setTextColor(color);
        return x;
    }

    private Boolean setBenchVisibility(boolean isVisible){

        if (isVisible){
            containerBench.setVisibility(View.VISIBLE);
            containerConditions.setVisibility(View.INVISIBLE); //disable clicks on out-of-focus sp. conditions buttons
            widgetActiveMinus.setEnabled(false);
            widgetActivePlus.setEnabled(false);
            widgetReset.setEnabled(false);
            widgetBench.setElevation(6);
        } else {
            containerBench.setVisibility(View.GONE);
            containerConditions.setVisibility(View.VISIBLE);
            widgetActiveMinus.setEnabled(true);
            widgetActivePlus.setEnabled(true);
            widgetReset.setEnabled(true);
            widgetBench.setElevation(0);
        }

        return (containerBench.getVisibility() == View.VISIBLE);
    }


    private void setBenchActionButtonState(){
        /*
            Button levels:
            0 -- disk == select all
            1 -- double-headed arrow

        */
        int countChecked = 0;

        for (int i=0; i < 5; i++)
            if (widgetsDcBench[i].isChecked())
                countChecked++;

        if (countChecked == 1)
            widgetBenchAction.getDrawable().setLevel(1);
        else
            widgetBenchAction.getDrawable().setLevel(0);
    }

    private void setBenchIsChecked(int position){
        for (int i=0; i < 5; i++) {
            if (i == position) {
                widgetsDcBench[i].setChecked(true);
                mIsCheckedBench[i]=true;
            } else {
                widgetsDcBench[i].setChecked(false);
                mIsCheckedBench[i]=false;
            }
        }
    }

    private void setAllBenchIsChecked(boolean state) {
        for (int i = 0; i < 5; i++) {
            widgetsDcBench[i].setChecked(state);
            mIsCheckedBench[i] = state;
        }
    }

}
