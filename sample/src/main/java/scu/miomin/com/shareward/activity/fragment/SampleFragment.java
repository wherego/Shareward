package scu.miomin.com.shareward.activity.fragment;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import scu.miomin.com.shareward.R;
import scu.miomin.com.shareward.core.BaseFragment;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class SampleFragment extends BaseFragment {

    private ProgressDialog dialog;

    private Button btn_query;

    public static SampleFragment newInstance() {
        SampleFragment fragment = new SampleFragment();
        return fragment;
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    protected void setUpView() {
        btn_query = (Button) fragmentView.findViewById(R.id.btn_query);

    }

    @Override
    protected void setUpData() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getResources().getString(R.string.app_name));
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    public void query() {
        dialog.show();
        dialog.dismiss();
    }
}
