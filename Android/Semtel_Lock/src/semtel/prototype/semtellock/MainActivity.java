package semtel.prototype.semtellock;

import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener, OnClickListener{
	
	ApplicationClass applicationClass;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        applicationClass = (ApplicationClass)getApplicationContext();
        applicationClass.semtelLock = new SemtelLock(this);
        
        ToggleButton lockToggleButton = (ToggleButton)findViewById(R.id.LockToggleButton);
        Button passwordSaveButton = (Button)findViewById(R.id.PasswordSaveButton);
        
        lockToggleButton.setOnCheckedChangeListener(this);
        passwordSaveButton.setOnClickListener(this);
        
        if(applicationClass.semtelLock.isLock()){
        	lockToggleButton.setChecked(true);
        }else{
        	lockToggleButton.setChecked(false);
        }
        
        
        
    }
	
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		// TODO Auto-generated method stub
		
		ToggleButton toggleButton = (ToggleButton)arg0;
		
		if(isChecked){//true
			//On
			//////////////////////////////////////
			applicationClass.semtelLock.setLock();
			//////////////////////////////////////
			Toast.makeText(this, "setLock", 1000).show();
			
			
		}else{//false
			//Off
			///////////////////////////////////////
			applicationClass.semtelLock.setUnLock();
			////////////////////////////////////////
			
			Toast.makeText(this, "setUnLock", 1000).show();
		}
		
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		EditText passwordEditText = (EditText)findViewById(R.id.PasswrodEditText);
		String password = passwordEditText.getText().toString();
		
		////////////////////////////////////////////////////////////////
		boolean result = applicationClass.semtelLock.setPassword(password);
		if(result){
			Toast.makeText(this, "setPassword", 1000).show();//success
		}else{
			Toast.makeText(this, "password is undefined", 1000).show();//fail
		}
		///////////////////////////////////////////////////////////////
		
		
		
	}
    
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		//인텐트로 자신을 부른게 누군지 확인한다.
		
				//1. 자신앱의 액티비티에서 불려졌는지 확인 //인텐트를 이용한다.
				// 자신앱의 액티비티에서 불려졌으면 락 실행안함
				//2. 잠글 설정이 되 있는 지 확인
				//3.
				
				//최상위 액티비티 확인
		//Context mContext = getApplicationContext();
		
		ActivityManager activityManager = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
		List<RunningTaskInfo> info;
		info = activityManager.getRunningTasks(3); //
		 
		for (Iterator iterator = info.iterator(); iterator.hasNext();)  {
		    RunningTaskInfo runningTaskInfo = (RunningTaskInfo) iterator.next();
		    String s_name = runningTaskInfo.topActivity.getClassName();
		    Log.i("확인", s_name); // "com.android.ApplicationName.ActivityName" ���� ���
		}
		
		
		//to do...
		//
		
		//자신의 앱이라는 걸 알려준다. 다른 곳에 들어가야함//뷰 이동
		Intent receiveIntent = getIntent();
		boolean isMyApp = receiveIntent.getBooleanExtra("isMyApp", false);
		
		if (!isMyApp) {//
			///////////////////////////////////////
			if (applicationClass.semtelLock.isLock()) {
				//////////////////////////////////
				Intent intent = new Intent(this, LockActivity.class);
				intent.putExtra("isMyApp", true);
				startActivity(intent);
			}
		}
	
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	
	
}
