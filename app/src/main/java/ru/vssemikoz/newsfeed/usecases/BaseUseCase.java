package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

public interface BaseUseCase<T,P> {
    List<T> run(List<T> itemsList, P params);
}
