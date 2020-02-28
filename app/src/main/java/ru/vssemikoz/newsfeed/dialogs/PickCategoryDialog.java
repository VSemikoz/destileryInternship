package ru.vssemikoz.newsfeed.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.Category;

public class PickCategoryDialog extends DialogFragment {
    public interface OnCategorySelectedListener {
        void onCategorySelected(Category selectCategory);
    }

    private OnCategorySelectedListener nListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] categories = Category.getCategoryNameList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chose_category_title)
                .setItems(categories, (dialog, which) -> {
                    nListener.onCategorySelected(Category.values()[which]);
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            nListener = (OnCategorySelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnCategorySelectedListener");
        }
    }
}
