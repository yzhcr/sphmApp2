package com.nju.sphm.Controller.ChooseProjectsActivities;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.nju.sphm.Controller.ChooseProjectsActivities.TestFilePicker.OnTestFileChangedListener;

public class TestFilePickerDialog extends AlertDialog implements OnClickListener
{
    private TestFilePicker mTestFilePicker;
    //private Calendar mDate = Calendar.getInstance();
    private OnTestFileSetListener mOnTestFileSetListener;

    //ArrayList<TestFileBean> testFileList= ChooseTestFiles.getInstance().getTestFileList();
    //private int testFileNum=testFileList.size();
    private int mChoseTestFile=1;
    //private int mChoseClass=1;
    
	@SuppressWarnings("deprecation")
	public TestFilePickerDialog(Context context)
	{
		super(context);
        mTestFilePicker = new TestFilePicker(context);
	    setView(mTestFilePicker);
        mTestFilePicker.setOnTestFileChangedListener(new OnTestFileChangedListener() {
            @Override
            public void onTestFileChanged(TestFilePicker view, int choseTestFile) {
                mChoseTestFile = choseTestFile;
                //mChoseClass=choseClass;
            }
        });
        setTitle("请选择测试文件");
	    setButton("确定", this);
        setButton2("取消", (OnClickListener)null);
	    //mDate.setTimeInMillis(date);
	}
	
	public interface OnTestFileSetListener
    {
        void OnTestFileSet(AlertDialog dialog, int choseTestFile);
    }

	public void setOnTestFileSetListener(OnTestFileSetListener callBack)
    {
        mOnTestFileSetListener = callBack;
    }
	 
	public void onClick(DialogInterface arg0, int arg1)
    {
        if (mOnTestFileSetListener != null)
        {
            mOnTestFileSetListener.OnTestFileSet(this, mChoseTestFile);
        }
    }
}
