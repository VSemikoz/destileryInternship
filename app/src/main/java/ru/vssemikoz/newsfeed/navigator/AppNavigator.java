package ru.vssemikoz.newsfeed.navigator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

public class AppNavigator implements Navigator {
    @Inject
    Context context;

    @Inject
    public AppNavigator(Context context) {
        this.context = context;
    }

    @Override
    public void openWebView(String url) {
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
}
