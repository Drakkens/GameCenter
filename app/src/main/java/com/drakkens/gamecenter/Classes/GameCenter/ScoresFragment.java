package com.drakkens.gamecenter.Classes.GameCenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.drakkens.gamecenter.Classes.DatabaseUtils.Database;
import com.drakkens.gamecenter.Classes.DatabaseUtils.Score;
import com.drakkens.gamecenter.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScoresFragment extends Fragment {
    private Database database;

    public ScoresFragment(Database database) {
        this.database = database;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_score, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new RecyclerViewListAdapter(database.getScores(null, null, null)));

        TextView usernameText = view.findViewById(R.id.usernameText);
        TextView scoreText = view.findViewById(R.id.findByScoreText);
        TextView timeText = view.findViewById(R.id.findByTimeText);
        TextView dateText = view.findViewById(R.id.findByDateText);

        ArrayList<TextView> dataTextViews = new ArrayList<>();
        dataTextViews.add(usernameText);
        dataTextViews.add(scoreText);
        dataTextViews.add(timeText);
        dataTextViews.add(dateText);

        Button sortButton = view.findViewById(R.id.sortButton);
        sortButton.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (sortButton.getText() == "ASC") sortButton.setText("DESC");
                else sortButton.setText("ASC");
            }
            return true;
        });


        Button searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                int selectionAmount = 0;
                for (TextView tv : dataTextViews) {
                    if (!tv.getText().toString().equals("")) {
                        selectionAmount++;
                    }
                }

                StringBuilder selection = new StringBuilder();
                StringBuilder orderBy = new StringBuilder();

                String[] selectionVals = new String[selectionAmount];
                int pos = 0;

                for (TextView tv : dataTextViews) {
                    if (!tv.getText().toString().equals("")) {
                        if (pos != 0) {
                            selection.append(" AND ");
                            orderBy.append(", ");

                        }
                        orderBy.append(tv.getContentDescription().toString());
                        orderBy.append(" ");
                        orderBy.append(sortButton.getText().toString());
                        selection.append(tv.getContentDescription().toString());
                        selection.append(" LIKE ?");
                        selectionVals[pos] = "%" + tv.getText().toString() + "%";
                        pos++;
                    }
                }

                recyclerView.setAdapter(new RecyclerViewListAdapter(database.getScores(selection.toString(), selectionVals, orderBy.toString())));

            }

            return true;
        });



        return view;
    }



    private class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolder> {
        private ArrayList<Score> scoreArrayList;

        public RecyclerViewListAdapter(ArrayList<Score> scoreArrayList) {
            this.scoreArrayList = scoreArrayList;


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
            return new RecyclerViewListAdapter.ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ((TextView) holder.itemView.findViewById(R.id.userText)).setText(scoreArrayList.get(position).getUser());
            ((TextView) holder.itemView.findViewById(R.id.timeText)).setText(String.valueOf(scoreArrayList.get(position).getTime()));
            ((TextView) holder.itemView.findViewById(R.id.scoreText)).setText(String.valueOf(scoreArrayList.get(position).getScore()));
            ((TextView) holder.itemView.findViewById(R.id.dateText)).setText(scoreArrayList.get(position).getDate());

        }

        @Override
        public int getItemCount() {
            return scoreArrayList.size();
        }


        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

            }
        }
    }

}
