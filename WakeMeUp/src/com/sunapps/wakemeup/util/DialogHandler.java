package com.sunapps.wakemeup.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHandler {
	public static void printAlertDialog(Context context, String title, String description){
    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
    	dlgAlert.setTitle(title);
    	dlgAlert.setMessage(description);
    	dlgAlert.setCancelable(true);
    	dlgAlert.setPositiveButton("Ok",
	    new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	          //dismiss the dialog  
	        }
	    });
    	dlgAlert.create().show();
	}
}
