package shateq.moonlight.util

import java.util.concurrent.ThreadFactory

/**
 * ThreadFactory implementation.
 */
class ThreadFactoryImpl(private val pattern: String) : ThreadFactory {
    private var count: Int = 0

    override fun newThread(r: Runnable): Thread {
        return Thread(r, pattern.replace("%d", "${count++}"))
    }
}
