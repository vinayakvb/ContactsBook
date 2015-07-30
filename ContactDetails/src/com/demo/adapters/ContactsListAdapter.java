package com.demo.adapters;

import java.util.ArrayList;

import com.demo.R;
import com.demo.models.ContactsModel;
import com.demo.views.CDetailsWebView;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author vinayak bevinakatti
 *
 */
public class ContactsListAdapter extends BaseAdapter {

	private static final String TAG = ContactsListAdapter.class.getName();
	private Activity activity;
	private ArrayList<ContactsModel> items;
	
	public ContactsListAdapter(Activity activity,
			ArrayList<ContactsModel> items) {
	    Log.i(TAG, TAG);
		this.activity = activity;
		this.items = items;
	}


	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.contacts_listrow, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.nameTV);
			holder.headingLL = (LinearLayout)convertView.findViewById(R.id.headingLL);
			holder.headingTV = (TextView)convertView.findViewById(R.id.headingTV);
			holder.nameLL = (LinearLayout)convertView.findViewById(R.id.nameLL);

			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < items.size()) {

			final ContactsModel contact = items.get(position);
			if (contact != null
					&& (contact.getFirstName().length() == 1)) 
			{
				holder.nameLL.setVisibility(View.GONE);
				holder.headingLL.setVisibility(View.VISIBLE);
				holder.headingTV.setText(contact.getFirstName());
				//holder.headingLL.setBackgroundColor(android.R.color.transparent);
			}
			else
			{
				holder.nameLL.setVisibility(View.VISIBLE);
				holder.headingLL.setVisibility(View.GONE);
				holder.name.setText(contact.getFirstName());
				
				View ll = (LinearLayout)holder.name.getParent();
                ll.setFocusable(true);
                ll.setSelected(true);
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity.getApplicationContext(), "Clicked on " + contact.getFirstName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, CDetailsWebView.class);
                        intent.putExtra("CONTACT_POSITION", position);
                        activity.startActivity(intent);
                    }
                });
			}
		}
		

		return convertView;
	}

	private static class ViewHolder {
		TextView name,headingTV;
		LinearLayout nameLL,headingLL;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
