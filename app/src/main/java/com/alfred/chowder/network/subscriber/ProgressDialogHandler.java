package com.alfred.chowder.network.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.alfred.chowder.interf.ProgressCancelListener;

/**
 * Created by Alfred on 16/10/20.
 */

public class ProgressDialogHandler extends Handler {
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private boolean isCancelable;
    private ProgressCancelListener mProgressCancelListener;

    public static  final int DIALOG_SHOW = 1;
    public static final int DIALOG_DISMISS = 2;

    public ProgressDialogHandler(Context context,ProgressCancelListener progressCancelListener,boolean isCancelable){
        super();
        this.mContext = context;
        this.mProgressCancelListener = progressCancelListener;
        this.isCancelable = isCancelable;
    }

    private void initProgressDialog(){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(isCancelable);

            if (isCancelable){
                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!mProgressDialog.isShowing()){
                mProgressDialog.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case DIALOG_SHOW:
                initProgressDialog();
                break;
            case DIALOG_DISMISS:
                dismissProgressDialog();
                break;
        }
    }
}
