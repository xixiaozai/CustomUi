package com.minisea.customui.view;

import com.minisea.cookbook.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *
 * @author ZhangHaihai(drivedreams@163.com)
 *
 **/
public class CombinationlStatusView extends LinearLayout{
	public final static String TYPE_CheckBox = "checkbox";
	public final static String TYPE_ProgressBar = "progressbar";
	public final static String TYPE_CheckedTextView = "checkedtext";
	public final static String TYPE_TextView = "text";
	
	public CheckBox checkbox = null;
	public MovingProgressBar progressBar = null;
	public CheckedTextView checkedTextView = null;
	public TextView textView = null;
	
	public CombinationlStatusView(Context context) {
		super(context);
	}
	
	public CombinationlStatusView(Context context, AttributeSet attr) {
		super(context, attr);
		if(isInEditMode()) return;
		obtainViews(context);
        initViewStatus(context, attr);
        
	}
	
	private void obtainViews(Context context) {
		// ���벼��  
        LayoutInflater mInflater = LayoutInflater.from(context);  
        LinearLayout view = (LinearLayout) mInflater.inflate(R.layout.custom_view_backup_status, this, true);  
       
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        progressBar = (MovingProgressBar) view.findViewById(R.id.progress_bar);
        checkedTextView = (CheckedTextView) view.findViewById(R.id.checked_textview);
        textView = (TextView) view.findViewById(R.id.textview);
	}

	/**
	 * ��ʼ���ؼ�״̬
	 * @param context
	 * @param attr
	 */
	private void initViewStatus(Context context, AttributeSet attr ){
		TypedArray typeArray = context.obtainStyledAttributes(attr, R.styleable.combinationl_status_view);  
        //��ȡ��ʾ�Ŀؼ�����
        String type = typeArray.getString(R.styleable.combinationl_status_view_type);  
        //��ȡcheckbox״̬
        boolean checkState = typeArray.getBoolean(R.styleable.combinationl_status_view_check_state, true); 
        //��ȡ����
        int progress = typeArray.getInteger(R.styleable.combinationl_status_view_progress, 0);
        //��ȡcheckedTextView��ʾ������
        String checkedText = typeArray.getString(R.styleable.combinationl_status_view_checkedtext); 
        
        //��ȡTextView��ʾ������
        String text = typeArray.getString(R.styleable.combinationl_status_view_text); 
        
        //��������,���Բ����ã���Ҫ��Ϊ�����Ч��  
        typeArray.recycle();  
        
        show(type);
        
        setCheckStated(checkState);
        setProgress(progress);
        setCheckedText(checkedText);
        setCheckedText(text);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * ����Checkbox״̬
	 * @param state
	 */
	public void setCheckStated(boolean state){
		checkbox.setChecked(state);
	}
	
	/**
	 * ���ý���
	 * @param progress
	 */
	public void setProgress(int progress){
		progressBar.setProgress(progress);
	}
	
	/**
	 * ����checkedTextView ����
	 * @param text
	 */
	public void setCheckedText(String text){
		checkedTextView.setText(text);
	}
	
	/**
	 * ����TextView����
	 * @param text
	 */
	public void setText(String text){
		textView.setText(text);
	}
	
	/**
	 * ���ݸ�����������ʾָ���Ŀؼ�
	 * @param type
	 */
	public void show(String type) {
		//�����������пؼ�
		hideBesides(type);
		
		if(type.equals(TYPE_CheckBox)) 
			checkbox.setVisibility(VISIBLE);
		if(type.equals(TYPE_ProgressBar)) 
			progressBar.setVisibility(VISIBLE);
		if(type.equals(TYPE_CheckedTextView)) 
			checkedTextView.setVisibility(VISIBLE);
		if(type.equals(TYPE_TextView)) 
			textView.setVisibility(VISIBLE);
	}
	
	private void hideBesides(String type) {
		if(checkbox.getVisibility() == View.VISIBLE && type != TYPE_CheckBox) checkbox.setVisibility(GONE);
		if(progressBar.getVisibility() ==View.VISIBLE && type != TYPE_ProgressBar) progressBar.setVisibility(GONE);
		if(checkedTextView.getVisibility() ==View.VISIBLE && type != TYPE_CheckedTextView) checkedTextView.setVisibility(GONE);
		if(textView.getVisibility() ==View.VISIBLE && type != TYPE_TextView) textView.setVisibility(GONE);
	}
	
}
