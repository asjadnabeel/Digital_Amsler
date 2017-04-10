package in.meamsler.mu.digitalamsler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;



public class Welcome extends AppCompatActivity  {
    private static final int SPLASH_DISPLAY_TIME = 2000; // splash screen delay time

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);

                Welcome.this.startActivity(intent);
                Welcome.this.finish();

                // transition from splash to main menu
                //overridePendingTransition(R.layout.activityfadein, R.layout.splashfadeout);

            }
        }, SPLASH_DISPLAY_TIME);
    }}