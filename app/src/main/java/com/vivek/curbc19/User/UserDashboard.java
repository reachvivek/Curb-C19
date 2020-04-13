package com.vivek.curbc19.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivek.curbc19.Common.FaqActivity;
import com.vivek.curbc19.Common.HelplineActivity;
import com.vivek.curbc19.Common.InfoActivity;
import com.vivek.curbc19.Common.RequestPermit;
import com.vivek.curbc19.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.vivek.curbc19.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.vivek.curbc19.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.vivek.curbc19.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.vivek.curbc19.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MYREQUESTCODE = 101;

    //Variables
    List<AuthUI.IdpConfig> providers;
    private FirebaseAuth mAuth;

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuIcon;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    static final float END_SCALE = 0.7f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Provider
        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        //Functions will be executed automatically when this activity will be created
        featuredRecycler();
        mostViewedRecycler();
        checkUserState();
    }

    private void checkUserState() {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if (currentUser!=null) {
            Toast.makeText(this, "Signed In: "+currentUser.getPhoneNumber(), Toast.LENGTH_LONG).show();
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);
        }
        else {
            Toast.makeText(this, "Sign In Required!", Toast.LENGTH_LONG).show();
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        }


    }

    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
            
    }


    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_login:
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                promptSignIn();
                break;
            case R.id.nav_logout:
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                AuthUI.getInstance()
                        .signOut(UserDashboard.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Menu nav_Menu = navigationView.getMenu();
                                nav_Menu.findItem(R.id.nav_login).setVisible(true);
                                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                                nav_Menu.findItem(R.id.nav_profile).setVisible(false);
                                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                                    drawerLayout.closeDrawer(GravityCompat.START);
                                else drawerLayout.openDrawer(GravityCompat.START);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserDashboard.this, "Failed:"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        }
        return true;
    }

    private void promptSignIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.FirebaseAuthUI)
                .build(), MYREQUESTCODE

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MYREQUESTCODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK)
            {
                //Fetch User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //Show Number on Toast
                Toast.makeText(this, "Sign In Successful: "+user.getPhoneNumber(), Toast.LENGTH_LONG).show();

                //Activate Logout Button
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_login).setVisible(false);
                nav_Menu.findItem(R.id.nav_logout).setVisible(true);
                nav_Menu.findItem(R.id.nav_profile).setVisible(true);

            }
            if (response == null)
            {
                Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show();
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_login).setVisible(true);
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                nav_Menu.findItem(R.id.nav_profile).setVisible(false);
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Sign-In Cancelled", Toast.LENGTH_SHORT).show();
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_login).setVisible(true);
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                nav_Menu.findItem(R.id.nav_profile).setVisible(false);
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }



    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.news2, "Times of India", "Covid-19 lockdown: PM Modi to address the nation on 14 April"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.news1, "Business Standard", "Lockdowns can't end until Covid-19 vaccine found, study says"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.bbc, "BBC News", "Coronavirus LIVE updates: India cases rise to 9352; death toll ..."));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.news4, "India Today", "Coronavirus in India LIVE: Active cases fall below number of ..."));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.maharastra, getString(R.string.st1), getString(R.string.stc1)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.delhi, getString(R.string.st2), getString(R.string.stc2)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.tamilnadu, getString(R.string.st3), getString(R.string.stc3)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.rajasthan, getString(R.string.st4), getString(R.string.stc4)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.madhyapradesh, getString(R.string.st5), getString(R.string.stc5)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.telangana, getString(R.string.st6), getString(R.string.stc6)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.gujrat, getString(R.string.st8), getString(R.string.stc8)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.uttarpradesh, getString(R.string.st9), getString(R.string.stc9)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.andrapradesh, getString(R.string.st10), getString(R.string.stc10)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.kerala, getString(R.string.st11), getString(R.string.stc11)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.jammuandkashmir, getString(R.string.st12), getString(R.string.stc12)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.karnataka, getString(R.string.st13), getString(R.string.stc13)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.haryana, getString(R.string.st14), getString(R.string.stc14)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.punjab, getString(R.string.st15), getString(R.string.stc15)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.westbengal, getString(R.string.st16), getString(R.string.stc16)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.bihar, getString(R.string.st17), getString(R.string.stc17)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.odisa, getString(R.string.st18), getString(R.string.stc18)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.uttarakhand, getString(R.string.st19), getString(R.string.stc19)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.himachalpradesh, getString(R.string.st20), getString(R.string.stc20)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.chattishgarh, getString(R.string.st21), getString(R.string.stc21)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.assam, getString(R.string.st22), getString(R.string.stc22)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.chan, getString(R.string.st23), getString(R.string.stc23)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.jharkhand, getString(R.string.st24), getString(R.string.stc24)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.goa, getString(R.string.st25), getString(R.string.stc25)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.manipur, getString(R.string.st26), getString(R.string.stc26)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.tripura, getString(R.string.st27), getString(R.string.stc27)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.arunachalpradesh, getString(R.string.st28), getString(R.string.stc28)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mizoram, getString(R.string.st29), getString(R.string.stc29)));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.nagaland, getString(R.string.st30), getString(R.string.stc30)));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }

    //Section 1 Components
    public void toggleUserState(View view) {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.FirebaseAuthUI)
                        .build(), MYREQUESTCODE

        );
    }

    public void info(View view) {
        startActivity(new Intent(this, InfoActivity.class));
    }

    public void faq(View view) {
        startActivity(new Intent(this, FaqActivity.class));
    }

    public void requestForm(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if (currentUser!=null) {
            startActivity(new Intent(this, RequestPermit.class));
        }
        else {
            Toast.makeText(this, "You Need To Sign In!", Toast.LENGTH_LONG).show();
            promptSignIn();
        }
    }

    public void helpline(View view) {
        startActivity(new Intent(this, HelplineActivity.class));
    }



}
