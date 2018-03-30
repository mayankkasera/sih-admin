package bytewalker.sihadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth ;
    DatabaseReference mDataRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       mAuth = FirebaseAuth.getInstance();
       mDataRoot = FirebaseDatabase.getInstance().getReference();



        if(!isUserLoggedIn())
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else
        {
            String ee = SharedpreferenceHelper.getInstance(this).getType();
            Toast.makeText(this, ee, Toast.LENGTH_SHORT).show();

            if(ee.equals("authority_admin"))
                startActivity(new Intent(this,AuthorityDashboardActivity.class));

            else {
                Intent i = new Intent(this,DashboardActivity.class);
                i.putExtra("type",ee);
                startActivity(i);
            }

            finish();
        }


    }

    private boolean isUserLoggedIn() {

        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
