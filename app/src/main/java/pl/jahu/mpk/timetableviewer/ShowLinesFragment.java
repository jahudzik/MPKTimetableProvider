package pl.jahu.mpk.timetableviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hudzj on 8/5/2014.
 */
public class ShowLinesFragment extends Fragment {

    public ShowLinesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lines, container, false);
        return rootView;
    }

}
