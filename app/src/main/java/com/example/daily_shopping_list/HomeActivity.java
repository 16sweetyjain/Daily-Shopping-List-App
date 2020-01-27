package com.example.daily_shopping_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.daily_shopping_list.Model.Data;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import java.text.DateFormat;

public class HomeActivity extends AppCompatActivity {

   private Toolbar mToolbar;
    private EditText edit_type;
    private EditText edit_amount;
    private EditText edit_note;
   private Button save_btn_101;
    AlertDialog dialog;
    private DatabaseReference mReference;
   // private Firebase mReference;
  private FirebaseAuth mAuth;
    private int amount;
    private RecyclerView mRecyclerView;
private TextView total_txt_view;
  //  private FloatingActionButton fab_btn;
    private Button fab_btn;

    private String type;
    private String Tamount;
    private String note;
    private String pos_key;

    private ArrayList<Data>myArrayList;
    private ListView myList;
    private ArrayAdapter<Data>mAdapetr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fab_btn = findViewById(R.id.fab);
        myList=findViewById(R.id.myList);

        mAdapetr=new ArrayAdapter<Data>(this,R.layout.support_simple_spinner_dropdown_item,myArrayList);





        total_txt_view = findViewById(R.id.total_amount);
        // total_txt_view=findViewById(R.id.total_amount);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();
        mReference = FirebaseDatabase.getInstance().getReference().child(uid);
       // mReference=new Firebase("https://daily-shopping-list-a43fc.firebaseio.com/");
        mReference.keepSynced(true);

       // mRecyclerView = findViewById(R.id.recycler_home);
      //  LinearLayoutManager layout_manager = new LinearLayoutManager(getApplicationContext());

      //  layout_manager.setStackFromEnd(true);
        //layout_manager.setReverseLayout(true);


//        mRecyclerView.setHasFixedSize(true);
  //      mRecyclerView.setLayoutManager(layout_manager);




        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent new_intent=new Intent(HomeActivity.this,UpdateActivity.class);
                startActivity(new_intent);

            }
        });



        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int total = 0;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Data data = snap.getValue(Data.class);
                    total += data.getAmount();
                    myArrayList.add(data);

                }
                myList.setAdapter(mAdapetr);

                String sTotal = String.valueOf(total + ".00");
                total_txt_view.setText(sTotal);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }







 /*   @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data,MyViewHolder> mAdapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>(
                Data.class,R.layout.item_data,MyViewHolder.class,mReference
        ) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder,final Data data, final int i) {

                myViewHolder.setDate(data.getDate());
                myViewHolder.setAmount(data.getAmount());
                myViewHolder.setNote(data.getNote());
                myViewHolder.setType(data.getType());

                myViewHolder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pos_key=getRef(i).getKey();
                        type=data.getType();
                        Tamount=String.valueOf(data.getAmount());
                        note=data.getNote();


                        update();
                    }
                });

            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myView;

        public MyViewHolder(View itemView){
            super(itemView);
            myView=itemView;
        }

        public void setAmount(int amount){
            TextView mType=myView.findViewById(R.id.amount);
            String s_amount=String.valueOf(amount);
            mType.setText(s_amount);
        }


        public void setDate(String date){
            TextView mType=myView.findViewById(R.id.date);
            mType.setText(date);
        }
        public void setNote(String note){
            TextView mType=myView.findViewById(R.id.note);
            mType.setText(note);
        }
        public void setType(String type){
            TextView mType=myView.findViewById(R.id.type);
            mType.setText(type);
        }




    }

    public void update(){
        AlertDialog.Builder new_builder=new AlertDialog.Builder(this);
        LayoutInflater mInflater=LayoutInflater.from(HomeActivity.this);

        View myView=mInflater.inflate(R.layout.update_input,null);

        final AlertDialog dialog=new_builder.create();
        dialog.setView(myView);
        dialog.show();


        final EditText upd_type=(EditText)findViewById(R.id.edt_type_upd);
        final EditText upd_amount=(EditText)findViewById(R.id.edt_amount_upd);
        final EditText upd_note=(EditText)findViewById(R.id.edt_note_upd);

        Button upd_btn=(Button)findViewById(R.id.edt_save_btn);
        Button del_btn=(Button)findViewById(R.id.edt_delete_btn_upd);

        upd_type.setText(type);
        upd_type.setSelection(type.length());
        upd_amount.setText(Tamount);
        upd_amount.setSelection(Tamount.length());
        upd_note.setText(note);
        upd_note.setSelection(note.length());



        upd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAmount=upd_amount.getText().toString().trim();
                String mType=upd_type.getText().toString().trim();
                String mNote=upd_note.getText().toString().trim();

                String date= DateFormat.getDateInstance().format(new Date());
                String id=mReference.push().getKey();



                int tot_amount=Integer.parseInt(mAmount);

                Data data=new Data(mType,mNote,id,tot_amount,date);
                mReference.child(pos_key).setValue(data);


            }
        });




        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReference.child(pos_key).removeValue();
                dialog.dismiss();


            }

        });


    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.logout: mAuth.signOut();
                               startActivity(new Intent(getApplicationContext(),MainActivity.class));

                               break;



        }
        return super.onOptionsItemSelected(item);

    }
}



