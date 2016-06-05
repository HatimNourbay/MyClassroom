package com.example.hatim.myclassroom.Tab.ContactTab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.example.hatim.myclassroom.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;



public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    //Référence à la classe DataBaseHelper pour accéder aux DAO
    private DataBaseHelper databaseHelper = null;

    TextView addTitleTV, addPrenomTV, addNomTV;
    EditText addPrenomET, addNomET;
    FloatingActionButton addPhotoFABtn;
    Button addBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addTitleTV = (TextView) findViewById(R.id.addTitleTV);
        addPrenomTV = (TextView) findViewById(R.id.addPrenomTV);
        addNomTV = (TextView) findViewById(R.id.addNomTV);

        addPrenomET = (EditText) findViewById(R.id.addPrenomET);
        addNomET = (EditText) findViewById(R.id.addNomET);

        addPhotoFABtn = (FloatingActionButton) findViewById(R.id.addFABtn);
        addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == addBtn) {
            // All input fields are mandatory, so made a check
            if (addPrenomET.getText().toString().trim().length() > 0 &&
                    addNomET.getText().toString().trim().length() > 0) {
                // Once click on "Submit", it's first creates the ContactTable object
                final ContactTable contactTable = new ContactTable();

                // Then, set all the values from user input
                contactTable.prenom = addPrenomET.getText().toString();
                contactTable.nom = addNomET.getText().toString();

                try {
                    // This is how, a reference of DAO object can be done
                    final Dao<ContactTable, Integer> contactDao = getHelper().getContactDao();

                    //This is the way to insert data into a database table
                    contactDao.create(contactTable);
                    reset();
                    showDialog();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Show a dialog with appropriate message in case input fields are blank
            else {
                showMessageDialog("Remplir les champs");
            }
        }

    }

    private void showDialog() {
        // After submission, Dialog opens up with "Success" message. So, build the AlertBox first
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the appropriate message into it.
        alertDialogBuilder.setMessage("Le contact a bien été ajouté, que voulez-vous faire maintenant?");

        // Add a positive button and it's action. In our case action would be, just hide the dialog box ,
        // so no need to write any code for that.
        alertDialogBuilder.setPositiveButton("Ajouter nouveau",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //rien faire -> pour à nouveau remplir le champs
                        // ou bien mettre finish(), mais dans ce cas va supprimer l'ajout du new contact
                    }
                });

        // Add a negative button and it's action. In our case, just open up the ViewTeacherRecordActivity screen
        // to display all the records
        alertDialogBuilder.setNegativeButton("Voir contacts",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent negativeActivity = new Intent(getApplicationContext(),ContactFragment.class);
                        //startActivity(negativeActivity);
                        finish();
                    }
                });

        // Now, create the Dialog and show it.
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void showMessageDialog(String message) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private void reset() {

        addPrenomET.setText("");
        addNomET.setText("");

    }

    // This is how, DatabaseHelper can be initialized for future use
    private DataBaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DataBaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
		/*
		 * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
