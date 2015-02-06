package com.nju.sphm.Controller.TableActivity;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.nju.sphm.Model.School.GetClass;
import com.nju.sphm.R;

public class ClassPicker extends FrameLayout
{
	private final NumberPicker mGradeSpinner;
	private final NumberPicker mClassSpinner;

    private GetClass getClassLogic=new GetClass();
    private int gradeNum=getClassLogic.getGradeNum();
    private int classNum=getClassLogic.getClassNum(gradeNum / 2);

    private int choseGrade=gradeNum / 2;
    private int choseClass=1;

    private OnClassChangedListener mOnClassChangedListener;
    
    public ClassPicker(Context context)
	{
    	super(context);
    	 
    	 inflate(context, R.layout.classdialog, this);

        mGradeSpinner=(NumberPicker)this.findViewById(R.id.chooseGrade);
        mClassSpinner=(NumberPicker)this.findViewById(R.id.chooseClass);

        mGradeSpinner.setMinValue(1);
        mGradeSpinner.setMaxValue(gradeNum);
        mGradeSpinner.setValue(gradeNum / 2);

        mGradeSpinner.setOnValueChangedListener(mOnChooseGradeChangedListener);


        mClassSpinner.setMaxValue(classNum);
        mClassSpinner.setMinValue(1);
        mClassSpinner.setValue(1);
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
        classNum=getClassLogic.getClassNum(mGradeSpinner.getValue());
        mClassSpinner.setMaxValue(classNum);
        mClassSpinner.setValue(1);
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
