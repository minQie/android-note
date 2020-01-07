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
	public View getView(final int position, View convertView, ViewGroup parent) {//���ﲻ����ȥ��position��ֵ,��������final��ȫû��Ӱ��
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
		//������Ϊ��View��ʼ��������ʾ
		Note theNote = notes.get(position);
		holder.time.setText(theNote.getTime());
		final String id = theNote.getId()+"";
		final String name = theNote.getName();
		final String content = theNote.getContent();
		holder.name.setText(name);
		holder.content.setText(content);
		//ʹ��listview��onItemClickListenerûһ������,�������������õ���¼�
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
				new MyDialog().show(noteFragment.getActivity(), R.drawable.indicator_input_error, "�Ƿ�Ҫɾ���ñ�ǩ?", new DialogButtonListener() {  
		            @Override  
		            public void sure() {
		            	//���ݿ���ɾ��
		            	NoteDBHelper helper = new NoteDBHelper(noteFragment.getActivity());
						SQLiteDatabase database = helper.getWritableDatabase();
						int row = database.delete("note", "id=?", new String[]{id});
						if(row == -1)
							Toast.makeText(noteFragment.getActivity(), "ɾ��ʧ��!", Toast.LENGTH_SHORT).show();
						else{
							//����ɾ���ɹ�,�������ɾ��
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
