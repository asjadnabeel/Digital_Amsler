package in.meamsler.mu.digitalamsler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import in.meamsler.mu.digitalamsler.utils.*;



public class MainActivity extends AppCompatActivity {

    MyView mv;
    AlertDialog dialog;
    ArrayList<Integer> zoneList, scoreList;

    TextView zoneInfo;
    int defect1, defect2, defect3, defect4, score = 0;
    private LinearLayout.LayoutParams params,params2;
    static int total_score;
    private Paint mPaint;


    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int STROKE_WIDTH_ID = Menu.FIRST + 1;
    private static final int CLEAR_MENU_ID = Menu.FIRST + 2;
    private static final int GO_TO_LIST = Menu.FIRST + 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        total_score = 0 ;

        // for getting values sent from MainActivity1
        //Intent intent = getIntent();
        //int temp = intent.getIntExtra("int_value", 0);
        //int temp = 2;




        //------------

        //myClient = new TCPClient("192.168.43.130", 6666);
        //myClient.execute();



        //mList = (ListView)findViewById(R.id.list);

        // connect to the server



        //---------------


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.dactvbg);  //Background Image

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        layout.setLayoutParams(params);

        Button submitButton = new Button(this);
        Button resetButton = new Button(this);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //submitButton.setGravity(Gravity.CENTER);
        //submitButton.setPadding(0, 75, 0, 0);
        //submitButton.setBackgroundColor(Color.TRANSPARENT);
        //params.gravity = Gravity.center_vertical|center_horizontal;

        submitButton.setText("DONE ");
        submitButton.setTextColor(Color.WHITE);
        submitButton.setTypeface(null, Typeface.BOLD);
        submitButton.setTextSize(20);
        //submitButton.setGravity(Gravity.CENTER_HORIZONTAL);

        resetButton.setText("RESET");
        resetButton.setTextColor(Color.WHITE);
        resetButton.setTypeface(null, Typeface.BOLD);
        resetButton.setTextSize(20);

        zoneInfo = new TextView(this);
        zoneInfo.setTextColor(Color.WHITE);
        zoneInfo.setTextSize(20);
        zoneInfo.setTypeface(null, Typeface.BOLD);



        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        submitButton.setLayoutParams(params);

        mv = new MyView(this);


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        mv.setLayoutParams(params);
        mv.setDrawingCacheEnabled(true);
        mv.setAdjustViewBounds(true);

        layout.addView(mv);
        layout.addView(zoneInfo);


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.gravity= Gravity.FILL_HORIZONTAL;
        submitButton.setLayoutParams(params);
        layout.addView(submitButton);

        params2.gravity=Gravity.FILL_VERTICAL;
        resetButton.setLayoutParams(params2);
        layout.addView(resetButton);



        //params.gravity= Gravity.CENTER_HORIZONTAL;





        setContentView(layout);

        defect1 = getIntent().getIntExtra("defect1", 0);
        defect2 = getIntent().getIntExtra("defect2", 0);
        defect3 = getIntent().getIntExtra("defect3", 0);
        defect4 = getIntent().getIntExtra("defect4", 0);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mv.setDrawingCacheEnabled(false);
                mv.clearScreen();

                // Intent intent = new Intent(getApplicationContext(),DrawScreen.class);
                // startActivity(intent);

            }
        });






        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Add all the scores

                int finalScore = 0;

                for (int i : scoreList) {
                    finalScore += i;
                }

                // Get timestamp
                // SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                // String timeStamp = s.format(new Date());

                long timeStamp = System.currentTimeMillis();

                String fileName = timeStamp + "_" + finalScore;

                // Check if directory exists
                File dir = new File(MainActivity.this
                        .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        + "/MAmsler");

                if (!dir.exists()) {
                    dir = getExternalStorageDirectory();
                }

                if (!mv.isDrawingCacheEnabled())
                    mv.setDrawingCacheEnabled(true);

                Bitmap bitmap = mv.getDrawingCache();

                File file = new File(dir + "/" + fileName + ".png");
                try {
                    if (isExternalStorageWritable()) {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                        ostream.close();
                        mv.invalidate();
                        Toast.makeText(MainActivity.this,
                                "Image saved as " + fileName + ".png",
                                Toast.LENGTH_SHORT).show();

                        for (String imageFiles : Utils
                                .getFileList(MainActivity.this)) {
                            Log.i("Files", imageFiles);
                        }

                    } else {
                        GenericAlertDialog
                                .showDialog(MainActivity.this,
                                        "Unable to write to External Storage Directory, Please try again ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    GenericAlertDialog.showDialog(MainActivity.this,
                            "Unable to write to file! ");
                } finally {

                    mv.setDrawingCacheEnabled(false);

                    mv.clearScreen();

                }

            }
        });

		/*
		 * displayButton.setOnClickListener(new OnClickListener() {
		 *
		 * @Override public void onClick(View v) { Intent intent = new
		 * Intent(MainActivityActivity.this, DisplayListActivity.class);
		 * startActivity(intent);
		 * MainActivityActivity.this.overridePendingTransition
		 * (android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		 *
		 * } });
		 */

    }



    public class MyView extends  android.support.v7.widget.AppCompatImageView{

        private Bitmap mBitmap, originalBitmap, originalNonResizedBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private BitmapFactory.Options options = new BitmapFactory.Options();

        @SuppressLint("NewApi") public MyView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            options.inMutable = true;

            scoreList = new ArrayList<Integer>();

            originalNonResizedBitmap = Bitmap.createBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.amslergrid,
                            options));

            originalNonResizedBitmap = getResizedBitmap(originalNonResizedBitmap);

            originalBitmap = originalNonResizedBitmap.copy(
                    Bitmap.Config.ARGB_8888, true);
            mBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            mCanvas = new Canvas(mBitmap);

        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mCanvas = new Canvas(mBitmap);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            canvas.drawPath(mPath, mPaint);

        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 2;

        private void touch_start(float x, float y) {

            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;

        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
            // mPaint.setMaskFilter(null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    touch_start(x, y);
                    zoneList = new ArrayList<Integer>();
                    zoneList.add(getZoneLocation((int) x, (int) y));

                    //zoneInfo.setText("Current Score is " + score);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:

                    touch_move(x, y);
                    zoneList.add(getZoneLocation((int) x, (int) y));
                    x= Math.round(x);
                    y=Math.round(y);
                    //myClient.sendMessage(x+"@"+y);
                    zoneInfo.setText("Current loc" +x+" and "+y);








                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    int sum = 0;
                    for (int i : zoneList) {
                        sum += i;
                    }

                    int avg = Math.round((float) sum / zoneList.size());

                    score = (defect1 + defect2 + defect3 + defect4)
                            * avg
                            * ((defect1 * avg) + (defect2 * avg) + (defect3 * avg) + (defect4 * avg))
                            + 1;

                    scoreList.add(score);

                    int scoreSum = 0;
                    for (int i : scoreList) {
                        scoreSum += i;
                    }

                    zoneInfo.setText("Current Score is " + scoreSum);

                    mPaint.setXfermode(null);
                    invalidate();

                    break;
            }
            return true;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int newWidthMeasure = MeasureSpec.makeMeasureSpec(
                    mBitmap.getWidth(), MeasureSpec.EXACTLY);
            int newHeightMeasure = MeasureSpec.makeMeasureSpec(
                    mBitmap.getHeight(), MeasureSpec.EXACTLY);

            setMeasuredDimension(newWidthMeasure, newHeightMeasure);

        }

        public void clearScreen() {
            mCanvas.drawColor(Color.TRANSPARENT);
            mCanvas.drawBitmap(originalBitmap, 0, 0, mBitmapPaint);
            scoreList.clear();
            score = 0;
            zoneInfo.setText("Current Score is " + score);
            invalidate();
        }

        public Rect getCoordinateRect() {
            return new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        }

        int getZoneLocation(int x, int y) {

            Rect rectangle = new Rect();

            rectangle = getCoordinateRect();

            // Zone 1 Rectangle

            Rect outerMostRect = new Rect(rectangle);

            int xOffset = rectangle.width() / 10;
            int yOffset = rectangle.height() / 10;

            // Log.i("Rectangle Attribs", "Width: " + xOffset + "Height: " +
            // yOffset);

            // Zone 2 Rectangle

            Rect zone2Rectangle = new Rect(outerMostRect.left + xOffset,
                    outerMostRect.top + yOffset, outerMostRect.right - xOffset,
                    outerMostRect.bottom - yOffset);

            // Log.i("Zone 2 Coordinates", "" + zone2Rectangle.left + " " +
            // zone2Rectangle.top + " " + zone2Rectangle.right + " "
            // + zone2Rectangle.bottom);

            // Zone 3 Rectangle

            Rect zone3Rectangle = new Rect(zone2Rectangle.left + xOffset,
                    zone2Rectangle.top + yOffset, zone2Rectangle.right
                    - xOffset, zone2Rectangle.bottom - yOffset);

            // Zone 4 Rectangle

            Rect zone4Rectangle = new Rect(zone3Rectangle.left + xOffset,
                    zone3Rectangle.top + yOffset, zone3Rectangle.right
                    - xOffset, zone3Rectangle.bottom - yOffset);

            // Zone 5 Rectangle
            Rect zone5Rectangle = new Rect(zone4Rectangle.left + xOffset,
                    zone4Rectangle.top + yOffset, zone4Rectangle.right
                    - xOffset, zone4Rectangle.bottom - yOffset);

            // Check from inside out for point existence
            if (zone5Rectangle.contains(x, y)) {
                return 5;
            } else if (zone4Rectangle.contains(x, y)) {
                return 4;
            } else if (zone3Rectangle.contains(x, y)) {
                return 3;
            } else if (zone2Rectangle.contains(x, y)) {
                return 2;
            } else if (outerMostRect.contains(x, y)) {
                return 1;
            }
            return -1;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int newDimen = size.x;

        float scaleWidth = ((float) newDimen) / width;
        float scaleHeight = ((float) newDimen) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;

    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getExternalStorageDirectory() {
        File file = new File(
                this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "MAmsler");
        if (!file.mkdirs())
            return null;
        else
            return file;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, COLOR_MENU_ID, 0, "Color");
        menu.add(0, STROKE_WIDTH_ID, 0, "Stroke Width");
        menu.add(0, CLEAR_MENU_ID, 0, "Clear");
        menu.add(0, GO_TO_LIST, 0, "View Saved Images");

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
}

