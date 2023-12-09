package com.project.maku_mobile_based;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.maku_mobile_based.model.Food;

import java.util.Map;

public class MenuActivity extends AppCompatActivity {

   private  TextView textViewFoodName , textViewPrice;
   private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);
        getSupportActionBar().hide();

         textViewFoodName = findViewById(R.id.textViewFoodName);
         textViewPrice = findViewById(R.id.textViewPrice);


         db = FirebaseFirestore.getInstance();
        CollectionReference foodRef = db.collection("foods");

        foodRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> foodData = document.getData();
                        if (foodData != null) {
                            String foodName = (String) foodData.get("foods");
                            String price = (String) foodData.get("price");

                            textViewFoodName.append(foodName + "\n");
                            textViewPrice.append(price + "\n");
                        }
                    }
                } else {

                }
            }
        });


    }
}