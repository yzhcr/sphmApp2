package com.nju.sphm.Controller.TableActivities;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.nju.sphm.Controller.TableActivities.ClassPicker.OnClassChangedListener;
import com.nju.sphm.Model.UIHelper.GetClass;

public class ClassPickerDialog extends AlertDialog implements OnClickListener
{
    private ClassPicker mClassPicker;
    //private Calendar mDate = Calendar.getInstance();
    private OnClassSetListener mOnClassSetListener;

    private GetClass getClassLogic=GetClass.getInstance();
    //private int gradeNum=getClassLogic.getGradeNum();
    private int mChoseGrade=getClassLogic.getChoseGrade();
    private int mChoseClass=getClassLogic.getChoseClass();
    
	@SuppressWarnings("deprecation")
	public ClassPickerDialog(Context context)
	{
		super(context);
        mClassPicker = new ClassPicker(context);
	    setView(mClassPicker);
        mClassPicker.setOnClassChangedListener(new OnClassChangedListener() {
            @Override
            public void onClassChanged(ClassPicker view, int choseGrade, int choseClass) {
                mChoseGrade=choseGrade;
                mChoseClass=choseClass;
            }
        });
        setTitle("请选择班级");
	    setButton("确定", this);
        setButton2("取消", (OnClickListener)null);
	    //mDate.setTimeInMillis(date);
	}
	
	public interface OnClassSetListener
    {
        void OnClassSet(AlertDialog dialog, int choseGrade, int choseClass);
    }

	public void setOnClassSetListener(OnClassSetListener callBack)
    {
        mOnClassSetListener = callBack;
    }
	 
	public void onClick(DialogInterface arg0, int arg1)
    {
        if (mOnClassSetListener != null)
        {
            mOnClassSetListener.OnClassSet(this, mChoseGrade,mChoseClass);
        }
    }
}
