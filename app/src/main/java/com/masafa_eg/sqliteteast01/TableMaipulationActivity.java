package com.masafa_eg.sqliteteast01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by amohamady on 01/23/2018.
 */

public class TableMaipulationActivity extends Activity {

    EditText etFirstName;
    EditText etLastName;

    Button btnDML;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_manipulation);
        getAllWidgets();
        bindWidgetsWithEvents();
        checkForRequest();
    }

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        if (request.equals(Constants.UPDATE)) {
            btnDML.setText(Constants.UPDATE);
            etFirstName.setText(getIntent().getExtras().get(Constants.FIRST_NAME).toString());
            etLastName.setText(getIntent().getExtras().get(Constants.LAST_NAME).toString());
        } else {
            btnDML.setText(Constants.INSERT);
        }
    }

    private void getAllWidgets() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastname);
        btnDML = (Button) findViewById(R.id.btnDML);
    }

    private void bindWidgetsWithEvents() {
        btnDML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }

    private void onButtonClick() {
        if (etFirstName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add the First Name ", Toast.LENGTH_LONG).show();
        } else if (etLastName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add the Last Name ", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.FIRST_NAME, etFirstName.getText().toString());
            intent.putExtra(Constants.LAST_NAME, etLastName.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
