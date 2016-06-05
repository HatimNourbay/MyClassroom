package com.example.hatim.myclassroom.Tab.WelcomeTab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Hatim on 05/06/2016.
 */
public class InformationLoading {


    public InformationLoading(){

    }

    public void retrieveProfilePicture(String ImageUrl, final Context mContext){
        Picasso.with(mContext)
                .load(ImageUrl)
                .into(new Target() {
                          @Override
                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                              try {

                                  String name = "profile.png";
                                  File myDir = new File(mContext.getFilesDir(),name);

                                  FileOutputStream out = new FileOutputStream(myDir);
                                  bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                                  out.flush();
                                  out.close();
                              } catch(Exception e){
                                  // some action
                              }
                          }

                          @Override
                          public void onBitmapFailed(Drawable errorDrawable) {
                          }

                          @Override
                          public void onPrepareLoad(Drawable placeHolderDrawable) {
                          }
                      }
                );

    }


}
