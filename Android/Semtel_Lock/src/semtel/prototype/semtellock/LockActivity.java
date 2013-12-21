package semtel.prototype.semtellock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LockActivity extends Activity implements OnClickListener{
	ApplicationClass applicationClass;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)  {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_lock);
	    
	    applicationClass = (ApplicationClass)getApplicationContext();
	    
        Button passwordInputButton = (Button)findViewById(R.id.passwordInputButton);
        
        passwordInputButton.setOnClickListener(this);
        
        
	
	    // TODO Auto-generated method stub
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		EditText passwordEditTextLock = (EditText)findViewById(R.id.passwordEditTextLock);
		String password = passwordEditTextLock.getText().toString();
		
		//////////////////////////////////////////////////////////
		if(applicationClass.semtelLock.isRight(password)){//accredit success
			Intent intent = new Intent(this, MainActivity.class);
			applicationClass.semtelLock.setUnLock();
			startActivity(intent);
			
		}else{//accredit fail
			Toast.makeText(this, "fail", 1000).show();
		}
		/////////////////////////////////////////////////////////////
		
		
	}
	
	

}
