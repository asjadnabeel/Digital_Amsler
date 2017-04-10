package in.meamsler.mu.digitalamsler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;




public class InstructionActivity extends AppCompatActivity /* implements OnClickListener */
{
    //private GestureDetector gestureDetector;
    Button accept, exit;
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

         img = (ImageView) findViewById(R.id.imageView1);


        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), DefectList.class);
                startActivity(i);

                // your code here
            }
        });

		/*
		 * accept=(Button)findViewById(R.id.button1);
		 * exit=(Button)findViewById(R.id.button2);
		 * accept.setOnClickListener(this); exit.setOnClickListener(this);
		 */
       // gestureDetector = new GestureDetector(new SwipeGestureDetector());
    }

	/* ... */


    /* public boolean onTouchEvent(MotionEvent event)
    {
        if (gestureDetector.onTouchEvent(event))
        {
          return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeftSwipe()
    {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void onRightSwipe()
    {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    */

    // Private class for gestures
    /* public class SwipeGestureDetector extends SimpleOnGestureListener
    {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            try
            {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                {
                    InstructionActivity.this.onLeftSwipe();

                    // Right swipe
                } else if (-diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                {
                    InstructionActivity.this.onRightSwipe();
                }
            } catch (Exception e)
            {
                Log.e("YourActivity", "Error on gestures");
            }
            return false;
        }
    }*/

}
