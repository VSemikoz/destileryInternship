package ru.vssemikoz.newsfeed.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.Source;

public class PickSourceDialog extends DialogFragment {
    public interface OnSourceSelectedListener {
        void onSourceSelected(Source selectSource);
    }

    private PickSourceDialog.OnSourceSelectedListener nListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] sources = Source.getSourceNameList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chose_source_title)
                .setItems(sources, (dialog, which) -> {
                    nListener.onSourceSelected(Source.values()[which]);
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            nListener = (PickSourceDialog.OnSourceSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnSourceSelectedListener");
        }
    }
}
