package com.wmc.dialog;

import com.wmc.note.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
  
public class MyDialog {  
  
    private AlertDialog dlg;  
    private ImageView ivIcon;  
    private TextView tvText;  
    private Button btnCancel,btnSure;  
  
    private Context context;  
    private int imgResId = 0;  
    private String text;  
    private DialogButtonListener listener;  
  
    public void show(Context context, String text, final DialogButtonListener listener) {  
        this.context = context;  
        this.text = text;  
        this.listener = listener;  
        createDialog();  
        setValue();  
    }  
  
    public void show(Context context, int imgResId, String text, final DialogButtonListener listener) {  
        this.context = context;  
        this.text = text;  
        this.listener = listener;  
        this.imgResId = imgResId;  
        createDialog();  
        setValue();  
    }  
  
    //����Dialog����ʼ���ؼ�  
    private void createDialog() {  
        dlg = new AlertDialog.Builder(context).create();  
        dlg.show();  
        Window window = dlg.getWindow();  
        window.setContentView(R.layout.dialog);  
        window.setGravity(Gravity.CENTER);//����  
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//����͸��  
        ivIcon = (ImageView) window.findViewById(R.id.ivIcon);  
        tvText = (TextView) window.findViewById(R.id.tvText);  
        btnCancel = (Button) window.findViewById(R.id.btnCancel);  
        btnSure = (Button) window.findViewById(R.id.btnSure);  
    }  
  
    //���ÿؼ�ֵ  
    private void setValue() {  
        if (imgResId != 0) {  
            ivIcon.setImageResource(imgResId);  
        } else {  
            ivIcon.setVisibility(View.GONE);  
        }  
        tvText.setText(text);  
        btnCancel.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                listener.cancel();  
                dlg.dismiss();  
            }  
        });  
        btnSure.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                listener.sure();  
                dlg.dismiss();  
            }  
        });  
    }  
}