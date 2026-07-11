package com.alexbralves.android.rxjava.extensions

import com.alexbralves.android.rxjava.core.AsyncResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T : Any> Observable<T>.toAsyncResult(): Observable<AsyncResult<T>> =
    map<AsyncResult<T>> { AsyncResult.Success(it) }
        .onErrorReturn { AsyncResult.Failure(it.toErrorResponse()) }
        .startWithItem(AsyncResult.Loading)

fun <T : Any> Observable<T>.applySchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread(),
): Observable<T> = subscribeOn(subscribeScheduler).observeOn(observeScheduler)

/** Alternativa reutilizável quando compose() deixa o encadeamento mais legível. */
fun <T : Any> asyncSchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread(),
) = ObservableTransformer<T, T> { upstream ->
    upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)
}

