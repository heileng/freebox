package com.example.freebox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyProfileEditActivity extends BaseActivity {
	private static final String[] mGenderString = { "��ѡ���Ա�", "��", "Ů" };
	private static final String[] mAvatarNumString = { "��ѡ��ͷ����", "1", "2",
			"3", "3", "4", "5" };
	private static final String[] mConstellationString = { "��ѡ������", "������",
			"��ţ��", "˫����", "��з��", "ʨ����", "��Ů��", "�����", "��Ы��", "������", "Ħ����",
			"ˮƿ��", "˫����" };
	private Spinner mGenderSpinner;
	private ArrayAdapter<String> mGenderAdapter;
	private Spinner mConstellationSpinner;
	private ArrayAdapter<String> mConstellationAdapter;
	private Spinner mAvatarSpinner;
	private ArrayAdapter<String> mAvatarAdapter;
	private EditText mNikNameEdit;
	private EditText mAgeEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("�༭�ҵ�����");
		setContentLayout(R.layout.my_setting_layout);
		setbtn_rightRes(R.drawable.mm_title_btn_summit);
		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(
						MyProfileEditActivity.this,
						"�ǳƣ�"
								+ mNikNameEdit.getText().toString()+"\n"
								+ "ͷ���ţ�"
								+ mAvatarSpinner.getSelectedItem().toString()+"\n"
								+ "�Ա�"
								+ mGenderSpinner.getSelectedItem().toString()+"\n"
								+ "���䣺"
								+ mAgeEdit.getText().toString()+"\n"
								+ "������"
								+ mConstellationSpinner.getSelectedItem()
										.toString(), Toast.LENGTH_LONG).show();
			}

		});
		// ��ʼ��
		mNikNameEdit = (EditText) findViewById(R.id.nik_name_edit);
		mAgeEdit = (EditText) findViewById(R.id.age_edit);
		// �Ա������б�
		mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);
		mGenderAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mGenderString);
		mGenderAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGenderSpinner.setAdapter(mGenderAdapter);
		mGenderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// m_tTextView.setText(m_Countries[arg2]);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// ͷ�������б�
		mAvatarSpinner = (Spinner) findViewById(R.id.avatar_num_spinner);
		mAvatarAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mAvatarNumString);
		mAvatarAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAvatarSpinner.setAdapter(mAvatarAdapter);
		mAvatarSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// m_tTextView.setText(m_Countries[arg2]);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// ���������б�
		mConstellationSpinner = (Spinner) findViewById(R.id.constellation_spinner);
		mConstellationAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mConstellationString);
		mConstellationAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mConstellationSpinner.setAdapter(mConstellationAdapter);
		mConstellationSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// m_tTextView.setText(m_Countries[arg2]);
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}
}
