package com.nju.sphm.Controller.TableActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.nju.sphm.Controller.TableActivity.ClassPicker.OnClassChangedListener;
import com.nju.sphm.Model.School.GetClass;

import java.util.Calendar;

public class ClassPickerDialog extends AlertDialog implements OnClickListener
{
    private ClassPicker mDateTimePicker;
    private Calendar mDate = Calendar.getInstance();
    private OnClassSetListener mOnDateTimeSetListener;

    private GetClass getClassLogic=new GetClass();
    private int gradeNum=getClassLogic.getGradeNum();
    private int mChoseGrade=gradeNum/2;
    private int mChoseClass=1;
    
	@SuppressWarnings("deprecation")
	public ClassPickerDialog(Context context, long date)
	{
		super(context);
		mDateTimePicker = new ClassPicker(context);
	    setView(mDateTimePicker);
	    mDateTimePicker.setOnClassChangedListener(new OnClassChangedListener() {
            @Override
            public void onClassChanged(ClassPicker view, int choseGrade, int choseClass) {
                mChoseGrade=choseGrade;
                mChoseClass=choseClass;
            }
        });
        setTitle("请选择班级");
	    setButton("确定", this);
        setButton2("取消", (OnClickListener)null);
	    mDate.setTimeInMillis(date);
	}
	
	public interface OnClassSetListener
    {
        void OnClassSet(AlertDialog dialog, int choseGrade, int choseClass);
    }

	public void setOnClassSetListener(OnClassSetListener callBack)
    {
        mOnDateTimeSetListener = callBack;
    }
	 
	public void onClick(DialogInterface arg0, int arg1)
    {
        if (mOnDateTimeSetListener != null) 
        {
            mOnDateTimeSetListener.OnClassSet(this, mChoseGrade,mChoseClass);
        }
    }
}
