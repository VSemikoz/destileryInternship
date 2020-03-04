package ru.vssemikoz.newsfeed.models;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.R;

public enum Source {
    ALL(R.string.source_all),
    RT(R.string.source_RT),
    LENTA(R.string.source_LENTA),
    RBC(R.string.source_RBC);

    private Integer source;

    public static String getRequestName(Source source) {
        return source.toString();
    }

    public static String getDisplayName(Source source) {
        if (source == Source.ALL) {
            return MainApplication.getContext().getString(R.string.display_source_no_limits);
        } else {
            return Source.getRequestName(source);
        }
    }

    Source(Integer source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return MainApplication.getContext().getString(source);
    }

    public static String[] getSourceNameList() {
        Source[] values = Source.values();
        String[] sources = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            sources[i] = getDisplayName(values[i]);
        }
        return sources;
    }
}
