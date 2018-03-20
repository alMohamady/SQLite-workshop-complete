package com.masafa_eg.sqliteteast01;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAddNewRecord;
    SQLiteHelper sqLiteHelper;

    LinearLayout parentLayout;
    LinearLayout LayoutDisplayPeople;

    TextView tvNoRecordsFound;
    String rowID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            getAllWidgets();
            sqLiteHelper = new SQLiteHelper(MainActivity.this);
            bindWidgetsWithEvent();
            displayAllRecords();
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK){
            String firstName = data.getStringExtra(Constants.FIRST_NAME);
            String lastName = data.getStringExtra(Constants.LAST_NAME);

            ContactModel contactModel =  new ContactModel();
            contactModel.setFirstName(firstName);
            contactModel.setLastName(lastName);

            if (requestCode == Constants.ADD_RECORD){
                sqLiteHelper.insertRecord(contactModel);
            } else if (requestCode == Constants.UPDATE_RECORD){
                contactModel.setID(rowID);
                sqLiteHelper.updateRecord(contactModel);
            }
            displayAllRecords();
        }
    }

    private void  getAllWidgets(){
        btnAddNewRecord = (Button) findViewById(R.id.btnAddNewRecord);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        LayoutDisplayPeople = (LinearLayout) findViewById(R.id.layoutDisplayPeople);
        tvNoRecordsFound = (TextView) findViewById(R.id.tvNoRecordsFound);
    }

    private void bindWidgetsWithEvent() {
        btnAddNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddRecords();
            }
        });
    }

    public void onAddRecords(){
        Intent intent = new Intent(MainActivity.this, TableMaipulationActivity.class);
        intent.putExtra(Constants.DML_TYPE, Constants.INSERT);
        startActivityForResult(intent, Constants.ADD_RECORD);
    }


    public void onUpdateRecords( String firstName, String lastName  ) {
        Intent intent = new Intent(MainActivity.this, TableMaipulationActivity.class);
        intent.putExtra(Constants.DML_TYPE, Constants.UPDATE);
        intent.putExtra(Constants.FIRST_NAME, firstName);
        intent.putExtra(Constants.LAST_NAME, lastName);
        startActivityForResult(intent, Constants.UPDATE_RECORD);
    }

    private void displayAllRecords() {
        LinearLayout inflateParentView;
        parentLayout.removeAllViews();

        ArrayList<ContactModel> contacts = sqLiteHelper.getAllRecords();

        if (contacts.size() > 0) {
            tvNoRecordsFound.setVisibility(View.GONE);

            ContactModel contactModel;

            for (int i = 0; i < contacts.size(); i++) {
                contactModel = contacts.get(i);

                final Holder holder = new Holder();
                final View view = LayoutInflater.from(this).inflate(R.layout.inflate_record, null);
                inflateParentView = (LinearLayout) view.findViewById(R.id.inflateParentView);
                holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);

                view.setTag(contactModel.getID());
                holder.firstName = contactModel.getFirstName();
                holder.lastName = contactModel.getLastName();
                String personName = holder.firstName + " " + holder.lastName;
                holder.tvFullName.setText(personName);

                final CharSequence[] item = {Constants.UPDATE, Constants.DELETE};
                inflateParentView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    rowID = view.getTag().toString();
                                    onUpdateRecords(holder.firstName, holder.lastName);
                                    displayAllRecords();
                                } else {
                                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                                    deleteDialog.setTitle("Delete Contact ?");
                                    deleteDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ContactModel contact = new ContactModel();
                                            contact.setID(view.getTag().toString());
                                            sqLiteHelper.deleteRecord(contact);
                                            displayAllRecords();
                                        }
                                    });
                                    deleteDialog.show();
                                }
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;
                    }
                });
                parentLayout.addView(view);
            }
        } else {
            tvNoRecordsFound.setVisibility(View.VISIBLE);
        }
    }

    private class Holder{
        TextView tvFullName;
        String firstName, lastName;
    }
}
