package com.anuj.twitter.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anuj.twitter.R;

/**
 * Created by anujacharya on 2/18/16.
 */
public class ComposeTweetDialog extends DialogFragment {

    TextView tvCharCount;
    Button btnTweet;
    EditText etTweet;

    public static String TAG = "TWEET_DIALOG";
    final int MAX_LENGTH = 140;

    ComposeTweetDialogListner composeTweetDialogListner;

    public interface ComposeTweetDialogListner{
        void onTweet(String text);
    }
    public ComposeTweetDialog(){
        composeTweetDialogListner = null;
    }

    public void setComposeTweetDialogListner(ComposeTweetDialogListner composeTweetDialogListner) {
        this.composeTweetDialogListner = composeTweetDialogListner;
    }

    public static ComposeTweetDialog newInstance(ComposeTweetDialogListner composeTweetDialogListner) {
        ComposeTweetDialog frag = new ComposeTweetDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.setComposeTweetDialogListner(composeTweetDialogListner);
        return frag;
    }

    @Override
    public void onResume() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels/3;
        getDialog().getWindow().setLayout(width, height);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        setRetainInstance(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        final View filterView = inflater.inflate(R.layout.layout_compose_tweet, container, false);


        btnTweet = (Button)filterView.findViewById(R.id.btnTweet);
        etTweet = (EditText) filterView.findViewById(R.id.etTweet);
        tvCharCount =  (TextView)filterView.findViewById(R.id.tvCharCount);

        // on btn click send the data to the parent  activity
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etTweet.getText().toString();
                composeTweetDialogListner.onTweet(text);
                dismiss();
                return;
            }
        });

        etTweet.addTextChangedListener(txwatcher);

        return filterView;
    }


    // Count the characters in the edit text
    final TextWatcher txwatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            tvCharCount.setText(String.valueOf(s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };
}
