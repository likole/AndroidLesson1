package cn.likole.test2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import static cn.likole.test2.JwxtTool.jwxt;

public class MainActivity extends AppCompatActivity {
    private TextView tv_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_info= (TextView) findViewById(R.id.tv_info);
        getExam();
    }

    private void getExam(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String[]> infos= jwxt.examPlan();
                    for(final String[] info:infos){
                        final String s=info[0]+info[1]+info[2];
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_info.setText(tv_info.getText()+"\n"+s);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
