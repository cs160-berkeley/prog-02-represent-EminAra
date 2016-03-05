
/**
 * Created by eminArakelian on 3/2/16.
 */


package com.cs160.joleary.catnip;

        import android.app.Fragment;

        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;

        import android.view.LayoutInflater;
        import android.widget.ProgressBar;
        import android.widget.TextView;

/**
 * Created by eminArakelian on 3/1/16.
 */
public class electionView extends Fragment {


    private View my_view;
    private String senator_name_1;
    private String senator_name_2;
    private String county_name;
    private int democrates_percentage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        my_view = inflater.inflate(R.layout.election_view, container, false);


        TextView txtCounty = (TextView) my_view.findViewById(R.id.textPlace);
        txtCounty.setText(county_name);

        ProgressBar myProg = (ProgressBar) my_view.findViewById(R.id.prgElection);
        myProg.setProgress(democrates_percentage);

        return my_view;

    }

    public void initElection(String senator_1, String senator_2, String countyName, int d_percentage) {


        senator_name_1 = senator_1;
        senator_name_2 = senator_2;
        county_name = countyName;
        democrates_percentage = d_percentage;


    }



}
