package obrabo.adlib;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import obrabo.adlib.helper.Global;
import obrabo.adlib.service.Service;

public  class AdPlus {
    static Dialog dialog;
    static  Button btnCancel;
    static CircularProgressView progress_view;
    static ImageView imgAd;
    static  Activity activity;

    public static void advert(Activity activityHome, String id){
        Global.idUser = id;
        activity = activityHome;
        dialog = new Dialog(activity, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
       // app:cpv_animAutostart="true"
       // app:cpv_indeterminate="true"
        //view
        progress_view = (CircularProgressView) dialog.findViewById(R.id.progress_view);
       // progress_view.setColor(R.color.colorPrimary);
       // progress_view.setIndeterminate(true);
       // progress_view.startAnimation();
        imgAd = (ImageView) dialog.findViewById(R.id.imgAd);

        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        new getAdvertAsync().execute();
    }

    private static class getAdvertAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return Service.advert();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress_view.setVisibility(View.GONE);
            Log.e("reMaster==", result);
            if (result.length() > 1) {
                try {
                    final JSONObject obj = new JSONObject(result);
                    Picasso.with(activity).load(obj.getString("imagem")).into(imgAd);
                    if (obj.getString("link").length() > 5) {
                        imgAd.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(obj.getString("link")));
                                    activity.startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                dialog.dismiss();
            }

        }
    }
}
