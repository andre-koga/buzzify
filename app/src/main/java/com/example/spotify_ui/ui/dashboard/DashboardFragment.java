package com.example.spotify_ui.ui.dashboard;

import static com.example.spotify_ui.Visibility.YOU;
import static com.example.spotify_ui.Wraps.wrap_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotify_ui.R;
import com.example.spotify_ui.Visibility;
import com.example.spotify_ui.Wraps;
import com.example.spotify_ui.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final LinearLayout main = binding.main;
        createWraps();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createWraps() {
        final LinearLayout main = binding.main;
        for (int i = 0; i < wrap_list.size(); i++) {
            Wraps wrap = wrap_list.get(i);
            if (wrap.getVisible() == YOU) {
                View view = LayoutInflater.from(main.getContext()).inflate(R.layout.wrap_widget, null, false);
                View details = ((ViewGroup) view).getChildAt(1);

                TextView instructor = (TextView) ((ViewGroup) details).getChildAt(0);
                TextView location = (TextView) ((ViewGroup) details).getChildAt(1);
                TextView time = (TextView) ((ViewGroup) details).getChildAt(2);

                View linear = ((ViewGroup) details).getChildAt(3);

                instructor.setText("Instructor: " + (wrap.getArtists()));
                location.setText("Location: " + wrap.getUser());
                time.setText("Date/Time: " + wrap.getWrap_name());
                main.addView(view);
            } else {
                continue;
            }
        }



    }
}