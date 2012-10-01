package com.op.kclock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.RemoteViews;
import android.util.Log;

public class Widget extends AppWidgetProvider {

	private String TAG = "com.op.widget";
      
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.e(TAG,"act");
		if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {

			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.widget);

		//	Intent alarmClockIntent  = new Intent("com.op.kclock.alarm.AlarmReceiver");
		//	intent2.putExtra("SCAN_MODE", "ONE_D_MODE");
			
			// Open alarm clock on click
			Intent alarmClockIntent = getAlarmClockIntent(context);
			if (alarmClockIntent != null) {
				PendingIntent pendingIntent = PendingIntent.getActivity(
						context, 0, alarmClockIntent, 0);
				views.setOnClickPendingIntent(R.id.Widget, pendingIntent);
			} else {
				Log.e(TAG,"wtf");
			}

					
			
			AppWidgetManager
					.getInstance(context)
					.updateAppWidget(
							intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS),
							views);
		}
	}

	private Intent getAlarmClockIntent(Context context) {
		PackageManager packageManager = context.getPackageManager();

		String clockImpls[][] = {
			{ "Standar Alarm Clock", "com.op.kclock.alarm",
				"com.op.kclock.alarm.AlarmReceiver" }
						};

		for (int i = 0; i < clockImpls.length; i++) {
			String vendor = clockImpls[i][0];
			String packageName = clockImpls[i][1];
			String className = clockImpls[i][2];
			try {
				ComponentName cn = new ComponentName(packageName, className);
				ActivityInfo aInfo = packageManager.getActivityInfo(cn,
						PackageManager.GET_META_DATA);
				Intent alarmClockIntent = new Intent(Intent.ACTION_MAIN)
						.addCategory(Intent.CATEGORY_LAUNCHER);
				alarmClockIntent.setComponent(cn);
				return alarmClockIntent;
			} catch (NameNotFoundException e) {
			}
		}

		return null;
	}
}
