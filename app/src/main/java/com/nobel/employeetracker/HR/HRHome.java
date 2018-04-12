package com.nobel.employeetracker.HR;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.nobel.employeetracker.R;

import Base.NobelActivity;

public class HRHome extends NobelActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        GotoSectionifRequired();
    }

    public static int SectionId=-1;
    public void GotoSectionifRequired(){
        if (SectionId==-1)return;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (SectionId==2)
        navigationView.setCheckedItem(R.id.nav_view_emp);
        else if (SectionId==4)
        navigationView.setCheckedItem(R.id.nav_tasks);

        onNavigationItemSelected(navigationView.getMenu().getItem(SectionId));
        SectionId=-1;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag = null;

        if (id == R.id.nav_home) {
             frag = new HelloAdmin();
        }

        if (id == R.id.nav_department) {
             frag = new Departments();
        }
        else if (id == R.id.nav_view_emp) {
             frag = new Employees();
        }
        else if (id == R.id.nav_add_emp) {
            startActivity(new Intent(this,AddEmployee.class).putExtra("title","Add Employee"));
        }
        else if (id == R.id.nav_tasks) {
             frag = new Tasks();
        }
        else if (id == R.id.nav_add_task) {
             startActivity(new Intent(this,AddTask.class).putExtra("title","Add Task"));
        } else if (id == R.id.nav_profile) {
            frag=new UpdateAdmin();
        }
        else if (id == R.id.nav_logout) {
             finish();
        }



        updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }





        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    TextView title;

    private void updateToolbarText(CharSequence text) {
        title.setText(text);
    }
}
