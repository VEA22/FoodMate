package com.example.foodmate;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView party_name;
        private ImageView party_image;
        private Data data;

        ItemViewHolder(View itemView) {
            super(itemView);

            party_name = itemView.findViewById(R.id.Party_name);
            party_image = itemView.findViewById(R.id.Party_image);
        }

        void onBind(Data data) {
            this.data = data;

            party_name.setText(data.getTitle());
            party_image.setImageResource(data.getResId());

            itemView.setOnClickListener(this);
            party_name.setOnClickListener(this);
            party_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final String uid = user.getUid();

            db.collection("user").whereEqualTo("uid", uid).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    Toast.makeText(itemView.getContext(), data.getTitle(), Toast.LENGTH_SHORT).show();
                                    Intent intent_cr = new Intent(itemView.getContext(), ChatRoom.class);

                                    intent_cr.putExtra("chatName", data.getName());
                                    intent_cr.putExtra("userName", (String)document.getData().get("name"));
                                    itemView.getContext().startActivity(intent_cr);

                                }
                            }
                            else {
                            }
                        }
                    });
/*
            Toast.makeText(itemView.getContext(), data.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent_cr = new Intent(itemView.getContext(), ProfileActivity.class);

            intent_cr.putExtra("chatName", data.getName());
            intent_cr.putExtra("userName", "pbj");
            itemView.getContext().startActivity(intent_cr);

 */
        }
    }
}
