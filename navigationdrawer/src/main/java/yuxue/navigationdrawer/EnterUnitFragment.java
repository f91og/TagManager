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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yuxue on 2017/4/11.
 */

public class EnterUnitFragment extends Fragment implements View.OnClickListener {
    private View vEnterUnit;
    private EditText etUnitCode, etUnitName;
    private TextView tvFeedback;
    private RadioButton rbSem1, rbSem2, rbSummer;
    private Button bSubmit;
    private Spinner sYear;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Set Variables and listener
        vEnterUnit = inflater.inflate(R.layout.fragment_enter_unit, container, false);
        etUnitCode = (EditText) vEnterUnit.findViewById(R.id.et_unit_code);
        etUnitName = (EditText) vEnterUnit.findViewById(R.id.et_unit_name);
        sYear = (Spinner) vEnterUnit.findViewById(R.id.s_year);
        tvFeedback = (TextView) vEnterUnit.findViewById(R.id.tv_feedback);
        rbSem1 = (RadioButton) vEnterUnit.findViewById(R.id.rb_sem1);
        rbSem2 = (RadioButton) vEnterUnit.findViewById(R.id.rb_sem2);
        rbSummer = (RadioButton) vEnterUnit.findViewById(R.id.rb_summer);
        bSubmit = (Button) vEnterUnit.findViewById(R.id.b_submit);
        bSubmit.setOnClickListener(this);
        return vEnterUnit;
    }
    /**
     * When the submit button is clicked
     */
    @Override
    public void onClick(View v) {
// Gather user input
        String unitCode = etUnitCode.getText().toString();
        String unitName = etUnitName.getText().toString();
        String year = sYear.getSelectedItem().toString();
        String semester;
        if (rbSem1.isChecked()) {
            semester = "Sem 1";
        } else if (rbSem2.isChecked()) {
            semester = "Sem 2";
        } else {
            semester = "Summer";
        }
// Validate user input
        if (unitCode.isEmpty()) {
            etUnitCode.setError("Unit Code is required!");
            return;
        }
        if (unitName.isEmpty()) {
            etUnitName.setError("Unit Name is required!");
            return;
        }
// Store user input to JSONObject
        JSONObject joUnit = new JSONObject();
        try {
            joUnit.put("unitCode", unitCode);
            joUnit.put("unitName", unitName);
            joUnit.put("year", year);
            joUnit.put("semester", semester);
        } catch (JSONException e) {
            e.printStackTrace();
        }
// Add created unit to file
        SharedPreferences spMyUnits = getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
        String sMyUnits = spMyUnits.getString("myUnits", null);
        JSONArray jaMyUnits = null;
// If myUnits has not been set, create one.
        if (sMyUnits == null) {
            jaMyUnits = new JSONArray();
        }
// If exist, parse it
        else {
            try {
                jaMyUnits = new JSONArray(sMyUnits);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
// Append joUnit to jaMyUnits and save back to file
        jaMyUnits.put(joUnit);
        SharedPreferences.Editor eMyUnits = spMyUnits.edit();
        eMyUnits.putString("myUnits", jaMyUnits.toString());
        eMyUnits.apply();
// Feedback
        String feedback = unitCode + " " + unitName + " has been recorded.";
        tvFeedback.setText(feedback);
    }
}
