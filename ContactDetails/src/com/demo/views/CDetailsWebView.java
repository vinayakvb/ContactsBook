package com.demo.views;

import java.util.ArrayList;
import java.util.Random;

import com.demo.R;
import com.demo.models.ContactsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * @author vinayak bevinakatti
 *
 */
public class CDetailsWebView extends Activity{
	SharedPreferences mSP;
	int mSelectedContact;
	WebView webView;
	ArrayList<ContactsModel> mContactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cdetails_webview);
		
		mSelectedContact = getIntent().getIntExtra("CONTACT_POSITION", 0);
		mSP = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		
		//Retrieve stored contacts list
		Gson gson = new Gson();
		String jsonString = mSP.getString("ContactsObj", "");
		ArrayList<ContactsModel> contactList = gson.fromJson(jsonString, 
				new TypeToken<ArrayList<ContactsModel>>() {}.getType());
		ContactsModel contact = new ContactsModel();
		mContactList = getCategorisedList(contactList);
		contact = mContactList.get(mSelectedContact);
		
		final String fname = contact.getFirstName();
		final String sname = contact.getSurName();
		final String phone = contact.getPhoneNumber();
		final String email = contact.getEmailAddr();
		
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/contactHTML.html");
		webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
		webView.setWebViewClient(new WebViewClient(){
		    public void onPageFinished(WebView view, String url){  
		    	String js = "javascript:showDetails('" + fname + "','" + sname + "','" + phone + "','" + email + "')";
		    	webView.loadUrl(js);
		    }           
		});
		
	}
	
	public class JavaScriptInterface {
	    Context mContext;
	    
	    /** Instantiate the interface and set the context */
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }

	    /** Callback from webview for closing this screen */
	    @JavascriptInterface
	    public void closeWebView() {
	        finish();
	    }
	    
	    /** Callback from webview for loading random contact */
	    @JavascriptInterface
	    public void fetchRandomContact() {
	        int contactSize = mContactList.size();
	        Random rand = new Random();
	        int randomNum = rand.nextInt((contactSize - 0) + 1) + 0;
	        
	        ContactsModel contact = new ContactsModel();
			contact = mContactList.get(randomNum);
			
			final String fname = contact.getFirstName();
			final String sname = contact.getSurName();
			final String phone = contact.getPhoneNumber();
			final String email = contact.getEmailAddr();
			
			runOnUiThread(new Runnable() {
				public void run() {
					String js = "javascript:showRandomContact('" + fname + "')";
					webView.loadUrl(js);
				}
			});
	    	
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
            }
            v.add(temp);
        }
        return v;
    }

}
