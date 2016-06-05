package com.example.hatim.myclassroom.Tab.ContactTab;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.example.hatim.myclassroom.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;



public class ContactAddActivity extends AppCompatActivity {

    //Référence à la classe DataBaseHelper pour accéder aux DAO
    private DataBaseHelper databaseHelper = null;

    TextView addTitleTV, addPrenomTV, addNomTV;
    EditText addPrenomET, addNomET;
    FloatingActionButton addPhotoFABtn;
    Button addBtn;
    ImageView contactPreviewPhoto;

    private static final int RESULT_LOAD_IMG = 1;

    final ContactTable contactTable = new ContactTable();

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

        contactPreviewPhoto = (ImageView) findViewById(R.id.contactPreviewPhoto);

    }

    //private View.OnClickListener mClickListener(View view) {
    public void mClickListenerFABtn(View view) {
        addContactPhotoMethod(view);

    }
    public void mClickListenerBtn(View view) {
        addContactMethod(view);
    }

    private void addContactPhotoMethod(View view) {

        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMG);

        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                contactPreviewPhoto.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                //contactTable.photoPath = imgDecodableString;
                // -> à rajouter qd modifications effectuée dans ContactTable: c a d int en String + modification du nom: photo photoPath
                // il faudra que tu ajoutes le chemin dans picasso (qui est dans l'adapter) -> pour que ça mette l'image 

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    private void addContactMethod(View view) {

            if (addPrenomET.getText().toString().trim().length() > 0 &&
                    addNomET.getText().toString().trim().length() > 0) {
                // Once click on "Submit", it's first creates the ContactTable object


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
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("@string/no_text");
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

    }


    private void showDialog() {
        // After submission, Dialog opens up with "Success" message. So, build the AlertBox first
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the appropriate message into it.
        alertDialogBuilder.setMessage("@string/cont_ajouté");

        // Add a positive button and it's action. In our case action would be, just hide the dialog box ,
        // so no need to write any code for that.
        alertDialogBuilder.setPositiveButton("@string/cont_encore",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //rien faire -> pour à nouveau remplir le champs
                        // ou bien mettre finish(), mais dans ce cas va supprimer l'ajout du new contact
                    }
                });

        // Add a negative button and it's action.
        alertDialogBuilder.setNegativeButton("@string/cont_view",
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
