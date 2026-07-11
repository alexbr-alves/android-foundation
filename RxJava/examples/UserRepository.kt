package com.alexbralves.android.rxjava.examples

import com.alexbralves.android.rxjava.core.AsyncResult
import com.alexbralves.android.rxjava.extensions.toAsyncResult
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class UserRepository {
    fun getUsers(): Observable<AsyncResult<List<User>>> =
        Observable.just(
            listOf(
                User(1, "Ada Lovelace"),
                User(2, "Grace Hopper"),
            ),
        )
            .delay(1, TimeUnit.SECONDS)
            .toAsyncResult()
}

