package com.wmc.activity;

import com.wmc.fragment.NoteFragment;
import com.wmc.fragment.OtherFragment;
import com.wmc.note.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainFragmentActivity extends FragmentActivity {

	private FragmentManager fm;//片段管理器
	private FragmentTransaction ft;
	private NoteFragment noteFragment;
	private OtherFragment otherFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fm = getSupportFragmentManager();
		noteFragment = new NoteFragment();
		otherFragment = new OtherFragment();

		ft = fm.beginTransaction();
    	ft.replace(R.id.content, noteFragment);
    	ft.commit();
	}
	//显示展示noteFragment
	public void showButtonClick(View view){
		ft = fm.beginTransaction();
    	ft.replace(R.id.content, noteFragment);
    	ft.commit();
    }
	//显示展示otherFragment
    public void otherButtonClick(View view){
    	ft = fm.beginTransaction();
    	ft.replace(R.id.content, otherFragment);
    	ft.commit();
    }
}
