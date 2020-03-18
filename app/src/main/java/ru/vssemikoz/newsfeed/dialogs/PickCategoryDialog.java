package ru.vssemikoz.newsfeed.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.Category;

public class PickCategoryDialog extends DialogFragment {
    public interface OnCategorySelectedListener {
        void onCategorySelected(Category selectCategory);
    }

    private OnCategorySelectedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] categories = Category.getCategoryNameList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chose_category_title)
                .setItems(categories, (dialog, which) -> {
                    listener.onCategorySelected(Category.values()[which]);
                });
        return builder.create();
    }

    @Override
        public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

        try {
            this.listener = (OnCategorySelectedListener) childFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(childFragment.toString() + "implement listener");
        }
    }
}
