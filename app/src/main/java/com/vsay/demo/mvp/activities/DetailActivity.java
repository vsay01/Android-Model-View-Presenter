package com.vsay.demo.mvp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vsay.demo.mvp.R;
import com.vsay.demo.mvp.models.Event;
import com.vsay.demo.mvp.utils.Constants;
import com.vsay.demo.mvp.utils.LocalUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by vsaya on 2/11/17.
 */

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.event_date)
    TextView mEventDate;
    @BindView(R.id.event_title)
    TextView mEventTitle;
    @BindView(R.id.event_description)
    TextView mEventDescription;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.notMVP)
    TextView mNotMVP;

    private Event city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));
        city = new Gson().fromJson(getIntent().getStringExtra(Constants.EVENT_EXTRA), Event.class);
        setViewData(city);
    }

    private void setViewData(Event city) {
        Picasso.with(this).load(city.getImage())
                .error(R.drawable.placeholder_nomoon)
                .placeholder(R.drawable.placeholder_nomoon)
                .into(mImage);
        if (LocalUtils.getDate(city.getDate()).equals("")) {
            mEventTitle.setVisibility(View.GONE);
        } else {
            mEventDate.setText(LocalUtils.getDate(city.getDate()));
        }
        mEventTitle.setText(city.getTitle());
        mEventDescription.setText(city.getDescription());
        mNotMVP.setText(getString(R.string.not_mvp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_call:
                callIntent(city);
                return true;
            case R.id.action_share:
                shareTheDetail(city);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareTheDetail(Event city) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, city.getDescription());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private void callIntent(Event city) {
        String phoneNumber = getString(R.string.defaultPhoneNumber);
        if (city.getPhone() != null && !city.getPhone().equals("")) { //some event doesn't have phone number return back
            phoneNumber = city.getPhone();
        }
        Uri callUri = Uri.parse("tel:" + phoneNumber);
        Intent callIntent;
        if (LocalUtils.isTelephonyEnabled(this)) {
            callIntent = new Intent(Intent.ACTION_DIAL, callUri);
        } else {
            callIntent = new Intent(Intent.ACTION_VIEW, callUri);
        }
        startActivity(callIntent);
    }
}
