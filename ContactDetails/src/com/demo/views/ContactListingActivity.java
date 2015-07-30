package com.demo.views;

import java.util.ArrayList;
import java.util.Collections;

import com.demo.R;
import com.demo.adapters.ContactsListAdapter;
import com.demo.models.ContactsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author vinayak bevinakatti
 *
 */
public class ContactListingActivity extends SideIndex implements OnClickListener{
	
	ListView mContactsListView;
    ContactsListAdapter mContactsListAdapter;
    SharedPreferences mSP;
    LinearLayout mListLayout;
    TextView mNoDataTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_listing);
		mContactsListView = (ListView)findViewById(R.id.booksLV);
        mSelectedIndexTV = (TextView) findViewById(R.id.selectedIndex);
        ((LinearLayout) findViewById(R.id.addContact)).setOnClickListener(this);
        ((Button) findViewById(R.id.addContactBtn)).setOnClickListener(this);
        
        mListLayout = (LinearLayout)findViewById(R.id.listLayout);
        mNoDataTextView = (TextView)findViewById(R.id.noData);
        
        mSP = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        
        loadContacts();
	}
	
/*	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getDisplayListOnChange();
    }*/
	
	 /**
     * ListIndexGestureListener method gets the list on scroll.
     */
    private class ListIndexGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {

            /**
             * we know already coordinates of first touch we know as well a
             * scroll distance
             */
            mSideIndexX = mSideIndexX - distanceX;
            mSideIndexY = mSideIndexY - distanceY;

            /**
             * when the user scrolls within our side index, we can show for
             * every position in it a proper item in the list
             */
            if (mSideIndexX >= 0 && mSideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
	
	private void loadContacts(){
		
		//Retrieve stored contacts list
		Gson gson = new Gson();
		String jsonString = mSP.getString("ContactsObj", "");
		mContactsList = gson.fromJson(jsonString, 
				new TypeToken<ArrayList<ContactsModel>>() {}.getType());

		if (mContactsList == null || mContactsList.size() <= 0) {
			mNoDataTextView.setVisibility(View.VISIBLE);
			mListLayout.setVisibility(View.GONE);
		} else {
			getDisplayListOnChange();
			
	        // Enable fast scrolling by side index
	        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
	        sideIndex.setOnClickListener(onClicked);
	        mSideIndexHeight = sideIndex.getHeight();
	        mGestureDetector = new GestureDetector(this,
	                new ListIndexGestureListener());
			
	        // Prepare contacts list
			mNoDataTextView.setVisibility(View.GONE);
			mListLayout.setVisibility(View.VISIBLE);
			Collections.sort(mContactsList);
	        ArrayList<ContactsModel> catList = getCategorisedList(mContactsList);
	        mTotalListSize = catList.size();
	        
	        mContactsListAdapter = new ContactsListAdapter(this, catList);
	        mContactsListView.setAdapter(mContactsListAdapter);
		}
	}
	
	private ArrayList<ContactsModel> getCategorisedList(
			ArrayList<ContactsModel> contactList) {
		ArrayList<ContactsModel> v = new ArrayList<ContactsModel>();
        String x1 = null;
        String x2 = null;
        for (int i = 0; i < contactList.size(); i++) {
            ContactsModel temp = contactList.get(i);
            //Insert the alphabets
            x1 = (temp.getFirstName().substring(0, 1)).toLowerCase();
            if(!x1.equalsIgnoreCase(x2))
            {
                v.add(new ContactsModel(x1.toUpperCase(), "", "" , ""));
                x2 = x1;
                mDealMap.put(x1.toUpperCase(), i);
            }
            v.add(temp);
        }
        return v;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		loadContacts();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.addContact:
			intent = new Intent(this, AddContactActivity.class);
            startActivityForResult(intent, 1100);
			break;
		case R.id.addContactBtn:
			intent = new Intent(this, AddContactActivity.class);
			startActivityForResult(intent, 1100);
			break;
		}	
	}
}
