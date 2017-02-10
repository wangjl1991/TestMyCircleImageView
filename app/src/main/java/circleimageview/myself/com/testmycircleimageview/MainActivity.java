package circleimageview.myself.com.testmycircleimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import circleimageview.myself.com.mylibrary.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.CircleImageView);
        //circleImageView.setOutCircleWidth(200);
        //circleImageView.setOutCircleColor(Color.WHITE);


    }
}
