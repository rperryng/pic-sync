package com.rperryng.picsync.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rperryng.picsync.R;

/**
 * Created by Ryan PerryNguyen on 2014-12-06.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = MainFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button buttonYo = (Button) getActivity().findViewById(R.id.main_button_yo);
        buttonYo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(), "Yo yo", Toast.LENGTH_SHORT).show();
    }
}
