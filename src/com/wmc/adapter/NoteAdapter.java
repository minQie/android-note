package com.wmc.adapter;

import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wmc.dbhelper.NoteDBHelper;
import com.wmc.dialog.DialogButtonListener;
import com.wmc.dialog.MyDialog;
import com.wmc.domain.Note;
import com.wmc.note.R;

public class NoteAdapter extends BaseAdapter {

	private static class ViewHolder{
		TextView name;
		TextView time;
		TextView content;
		ImageButton delete;
	}
	private List<Note> notes;
	private Fragment noteFragment;

	public NoteAdapter(Fragment noteFragment, List<Note> notes) {
		this.noteFragment = noteFragment;
		this.notes = notes;
	}

	@Override
	public int getCount() {
		return notes.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {//这里不可能去改position的值,所以设置final完全没有影响
		ViewHolder holder;
		if(convertView == null) {
			convertView = View.inflate(noteFragment.getActivity(), R.layout.note_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.delete = (ImageButton) convertView.findViewById(R.id.delete);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		//接下来为该View初始化界面显示
		Note theNote = notes.get(position);
		holder.time.setText(theNote.getTime());
		final String id = theNote.getId()+"";
		final String name = theNote.getName();
		final String content = theNote.getContent();
		holder.name.setText(name);
		holder.content.setText(content);
		//使用listview的onItemClickListener没一点卵用,所以在这里设置点击事件
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("content", content);
				intent.setAction("android.intent.action.OpenEdit");
				intent.addCategory("android.intent.category.DEFAULT");
				noteFragment.startActivityForResult(intent, 2);
			}
		});
		holder.delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				new MyDialog().show(noteFragment.getActivity(), R.drawable.indicator_input_error, "是否要删除该便签?", new DialogButtonListener() {  
		            @Override  
		            public void sure() {
		            	//数据库中删除
		            	NoteDBHelper helper = new NoteDBHelper(noteFragment.getActivity());
						SQLiteDatabase database = helper.getWritableDatabase();
						int row = database.delete("note", "id=?", new String[]{id});
						if(row == -1)
							Toast.makeText(noteFragment.getActivity(), "删除失败!", Toast.LENGTH_SHORT).show();
						else{
							//数据删除成功,界面才能删除
							notes.remove(position);
							notifyDataSetChanged();
						}
						database.close();
		            }  
		            @Override  
		            public void cancel(){}  
		        });
			}
		});
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
}
