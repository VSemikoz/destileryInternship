package ru.vssemikoz.newsfeed.usecases;


public interface BaseUseCase<T, P> {
    T run(P params);
}
