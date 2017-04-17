package com.example.nope.fuckandroid;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.media.SoundPool;
import android.media.AudioManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity{
    //vars
    int soundId;
    SoundPool soundPool;
    AudioManager am;
    Toolbar toolbar;
    int lastId = 0;
    String strings[] = {"Airhorn", "Nextel", ":^)"};

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Now Playing" + strings[0]);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                soundPool.stop(lastId);
                playSound();
            }
        });

        toolbar.setTitle("Now Playing: " + strings[0]);

        //get sys volume

        am = (AudioManager)getSystemService(AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(audioAttributes).build();
        soundId = soundPool.load(this, R.raw.horn, 1);

        CustomAdapter<String> adapter = new CustomAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                setSound((int)id);
            }


        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    private void setSound(int id)
    {

        toolbar.setTitle("Now Playing: " + strings[id]);
        switch(id)
        {
            case 0:
                soundPool.stop(lastId);
                soundId = soundPool.load(this, R.raw.horn, 1);
                playSound();
                break;
            case 1:
                soundPool.stop(lastId);
                soundId = soundPool.load(this, R.raw.ass, 2);
                playSound();
                break;
            case 2:
                soundPool.stop(lastId);
                soundId = soundPool.load(this, R.raw.tuturu, 3);
                playSound();
                break;
            default:
                soundPool.stop(lastId);
                soundId = soundPool.load(this, R.raw.horn, 1);
                playSound();
                break;
        }

        return;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;

            default:
                return false;
        }
    }

    private void playSound()
    {
        lastId = soundPool.play(soundId, 1, 1, 0, 0, 1.0f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
