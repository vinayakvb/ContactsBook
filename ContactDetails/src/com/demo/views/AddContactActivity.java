package com.demo.views;

import java.util.ArrayList;

import com.demo.R;
import com.demo.models.ContactsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author vinayak bevinakatti
 *
 */
public class AddContactActivity extends Activity implements OnClickListener{
	
	EditText mFNameEditText;
	EditText mSurnameEditText;
	EditText mPhoneEditText;
	EditText mEmailEditText;
	SharedPreferences mSP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		((Button)findViewById(R.id.done_btn)).setOnClickListener(this);
		((Button)findViewById(R.id.cancel_btn)).setOnClickListener(this);
		mFNameEditText = (EditText)findViewById(R.id.fname);
		mSurnameEditText = (EditText)findViewById(R.id.surname);
		mPhoneEditText = (EditText)findViewById(R.id.phone);
		mEmailEditText = (EditText)findViewById(R.id.email);
		mSP = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.done_btn:
			
			String fnameStr = mFNameEditText.getText().toString().trim();
			String surnameStr = mSurnameEditText.getText().toString().trim();
			String phoneStr = mPhoneEditText.getText().toString().trim();
			String emailStr = mEmailEditText.getText().toString().trim();
			
			if (fnameStr.equals("")) {
				Toast.makeText(this, "First name cannot be empty", Toast.LENGTH_LONG).show();
			} else if (surnameStr.equals("")) {
				Toast.makeText(this, "Surname cannot be empty", Toast.LENGTH_LONG).show();
			} else if (!isValidPhoneNumber(phoneStr)) {
				Toast.makeText(this, "Invalid phone number", Toast.LENGTH_LONG).show();
			} else if (!isValidEmail(emailStr)) {
				Toast.makeText(this, "Invalid email address", Toast.LENGTH_LONG).show();
			} else {
				
				//Retrieve stored contacts list
				Gson gson = new Gson();
				String jsonString = mSP.getString("ContactsObj", "");
				ArrayList<ContactsModel> contactList = gson.fromJson(jsonString, 
						new TypeToken<ArrayList<ContactsModel>>() {}.getType());
				
				if(contactList == null) {
					contactList = new ArrayList<ContactsModel>();
				}
				
				ContactsModel contact = new ContactsModel();
				contact.setFirstName(fnameStr);
				contact.setSurName(surnameStr);
				contact.setPhoneNumber(phoneStr);
				contact.setEmailAddr(emailStr);
				contactList.add(contact);
				
				// Update contact list with new entry
				Editor prefsEditor = mSP.edit();
				
				String json = gson.toJson(contactList);
				prefsEditor.putString("ContactsObj", json);
				prefsEditor.commit(); 
				
				Toast.makeText(this, "Contact Added.", Toast.LENGTH_LONG).show();
				finish();
			}
			
			break;
		case R.id.cancel_btn:
			finish();
			break;
		}
	}
	
	private boolean isValidEmail(CharSequence email) {
	    if (!TextUtils.isEmpty(email)) {
	        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	    }
	    return false;
	}

	private boolean isValidPhoneNumber(CharSequence phoneNumber) {
	    if (!TextUtils.isEmpty(phoneNumber)) {
	        return Patterns.PHONE.matcher(phoneNumber).matches();
	    }
	    return false;
	}
}
