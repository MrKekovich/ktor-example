package com.example.shared.util.task

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedDeque

class AutoDeletableStack<T> private constructor(
    val lifeTime: Long,
    private val stack: ConcurrentLinkedDeque<T>,
) : MutableCollection<T> by stack {
    constructor(
        lifeTime: Long,
    ) : this(
        lifeTime,
        ConcurrentLinkedDeque<T>(),
    )

    private val scope = CoroutineScope(SupervisorJob())

    override fun add(element: T): Boolean =
        stack.add(element).also {
            if (it) {
                scope.launch {
                    delay(lifeTime)
                    stack.remove(element)
                }
            }
        }
}
