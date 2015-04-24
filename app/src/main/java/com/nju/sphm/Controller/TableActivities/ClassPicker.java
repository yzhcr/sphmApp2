package com.nju.sphm.Controller.TableActivities;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.nju.sphm.Model.UIHelper.ChooseClassHelper;
import com.nju.sphm.R;

public class ClassPicker extends FrameLayout
{
	private final NumberPicker mGradeSpinner;
	private final NumberPicker mClassSpinner;

    private ChooseClassHelper chooseClassHelperLogic = ChooseClassHelper.getInstance();
    private int choseGrade= chooseClassHelperLogic.getChoseGrade();
    private int choseClass= chooseClassHelperLogic.getChoseClass();
    private int gradeNumMax= chooseClassHelperLogic.getGradeNumMax();
    private int gradeNumMin= chooseClassHelperLogic.getGradeNumMin();
    private int classNum= chooseClassHelperLogic.getClassNum(choseGrade);



    private OnClassChangedListener mOnClassChangedListener;
    
    public ClassPicker(Context context)
	{
    	super(context);
    	 
    	 inflate(context, R.layout.classdialog, this);

        mGradeSpinner=(NumberPicker)this.findViewById(R.id.chooseGrade);
        mClassSpinner=(NumberPicker)this.findViewById(R.id.chooseClass);

        mGradeSpinner.setMinValue(gradeNumMin);
        mGradeSpinner.setMaxValue(gradeNumMax);
        mGradeSpinner.setValue(choseGrade);

        mGradeSpinner.setOnValueChangedListener(mOnChooseGradeChangedListener);


        mClassSpinner.setMaxValue(classNum);
        mClassSpinner.setMinValue(1);
        mClassSpinner.setValue(choseClass);
        mClassSpinner.setOnValueChangedListener(mOnChooseClassChangedListener);

	}
    
    private OnValueChangeListener mOnChooseGradeChangedListener =new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
            choseGrade=mGradeSpinner.getValue();
			updateGradeControl();
            onClassChanged();
		}
	};
    
    private OnValueChangeListener mOnChooseClassChangedListener =new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
            choseClass=mClassSpinner.getValue();
            onClassChanged();
		}
	};
	

	
	private void updateGradeControl()
    {
        mGradeSpinner.invalidate();
        classNum= chooseClassHelperLogic.getClassNum(mGradeSpinner.getValue());
        mClassSpinner.setMaxValue(classNum);
        mClassSpinner.setValue(1);
        choseClass=1;
        mClassSpinner.invalidate();
    }
	
	  public interface OnClassChangedListener
	  {
	        void onClassChanged(ClassPicker view, int choseGrade, int choseClass);
	  }
	
	  public void setOnClassChangedListener(OnClassChangedListener callback)
	  {
	        mOnClassChangedListener = callback;
	   }
	  
	  private void onClassChanged()
	  {
	        if (mOnClassChangedListener != null)
	        {
	            mOnClassChangedListener.onClassChanged(this, choseGrade,choseClass);
	        }
	    }
}
