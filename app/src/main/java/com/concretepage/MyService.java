package com.concretepage;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
	  private static final String TAG = "MyService";
	  private boolean isRunning  = false;
	  private Looper looper;
	  private MyServiceHandler myServiceHandler;
	  @Override
	  public void onCreate() {
	    HandlerThread handlerthread = new HandlerThread("MyThread", Process.THREAD_PRIORITY_BACKGROUND);
	    handlerthread.start();
	    looper = handlerthread.getLooper();
	    myServiceHandler = new MyServiceHandler(looper);
        isRunning = true;
	  }
	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {

		  onTaskRemoved(intent);
	      Message msg = myServiceHandler.obtainMessage();
	      msg.arg1 = startId;
	      myServiceHandler.sendMessage(msg);
	      Toast.makeText(this, "MyService Started.", Toast.LENGTH_SHORT).show();
	      //If service is killed while starting, it restarts. 
	      return START_STICKY;
	  }
	  @Override
	  public IBinder onBind(Intent intent) {
	      return null;
	  }
	  @Override
	  public void onDestroy() {
	    isRunning = false;  
	    Toast.makeText(this, "MyService Completed or Stopped.", Toast.LENGTH_SHORT).show();
	  }
	  private final class MyServiceHandler extends Handler {
	      public MyServiceHandler(Looper looper) {
	          super(looper);
	      }
	      @Override
	      public void handleMessage(Message msg) {
              synchronized (this) {
            	  for (int i = 0; i < 10; i++) {
                      try {
                          Runtime.getRuntime().exec(new String[] { "su", "-c", "input tap 523.5 1772.0"});

                          Log.i(TAG, "MyService running...");
                          Thread.sleep(60000);
                      } catch (Exception e) {
                    	  Log.i(TAG, e.getMessage());
                      }
                      if(!isRunning){
                    	  break;
                      } 
                  }
              }
              //stops the service for the start id.
	          stopSelfResult(msg.arg1);
	      }
	  }
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
		restartServiceIntent.setPackage(getPackageName());
		startService(restartServiceIntent);



		super.onTaskRemoved(rootIntent);
	}
	}