package com.wmc.activity;

import com.wmc.note.R;
import com.wmc.utils.TimeUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends FragmentActivity {

	private TextView nameTextView;
	private TextView contentTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		nameTextView = (TextView) findViewById(R.id.name);
		contentTextView = (TextView) findViewById(R.id.content);
	}
	
	public void addSureButtonClick(View view){
		String name = nameTextView.getText().toString().trim();
		String content = contentTextView.getText().toString().trim();
		if(name.equals(""))
			Toast.makeText(this, "标签名不能为空!", Toast.LENGTH_SHORT).show();
		else if(content.equals(""))
			Toast.makeText(this, "内容不能为空!", Toast.LENGTH_SHORT).show();
		else{
			Intent data = new Intent();
			data.putExtra("name", name);
			data.putExtra("time", TimeUtil.getCurrentTime());
			data.putExtra("content", content);
			setResult(RESULT_OK, data);
			finish();
		}
	}
	public void addCancelButtonClick(View view){
		finish();
	}

}
