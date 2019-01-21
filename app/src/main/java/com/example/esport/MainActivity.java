package com.example.esport;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.esport.data.spf.MySharedPreference;
import com.example.esport.di.Injectable;
import com.example.esport.fragment.MainFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,Injectable{

    /*private SharedPreferences preferences;
    private SharedPreferences.Editor editor;*/

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    public MySharedPreference mySharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

//        MyApplication.getInstance().getAppComponent().inject(this);


        setContentView(R.layout.activity_main);

        /*preferences = getSharedPreferences("mypreference", MODE_PRIVATE);
        editor = preferences.edit();*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.action_search:
                savePreference();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * update the title on the actionbar.
     * @param title a String will be displayed on the actionbar as the title.
     */
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private void savePreference() {

//        Map<String,?> map = preferences.getAll();
        Map<String,?> map = mySharedPreference.getAll();
        List<String> items = new ArrayList<>();
        List<Boolean> status = new ArrayList<>();
        for(Map.Entry e: map.entrySet()){
            items.add((String) e.getKey());
            if(e.getValue().equals("Checked"))
                status.add(true);
            else
                status.add(false);
        }

        Collections.reverse(items);
        Collections.reverse(status);

        final String[] preItems = new String[items.size()];
        for(int i=0; i<items.size(); i++) {
            preItems[i] = items.get(i);
        }

        final boolean[] preStatus = new boolean[status.size()];
        for(int i=0; i<status.size(); i++){
            preStatus[i] = status.get(i);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Favourite Feed")
                .setIcon(R.mipmap.ic_launcher)
                .setMultiChoiceItems(preItems, preStatus, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for(int j=0; j<preStatus.length; j++){
                            if (preStatus[j])
//                                editor.putString(preItems[j],"Checked").commit();
                                mySharedPreference.putString(preItems[j],"Checked");
                            else
//                                editor.putString(preItems[j], "unChecked").commit();
                                mySharedPreference.putString(preItems[j], "unChecked");
                        }

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, MainFragment.newInstance())
                                .commitNow();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
