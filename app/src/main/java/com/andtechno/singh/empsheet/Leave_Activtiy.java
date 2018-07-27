package com.andtechno.singh.empsheet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class Leave_Activtiy extends AppCompatActivity {

    private EditText Name,Age,City,Designation,Gender;
    private TextView timestamp;
    ImageView mimageView;
    private Boolean isFabOpen = false;
    FloatingActionButton fab,fab1,fab2,fab3;
    TextView oneDay,halfDay,moreDay;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private int  oneday,halfday,mreday;
    sharePreference sharePref;
    int oneCount;
    EditText  ed1,ed2;
    LinearLayout ll_City,ll_Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave__activtiy);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharePref=new sharePreference(Leave_Activtiy.this);
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        moreDay=findViewById(R.id.moreDay);
        oneDay=findViewById(R.id.oneDay);
        ll_City=findViewById(R.id.ll_city);
        ll_Desc=findViewById(R.id.ll_Decs);
        halfDay=findViewById(R.id.halfDay);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        Bundle extras = getIntent().getExtras();
        int position= (int)extras.get("position");
        Log.e("Position",""+position);
        mimageView=findViewById(R.id.image_from_camera);
        Name=findViewById(R.id.etMName);
        Age=findViewById(R.id.etAge);
        Gender=findViewById(R.id.etgender);
        City=findViewById(R.id.City);
        Designation=findViewById(R.id.etDes);
        timestamp=findViewById(R.id.timestamp);

        Name.setText(MainActivity.nameList.get(position));
        Age.setText(MainActivity.agelist.get(position));
        Gender.setText(MainActivity.genderList.get(position));
        City.setText(MainActivity.Citylist.get(position));
        Designation.setText(MainActivity.desglist.get(position));

        new AsyncTaskLoadImage(mimageView).execute(MainActivity.imglist.get(position));
        if(MainActivity.genderList.get(position).equalsIgnoreCase("Male"))
            mimageView.setImageResource(R.drawable.male);
            else
                mimageView.setImageResource(R.drawable.female);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sharePref.getHalfLeave()==2){
                    alertDialog("Sorry ! You cant take half day in this month /n Limit exceeded.");
                }else{
                    halfday=halfday+1;
                    sharePref.setHalfLeave(halfday);
                    Snackbar.make(view, "Added leave for half day,", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(sharePref.getOneDay()==2){
                    alertDialog("Sorry ! You cant take leave for a day in this month /n Limit exceeded");
                }else{
                    oneday=oneday+1;
                    sharePref.setOneDay(oneday);
                    Snackbar.make(view, "Added leave for a day,", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreDialog();

            }
        });

    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            moreDay.startAnimation(fab_close);
            oneDay.startAnimation(fab_close);
            halfDay.startAnimation(fab_close);
            isFabOpen = false;
            ll_Desc.setVisibility(View.VISIBLE);
            ll_City.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.network);
            Log.d("fab", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            moreDay.startAnimation(fab_open);
            oneDay.startAnimation(fab_open);
            halfDay.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            ll_Desc.setVisibility(View.INVISIBLE);
            ll_City.setVisibility(View.INVISIBLE);
            fab.setImageResource(R.drawable.add);
            Log.d("Fab","open");

        }
    }

    public void moreDialog() {


        final Dialog dialog = new Dialog(Leave_Activtiy.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.moreleave_dialog);


        ed1=dialog.findViewById(R.id.etnumber);
       ed2=dialog.findViewById(R.id.etmore);



        TextView Continue =  dialog.findViewById(R.id.txtyes);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String halfCount= ed1.getText().toString();
                oneCount= Integer.parseInt(ed2.getText().toString());
                sharePref.setMoreDay(oneCount);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void LeaveDialog() {


        final Dialog dialog = new Dialog(Leave_Activtiy.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_box);


        TextView HALD=dialog.findViewById(R.id.hald);
        TextView ONE=dialog.findViewById(R.id.one);
        TextView MORE=dialog.findViewById(R.id.more);

        String halfCount= String.valueOf(sharePref.getHalfLeave());
        String oneCount= String.valueOf(sharePref.getOneDay());
        String moreCount= String.valueOf(sharePref.getMoreDay());
        HALD.setText(halfCount);
        ONE.setText(oneCount);
        MORE.setText(moreCount);
        TextView Continue = (TextView) dialog.findViewById(R.id.txtyes);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void attendancelist(View view) {

        LeaveDialog();

    }

    public void alertDialog(String str1){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Leave_Activtiy.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(str1);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tickmark);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

            }
        });


        // Showing Alert Message
        alertDialog.show();
    }
    public  class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
        private ImageView imageView;
        public AsyncTaskLoadImage(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (Exception e) {
                //  Log.e(TAG, e.getMessage());
            }catch (OutOfMemoryError e){
                e.getLocalizedMessage();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
