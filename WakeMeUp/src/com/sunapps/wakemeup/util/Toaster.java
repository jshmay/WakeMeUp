package com.sunapps.wakemeup.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
	public static void print(Context context, String bread){
		Toast mToast = Toast.makeText(context, bread, Toast.LENGTH_SHORT);
		mToast.show();
	}
}
