package com.wmc.fragment;

import java.util.ArrayList;
import java.util.List;

import com.wmc.adapter.NoteAdapter;
import com.wmc.dbhelper.NoteDBHelper;
import com.wmc.domain.Note;
import com.wmc.note.R;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class NoteFragment extends Fragment {

	private NoteDBHelper helper;
	private List<Note> notes;
	private NoteAdapter adapter;
	private ListView listview;
	private Button addButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_node, null);
		helper = new NoteDBHelper(getActivity());
		listview = (ListView) view.findViewById(R.id.note_listview);
		refresh();
		//��
		addButton = (Button) view.findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.OpenAdd");
				intent.addCategory("android.intent.category.DEFAULT");
				startActivityForResult(intent, 1);
			}
		});
		//��
		return view;
	}
	//��
	private List<Note> prepareNodes(){
		List<Note> notes = new ArrayList<Note>();
		SQLiteDatabase database = helper.getReadableDatabase();
		String sql = "select * from note";
		//Cursor cursor = database.query("note", null, null, null, null, null, null);
		Cursor cursor = database.rawQuery(sql, null);
    	while(cursor.moveToNext()){
    		//getXXX:���ص�ǰָ����ָ���ж�Ӧ�кŵ�����,getColumnIndex����������Ӧ���к�
    		int id = cursor.getInt(cursor.getColumnIndex("id"));
    		String name = cursor.getString(cursor.getColumnIndex("name"));
    		String time = cursor.getString(cursor.getColumnIndex("time"));
    		String content = cursor.getString(cursor.getColumnIndex("content"));
    		notes.add(new Note(id, name, time, content));
    	}
    	cursor.close();
    	database.close();
		return notes;
	}
	//������
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null){
			String name = data.getStringExtra("name");
			String time = data.getStringExtra("time");
			String content = data.getStringExtra("content");
			SQLiteDatabase database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("time", time);
			values.put("content",content);
			//������requestCode��Ϊ�ĸ�Activity�������ݵı�ʶ,��ʼ�Լ��õ��Ƕ����һ��status��Ϊ��ʶ,��Ȼʹ��resultCode�����Ҳ��
			if(requestCode == 1){
				long row = database.insert("note",null, values);
				if(row == -1)
					Toast.makeText(NoteFragment.this.getActivity(), "����ʧ��!", Toast.LENGTH_SHORT).show();
				else
					refresh();//���ݿ���ӳɹ��Ÿ��½���
			}else if(requestCode == 2){
				String id = data.getStringExtra("id");
				int row = database.update("note", values, "id=?", new String[]{id});
				if(row == -1)
					Toast.makeText(NoteFragment.this.getActivity(), "����ʧ��!", Toast.LENGTH_SHORT).show();
				else
					refresh();//���ݿ���³ɹ��Ÿ��½���
			}
			database.close();
		}
	}
	private void refresh(){
		notes = prepareNodes();
		adapter = new NoteAdapter(this, notes);
		listview.setAdapter(adapter);
	}
}
