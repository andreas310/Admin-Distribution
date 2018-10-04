package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SettingadminActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView viewslider1,viewslider2,viewslider3,viewslider4,viewslider5;
    EditText editrate,editminim;
    private Context context = this;
    EditText oldpass,newpass;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private Uri filePath;
    private String slideposition="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingadmin);

        User user = SharedPrefManager.getInstance(this).getUser();

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        viewslider1 = (ImageView) findViewById(R.id.viewslider1);
        viewslider2 = (ImageView) findViewById(R.id.viewslider2);
        viewslider3 = (ImageView) findViewById(R.id.viewslider3);
        viewslider4 = (ImageView) findViewById(R.id.viewslider4);
        viewslider5 = (ImageView) findViewById(R.id.viewslider5);
        editrate = (EditText) findViewById(R.id.editrate);
        editminim = (EditText) findViewById(R.id.editminim);



        findViewById(R.id.btnchange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postchange();
            }
        });
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.viewslider1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                slideposition = "1";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });

        findViewById(R.id.viewslider2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                slideposition = "2";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });

        findViewById(R.id.viewslider3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                slideposition = "3";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });

        findViewById(R.id.viewslider4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                slideposition = "4";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });

        findViewById(R.id.viewslider5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                slideposition = "5";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });

        gettoko();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                filePath = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                switch (slideposition){
                    case "1":
                        // Set the Image in ImageView after decoding the String
                        viewslider1.setImageBitmap(BitmapFactory
                                .decodeFile(imgDecodableString));
                        uploadMultipart();
                        break;
                    case "2":
                        // Set the Image in ImageView after decoding the String
                        viewslider2.setImageBitmap(BitmapFactory
                                .decodeFile(imgDecodableString));
                        uploadMultipart();
                        break;
                    case "3":
                        // Set the Image in ImageView after decoding the String
                        viewslider3.setImageBitmap(BitmapFactory
                                .decodeFile(imgDecodableString));
                        uploadMultipart();
                        break;
                    case "4":
                        // Set the Image in ImageView after decoding the String
                        viewslider4.setImageBitmap(BitmapFactory
                                .decodeFile(imgDecodableString));
                        uploadMultipart();
                        break;
                    case "5":
                        // Set the Image in ImageView after decoding the String
                        viewslider5.setImageBitmap(BitmapFactory
                                .decodeFile(imgDecodableString));
                        uploadMultipart();
                        break;
                }


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    public void uploadMultipart() {
        //getting name for the image
//        String name = editText.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        switch (slideposition){
            case "1":
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();
                    User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                    String ids = user.getAddress();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId,URLs.URL_SETSLIDER1)
                            .addFileToUpload(path, "file") //Adding file
                            .addParameter("session_id", ids) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(3)
                            .startUpload(); //Starting the upload


                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case "2":
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();
                    User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                    String ids = user.getAddress();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId,URLs.URL_SETSLIDER2)
                            .addFileToUpload(path, "file") //Adding file
                            .addParameter("session_id", ids) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(3)
                            .startUpload(); //Starting the upload


                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case "3":
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();
                    User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                    String ids = user.getAddress();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId,URLs.URL_SETSLIDER3)
                            .addFileToUpload(path, "file") //Adding file
                            .addParameter("session_id", ids) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(3)
                            .startUpload(); //Starting the upload


                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case "4":
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();
                    User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                    String ids = user.getAddress();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId,URLs.URL_SETSLIDER4)
                            .addFileToUpload(path, "file") //Adding file
                            .addParameter("session_id", ids) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(3)
                            .startUpload(); //Starting the upload


                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case "5":
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();
                    User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                    String ids = user.getAddress();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId,URLs.URL_SETSLIDER5)
                            .addFileToUpload(path, "file") //Adding file
                            .addParameter("session_id", ids) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(3)
                            .startUpload(); //Starting the upload


                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "Gagal Upload", Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void getacoun() {
        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONObject userJson = obj.getJSONObject("data");

                            String bankid = userJson.getString("bank_id");
                            String acounnum = userJson.getString("account_number");
                            String acounname = userJson.getString("account_name");

                            EditText acounnum1 =  (EditText)findViewById(R.id.acounnum);
                            EditText acounname1 = (EditText)findViewById(R.id.acounname);
                            TextView bankid1 =(TextView)findViewById(R.id.textbank);


                            acounnum1.setText(acounnum);
                            acounname1.setText(acounname);
                            bankid1.setText(bankid);

                            getbank();
                            //starting the profile activity

                            break;

                        default:

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_USERBANK, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    private void getbank() {
        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONArray userJson = obj.getJSONArray("data");
                            for (int i = 0 ; i < userJson.length(); i++) {
                                JSONObject count = userJson.getJSONObject(i);
                                String item;
                                item = count.getString("id");
                                TextView idrec = (TextView)findViewById(R.id.idrec);
                                idrec.setText(item);
                                TextView bankid1 =(TextView)findViewById(R.id.textbank);
                                String bankid2 = bankid1.getText().toString();
                                if (Objects.equals(item,bankid2)){
                                    bankid1.setText(count.getString("short_name"));
                               }
                               else {

                                }
                            }



                            //starting the profile activity

                            break;

                        default:

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", "");

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_BANK, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    private void gettoko() {
        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONObject json1 = obj.getJSONObject("data");
                            JSONArray userJson = json1.getJSONArray("slider");
                            JSONObject userJson1 = json1.getJSONObject("exchange_rate");


                            String slide1 =  userJson.getString(0);
                            String slide2 = userJson.getString(1);
                            String slide3 = userJson.getString(2);
                            String slide4 = userJson.getString(3);
                            String slide5 = userJson.getString(4);

                            Picasso.with(context)
                                    .load(slide1)
                                    .fit().centerCrop()
                                    .into(viewslider1);
                            Picasso.with(context)
                                    .load(slide2)
                                    .fit().centerCrop()
                                    .into(viewslider2);
                            Picasso.with(context)
                                    .load(slide3)
                                    .fit().centerCrop()
                                    .into(viewslider3);
                            Picasso.with(context)
                                    .load(slide4)
                                    .fit().centerCrop()
                                    .into(viewslider4);
                            Picasso.with(context)
                                    .load(slide5)
                                    .fit().centerCrop()
                                    .into(viewslider5);

                            editrate.setText(userJson1.getString("rate"));
                            editminim.setText(userJson1.getString("minimum"));
                            //starting the profile activity

                            break;

                        default:

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();
                String ids = user.getAddress();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("session_id", ids);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CONFIG, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }



    private void postchange() {
        //first getting the values
        if (TextUtils.isEmpty(editrate.getText())) {
            oldpass.setError("Please enter your Rate");
            oldpass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(editminim.getText())) {
            newpass.setError("Please enter your Minim reedem");
            newpass.requestFocus();
            return;
        }

        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Success");
                            builder1.setMessage(obj.getString("data"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                            //starting the profile activity

                            break;

                        default:

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Failed");
                            builder.setMessage(obj.getString("message"));
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert1 = builder.create();
                            alert1.show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();

                String idx = user.getAddress();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("session_id", idx);
                params.put("exchange_rate", editrate.getText().toString());
                params.put("minimum_exchange", editminim.getText().toString());

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EDITEXCHANGE, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    private void postdealer() {
        //first getting the values
        final EditText tokoview = (EditText)findViewById(R.id.tokoview);
        final EditText phone = (EditText) findViewById(R.id.phonetokoview);
        final EditText address = (EditText) findViewById(R.id.addresstoko);

        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            startActivity(new Intent(getApplicationContext(), DealerActivity.class));
                            Toast.makeText(getApplicationContext(), obj.getString("data"), Toast.LENGTH_SHORT).show();
                            finish();

                            //starting the profile activity

                            break;

                        default:

                            Toast.makeText(getApplicationContext(), obj.getString("message")+"2", Toast.LENGTH_SHORT).show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                User2 user2 = SharedPrefManager2.getInstance(SettingadminActivity.this).getUser();

                String id = user2.getPoin();
                User user = SharedPrefManager.getInstance(SettingadminActivity.this).getUser();

                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);
                params.put("dealer_id", id);
                params.put("dealer_name", tokoview.getText().toString());
                params.put("dealer_phone", phone.getText().toString());
                params.put("dealer_address", address.getText().toString());

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EDITDEALERDETAIL, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }




    public void onBackPressed() {
        finish();
    }



}
