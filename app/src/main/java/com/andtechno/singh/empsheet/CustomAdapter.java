package com.andtechno.singh.empsheet;

/**
 * Created by retisense on 26/07/18.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CustomAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] imageId;
    public ImageView imageView;
    public CustomAdapter(Activity context,
                      String[] web, String[] imageId) {
        super(context, R.layout.recycler_list_view, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.recycler_list_view, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        imageView = rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        new AsyncTaskLoadImage(imageView).execute(EmployeeDetails.imageId[position]);

        return rowView;
    }

    public static class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
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