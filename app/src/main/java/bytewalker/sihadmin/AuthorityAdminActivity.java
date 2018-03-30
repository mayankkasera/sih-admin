package bytewalker.sihadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class AuthorityAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    EditText mserachtext;
    String searching_text ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_admin);

        mserachtext = findViewById(R.id.search_authority_txt);
        recyclerView = findViewById(R.id.authority_recyclerView);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mserachtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searching_text = s.toString();
                onStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searching_text = s.toString();
                onStart();
            }

            @Override
            public void afterTextChanged(Editable s) {
                searching_text = s.toString();
                onStart();
            }
        });


    }

    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("authority_admin").orderByChild("authority").startAt(searching_text.toLowerCase())
                .endAt(searching_text.toLowerCase() + "\uf8ff");


        FirebaseRecyclerOptions<AuthorityModal> options =
                new FirebaseRecyclerOptions.Builder<AuthorityModal>()
                        .setQuery(query, AuthorityModal.class)
                        .build();

        FirebaseRecyclerAdapter<AuthorityModal,AuthoriyAdminViewHolder> adapter
                = new FirebaseRecyclerAdapter<AuthorityModal,AuthoriyAdminViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull AuthoriyAdminViewHolder holder, int position, @NonNull AuthorityModal user) {
                final int pos = position;
                holder.setImage(user.getImage());
                holder.settitle(user.getAuthority().toUpperCase());
                holder.setAdminName( "Admin :" +user.getName());

                final String Admin_id = getRef(position).getKey().toString();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        Intent i = new Intent(getBaseContext(),ProfileActivity.class);
//                        i.putExtra("admin_id",Admin_id);
//                        i.putExtra("admin_type","authority");
//                        startActivity(i);
                    }
                });

            }

            @Override
            public AuthoriyAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_admin_layout, parent, false);

                return new AuthoriyAdminViewHolder(mView);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public class AuthoriyAdminViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public AuthoriyAdminViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void settitle(String title){
            TextView textView = mView.findViewById(R.id.txt_admin);
            textView.setText(title);
        }

        public void setImage(String image) {
            ImageView img =  mView.findViewById(R.id.image_admin);;
            Picasso.with(AuthorityAdminActivity.this).load(image).into(img);
        }


        public  void setAdminName(String title)
        {
            TextView textView = mView.findViewById(R.id.txt_admin_name);
            textView.setText(title);
        }



        public View getView(){
            return this.mView;
        }

    }

}
