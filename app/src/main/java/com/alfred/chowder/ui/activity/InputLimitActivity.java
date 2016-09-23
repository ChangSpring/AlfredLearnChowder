package com.alfred.chowder.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.alfred.chowder.R;
import com.alfred.chowder.ui.base.BaseActivity;

public class InputLimitActivity extends BaseActivity {

	private EditText mEditText;

	/**
	 * 整数位数
	 */
	private static final int INTEGER_COUNT = 3;
	/**
	 * 小数位数
	 */
	private static final int DECIMAL_COUNT = 2;


	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_limit);

		mEditText = (EditText) findViewById(R.id.edittext);
//		mEditText.addTextChangedListener(mTextWatcher);
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				Log.i("InputLimitActivity", "beforeTextChanged");
				Log.i("beforeTextChanged", "s = " + s);
				Log.i("beforeTextChanged", "start = " + start);
				Log.i("beforeTextChanged", "count = " + count);
				Log.i("beforeTextChanged", "after = " + after);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.i("InputLimitActivity", "onTextChanged");
				Log.i("onTextChanged", "s = " + s);
				Log.i("onTextChanged", "start = " + start);
				Log.i("onTextChanged", "count = " + count);
				Log.i("onTextChanged", "before = " + before);
				if (start == 0 && s.toString().equals(".") && count == 1) {
					//输入的第一个字符为"."
					mEditText.setText("");
				} else if (s.length() >= INTEGER_COUNT + 1 && count != 0) {
					//当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
					if (s.toString().contains(".")) {
						//当前输入的有"."字符
						String[] text = s.toString().split("\\.");
						if (text.length >= 2 && text[1].length() > DECIMAL_COUNT) {
							//小数位数超数
//							s.delete(text[0].length() + text[1].length() + 1, s.toString().length());
//							mEditText.setText(s);
							mEditText.setText(s.subSequence(0, s.toString().length() - 1));
							mEditText.setSelection(s.toString().length() - 1);
						}
					} else {
//						s.delete(s.toString().length() - 1, s.toString().length());
//						mEditText.setText(s);
						mEditText.setText(s.subSequence(0, s.toString().length() - 1));
						mEditText.setSelection(s.toString().length() - 1);
					}
				}

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("InputLimitActivity", "afterTextChanged");
				Log.i("afterTextChanged", "s = " + s);


			}
		});
	}

	private String saveDoubleDecimal(String value) {
		return String.format("3.2f", Double.valueOf(value));
	}

/*	TextWatcher mTextWatcher = new TextWatcher() {
		private CharSequence temp;
		private int editStart;
		private int editEnd;

		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
									  int arg3) {
			temp = s;
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
								  int arg3) {
//			mTextView.setText(s);
		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = mEditText.getSelectionStart();
			editEnd = mEditText.getSelectionEnd();
			if (temp.length() > 10) {
				Toast.makeText(InputLimitActivity.this,
						"你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
						.show();
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				mEditText.setText(s);
				mEditText.setSelection(tempSelection);
			}
		}
	};*/
}
