package ru.vssemikoz.newsfeed.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.utils.Enum;

public class PickCategoryDialog extends DialogFragment {
    public interface NoticeDialogListener {
        void onDialogSelectCategory(Category selectCategory);
    }
    private NoticeDialogListener nListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] categoryStrList = Enum.getCategoryNameList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выбрать категрию")
                .setItems(categoryStrList, (dialog, which) -> {
                    nListener.onDialogSelectCategory(Category.values()[which]);
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            nListener = (NoticeDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
