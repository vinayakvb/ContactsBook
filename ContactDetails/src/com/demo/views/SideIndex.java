package com.demo.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.demo.R;
import com.demo.models.ContactsModel;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class SideIndex extends Activity {

	private static final String TAG = SideIndex.class.getSimpleName();

	protected GestureDetector mGestureDetector;

	protected ArrayList<ContactsModel> mContactsList;

	protected int mTotalListSize = 0;

	/**
	 * list with items for side index
	 */
	private ArrayList<Object[]> mIndexList = null;

	/**
	 * list with row number for side index
	 */
	protected HashMap<String, Integer> mDealMap = new HashMap<String, Integer>();

	/**
	 * height of left side index
	 */
	protected int mSideIndexHeight;

	/**
	 * number of items in the side index
	 */
	private int mIndexListSize;

	/**
	 * x and y coordinates within our side index
	 */
	protected static float mSideIndexX;
	protected static float mSideIndexY;

	protected TextView mSelectedIndexTV;

	private List<String> mAlphaList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, TAG);
	}

	private int listLocation;

	/**
	 * displayListItem is method used to display the row from the list on scrool
	 * or touch.
	 */
	public void displayListItem() {

		// compute number of pixels for every side index item
		double pixelPerIndexItem = (double) mSideIndexHeight / mIndexListSize;

		// compute the item index for given event position belongs to
		int itemPosition = (int) (mSideIndexY / pixelPerIndexItem);

		if (itemPosition < 0) {
			itemPosition = 0;
		} else if (itemPosition >= mAlphaList.size()) {
			itemPosition = mAlphaList.size() - 1;
		}

		mSelectedIndexTV.setText(mAlphaList.get(itemPosition));

		ListView listView = (ListView) findViewById(R.id.booksLV);

		if (mDealMap.containsKey(mAlphaList.get(itemPosition))) {
			listLocation = mDealMap.get(mAlphaList.get(itemPosition));
			
			if (listLocation > mTotalListSize) {
				listLocation = mTotalListSize;
			}

			listView.setSelection(listLocation);
		}
	}

	/**
	 * getListArrayIndex consists of index details, to navigate through out the
	 * index.
	 * 
	 * @param strArr
	 *            , index values
	 * @return ArrayList object
	 */
	private ArrayList<Object[]> getListArrayIndex(String[] strArr) {
		ArrayList<Object[]> tmpIndexList = new ArrayList<Object[]>();
		Object[] tmpIndexItem = null;

		int tmpPos = 0;
		String tmpLetter = "";
		String currentLetter = null;

		for (int j = 0; j < strArr.length; j++) {
			currentLetter = strArr[j];

			// every time new letters comes
			// save it to index list
			if (!currentLetter.equalsIgnoreCase(tmpLetter)) {
				tmpIndexItem = new Object[3];
				tmpIndexItem[0] = tmpLetter;
				tmpIndexItem[1] = tmpPos - 1;
				tmpIndexItem[2] = j - 1;

				tmpLetter = currentLetter;
				tmpPos = j + 1;
				tmpIndexList.add(tmpIndexItem);
			}
		}

		// save also last letter
		tmpIndexItem = new Object[3];
		tmpIndexItem[0] = tmpLetter;
		tmpIndexItem[1] = tmpPos - 1;
		tmpIndexItem[2] = strArr.length - 1;
		tmpIndexList.add(tmpIndexItem);

		// and remove first temporary empty entry
		if (tmpIndexList != null && tmpIndexList.size() > 0) {
			tmpIndexList.remove(0);
		}

		return tmpIndexList;
	}

	/**
	 * getDisplayListOnChange method display the list from the index.
	 * 
	 * @param displayScreen
	 *            , specify which screen it belongs
	 */
	public void getDisplayListOnChange() {
		LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
		mSideIndexHeight = sideIndex.getHeight();

		if (mSideIndexHeight == 0) {
			Display display = getWindowManager().getDefaultDisplay();
			mSideIndexHeight = display.getHeight();
			// Navigation Bar + Tab space comes approximately 80dip
		}

		sideIndex.removeAllViews();

		/**
		 * temporary TextView for every visible item
		 */
		TextView tmpTV = null;

		/**
		 * we will create the index list
		 */
		String[] strArr = new String[mContactsList.size()];

		int j = 0;
		for (ContactsModel user : mContactsList) {
			strArr[j++] = user.getFirstName().substring(0, 1);
		}

		mIndexList = getListArrayIndex(strArr);

		/**
		 * number of items in the index List
		 */
		mIndexListSize = mIndexList.size();

		/**
		 * maximal number of item, which could be displayed
		 */
		int indexMaxSize = (int) Math.floor(mSideIndexHeight / 25);

		int tmpIndexListSize = mIndexListSize;

		/**
		 * handling that case when indexListSize > indexMaxSize, if true display
		 * the half of the side index otherwise full index.
		 */
		while (tmpIndexListSize > indexMaxSize) {
			tmpIndexListSize = tmpIndexListSize / 2;
		}

		/**
		 * computing delta (only a part of items will be displayed to save a
		 * place, without compact
		 */
		double delta = mIndexListSize / tmpIndexListSize;

		String tmpLetter = null;
		Object[] tmpIndexItem = null;
		
		ArrayList<String> alphabets = new ArrayList<String>();
		alphabets.add("A");
		alphabets.add("B");
		alphabets.add("C");
		alphabets.add("D");
		alphabets.add("E");
		alphabets.add("F");
		alphabets.add("G");
		alphabets.add("H");
		alphabets.add("I");
		alphabets.add("J");
		alphabets.add("K");
		alphabets.add("L");
		alphabets.add("M");
		alphabets.add("N");
		alphabets.add("O");
		alphabets.add("P");
		alphabets.add("Q");
		alphabets.add("R");
		alphabets.add("S");
		alphabets.add("T");
		alphabets.add("U");
		alphabets.add("V");
		alphabets.add("W");
		alphabets.add("X");
		alphabets.add("Y");
		alphabets.add("Z");
		
		
		mIndexListSize = alphabets.size();
		
		for(int i=0;i<alphabets.size();i++) {
			tmpTV = new TextView(this);
			tmpLetter = alphabets.get(i).toString();
			tmpTV.setText(tmpLetter);
			tmpTV.setTextColor(Color.GRAY);
			tmpTV.setTypeface(Typeface.DEFAULT_BOLD);
			tmpTV.setGravity(Gravity.CENTER);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 1);
			tmpTV.setLayoutParams(params);
			mAlphaList.add(tmpLetter);
			sideIndex.addView(tmpTV);
		}

		/*
		for (double i = 1; i <= indexListSize; i = i + delta) {
			tmpIndexItem = indexList.get((int) i - 1);
			tmpLetter = tmpIndexItem[0].toString();
			tmpTV = new TextView(this);
			tmpTV.setText(tmpLetter);
			tmpTV.setGravity(Gravity.CENTER);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 1);
			tmpTV.setLayoutParams(params);
			alphaList.add(tmpLetter);
			sideIndex.addView(tmpTV);
		}*/

		sideIndex.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mSideIndexX = event.getX();
				mSideIndexY = event.getY();

				displayListItem();

				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
					mSelectedIndexTV.setText("");
				}
				return false;
			}
		});
	}

	protected OnClickListener onClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
		}
	};
}
