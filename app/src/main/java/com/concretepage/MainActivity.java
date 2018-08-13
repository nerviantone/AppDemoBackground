package com.concretepage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        OnClickListener listener = new OnClickListener() {
    	    public void onClick(View view) {
    	        Intent intent = new Intent(MainActivity.this, MyService.class);	
		        switch (view.getId()) {
		            case R.id.service_start:
		            	//starts service for the given Intent 
		                startService(intent);
		                break;
		            case R.id.service_stop:
		            	//stops service for the given Intent
		                stopService(intent);
		                break;
                }
		    }
    	};
    	findViewById(R.id.service_start).setOnClickListener(listener);
    	findViewById(R.id.service_stop).setOnClickListener(listener);
    }
}
