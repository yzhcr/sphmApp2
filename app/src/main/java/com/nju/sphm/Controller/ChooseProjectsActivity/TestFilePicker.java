package com.nju.sphm.Controller.ChooseProjectsActivity;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Model.ChooseTestProject.ChooseTestFiles;
import com.nju.sphm.R;

import java.util.ArrayList;

public class TestFilePicker extends FrameLayout
{
	private final NumberPicker mTestFileSpinner;
    ArrayList<TestFileBean> testFileList=ChooseTestFiles.getInstance().getTestFileList();
    private int testFileNum=testFileList.size();
    //private int classNum=getClassLogic.getClassNum(gradeNum / 2);

    private int choseTestFile=ChooseTestFiles.getInstance().getChosenTestFile()+1;
    //private int choseClass=1;

    private OnTestFileChangedListener mOnTestFileChangedListener;
    
    public TestFilePicker(Context context)
	{
    	super(context);
    	 
    	 inflate(context, R.layout.testfiledialog, this);

        mTestFileSpinner =(NumberPicker)this.findViewById(R.id.chooseTestFile);
        String[] testFile=new String[testFileNum];
        for(int i=0;i<testFileNum;i++){
            testFile[i]=testFileList.get(i).getFileName();
        }
        mTestFileSpinner.setDisplayedValues(testFile);
        mTestFileSpinner.setMinValue(1);
        mTestFileSpinner.setMaxValue(testFileNum);
        mTestFileSpinner.setValue(choseTestFile);

        mTestFileSpinner.setOnValueChangedListener(mOnChooseTestFileChangedListener);

	}
    
    private OnValueChangeListener mOnChooseTestFileChangedListener =new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
            choseTestFile= mTestFileSpinner.getValue();
			updateTestFileControl();
            onTestFileChanged();
		}
	};

   /* private OnValueChangeListener mOnChooseClassChangedListener =new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
            onClassChanged();
		}
	};*/



	private void updateTestFileControl()
    {
        mTestFileSpinner.invalidate();
        //classNum=getClassLogic.getClassNum(mTestFileSpinner.getValue());
    }
	
	  public interface OnTestFileChangedListener
	  {
	        void onTestFileChanged(TestFilePicker view, int choseTestFile);
	  }
	
	  public void setOnTestFileChangedListener(OnTestFileChangedListener callback)
	  {
	        mOnTestFileChangedListener = callback;
	   }
	  
	  private void onTestFileChanged()
	  {
	        if (mOnTestFileChangedListener != null)
	        {
	            mOnTestFileChangedListener.onTestFileChanged(this, choseTestFile);
	        }
	    }
}
