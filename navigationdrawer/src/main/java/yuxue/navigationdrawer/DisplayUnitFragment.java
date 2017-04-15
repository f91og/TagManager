package yuxue.navigationdrawer;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yuxue on 2017/4/11.
 */

public class DisplayUnitFragment extends Fragment implements View.OnClickListener {
    private View vDisplayUnit;
    private RadioButton rbSem1, rbSem2, rbSummer;
    private Button bDisplayUnits, bClearUnits;
    private Spinner sYear;
    private TextView tvDisplayUnits;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Set Variables and listeners
        vDisplayUnit = inflater.inflate(R.layout.fragment_display_unit, container, false);
        rbSem1 = (RadioButton) vDisplayUnit.findViewById(R.id.rb_display_sem1);
        rbSem2 = (RadioButton) vDisplayUnit.findViewById(R.id.rb_display_sem2);
        rbSummer = (RadioButton) vDisplayUnit.findViewById(R.id.rb_display_summer);
        bDisplayUnits = (Button) vDisplayUnit.findViewById(R.id.b_display_units);
        bClearUnits = (Button) vDisplayUnit.findViewById(R.id.b_clear_units);
        sYear = (Spinner) vDisplayUnit.findViewById(R.id.s_display_Year);
        tvDisplayUnits = (TextView) vDisplayUnit.findViewById(R.id.tv_display_units);
        bDisplayUnits.setOnClickListener(this);
        bClearUnits.setOnClickListener(this);
        return vDisplayUnit;
    }
    @Override
    public void onClick(View v) {
// Get myUnits file
        SharedPreferences spMyUnits = getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
        switch (v.getId()) {
// If display units button is clicked:
            case R.id.b_display_units:
// Gather user input
                String year = sYear.getSelectedItem().toString();
                String semester;
                if (rbSem1.isChecked()) {
                    semester = "Sem 1";
                } else if (rbSem2.isChecked()) {
                    semester = "Sem 2";
                } else {
                    semester = "Summer";
                }
// Get saved units
                String error = "No units recorded for " + semester + ", " + year + ":\n";
                String sMyUnits = spMyUnits.getString("myUnits", null);
                if (sMyUnits == null) {
                    tvDisplayUnits.setText(error);
                    return;
                }
                JSONArray jaMyUnits = null;
                try {
                    jaMyUnits = new JSONArray(sMyUnits);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
// Search for corresponding units and save matching results to jaSearchUnits
                JSONArray jaSearchUnits = new JSONArray();
                for (int i = 0; i < jaMyUnits.length(); i++) {
                    JSONObject joUnit = null;
                    try {
                        joUnit = jaMyUnits.getJSONObject(i);
                        if (joUnit.getString("semester").equals(semester) && joUnit.getString("year").equals(year)) {
                            jaSearchUnits.put(joUnit);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
// Generate text shown in TextView
                if (jaSearchUnits.length() == 0) {
                    tvDisplayUnits.setText(error);
                    return;
                }
                String feedback = "Your units for " + semester + ", " + year + ":\n";
                for (int i = 0; i < jaSearchUnits.length(); i++) {
                    JSONObject joUnit = null;
                    try {
                        joUnit = jaSearchUnits.getJSONObject(i);
                        feedback += "\n" + joUnit.getString("unitCode") + "\n";
                        feedback += joUnit.getString("unitName") + "\n";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tvDisplayUnits.setText(feedback);
                break;
// If clear units button is clicked:
            case R.id.b_clear_units:
                SharedPreferences.Editor eMyUnits = spMyUnits.edit();
                eMyUnits.remove("myUnits");
                eMyUnits.apply();
                break;
        }
    }
}