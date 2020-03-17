package ru.vssemikoz.newsfeed.navigator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.Objects;

import ru.vssemikoz.newsfeed.dialogs.PickCategoryDialog;

public class Navigator {
    static public void openWebView(String url, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setPackage(null);
            context.startActivity(intent);
        }
    }

    static public void openCategoryDialogFragment(Fragment fragment, FragmentActivity fragmentActivity) {
        DialogFragment categoryDialog = new PickCategoryDialog();
        categoryDialog.onAttachFragment(fragment);
        categoryDialog.show(fragmentActivity.getSupportFragmentManager(), "categoryDialog");
    }
}
