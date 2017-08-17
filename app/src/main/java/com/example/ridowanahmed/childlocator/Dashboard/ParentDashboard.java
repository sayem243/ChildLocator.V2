package com.example.ridowanahmed.childlocator.Dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ridowanahmed.childlocator.Map.ParentMap;
import com.example.ridowanahmed.childlocator.Model.ChildInformation;
import com.example.ridowanahmed.childlocator.R;
import com.example.ridowanahmed.childlocator.adapter.RecyclerAdapter;
import com.example.ridowanahmed.childlocator.adapter.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentDashboard extends AppCompatActivity {
    public static String CHILD_NAME = "passing@child_name";
    private DatabaseReference childData;
    SharedPreferences mSharedPreferences;
    private final String TAG = "ParentDashboard";
    ArrayList<ChildInformation> childList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        mSharedPreferences = ParentDashboard.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
        final String phoneNumber = mSharedPreferences.getString(getString(R.string.PARENT_GIVE_NUMBER), "");
        childList = new ArrayList<>();

        childData = FirebaseDatabase.getInstance().getReference(phoneNumber);
        childData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    Log.e(TAG, childSnapshot.getKey());

                    childList.add(childSnapshot.getValue(ChildInformation.class));
                    showChildList(childList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void showChildList(final ArrayList<ChildInformation> childList) {
        RecyclerAdapter adapter = new RecyclerAdapter(ParentDashboard.this, childList);

        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(ParentDashboard.this, 1);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);
        recycler_view.setAdapter(adapter);

        recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(ParentDashboard.this, recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        ChildInformation item = childList.get(position);
                        Intent childIntent = new Intent(ParentDashboard.this, ParentMap.class);
                        Log.e(TAG, "Click " + item.getChildName());
                        childIntent.putExtra(CHILD_NAME, item.getChildName());
                        startActivity(childIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        ChildInformation item = childList.get(position);

                        Toast.makeText(ParentDashboard.this, item.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
