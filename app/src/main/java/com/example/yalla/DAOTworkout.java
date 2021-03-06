package com.example.yalla;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOTworkout
{
    private DatabaseReference databaseReference;
    public DAOTworkout(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Workout.class.getSimpleName());
    }
    public Task<Void> add(Workout workout){
        if (workout == null)
            return null;
        return databaseReference.push().setValue(workout);
    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }
}
