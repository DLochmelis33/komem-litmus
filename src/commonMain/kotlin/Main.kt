import barriers.CinteropBarrier
import tests.*
import kotlin.time.Duration.Companion.seconds

fun main() {
    val runner = WorkerTestRunner

//    val parameters = variateParameters(
//        getAffinityManager()?.scheduleShort2().orUnrestricted(2), // affinityScheduleUnrestricted(2),
//        generateSequence(2) { it * 3 }.take(5).toList(),
//        listOf(null /* { MemShuffler(50_000) } */),
//        listOf(::CinteropBarrier)
//    ).toList()
//    val parameters = variateParameters(
//        listOf(null),
//        generateSequence(3) { it * 3 }.take(5).toList(),
//        listOf(null),
//        listOf(::CinteropBarrier)
//    ).toList()
    // TODO: on x86 manually run tests with some affinity
    // TODO: on macos also run without parallel (just in case they interfere too much)
    // TODO: also run release AND debug

    val testProducer = ::SBTest

    fun convert(r: LitmusResult) = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)
        .map { o -> r.firstOrNull { it.outcome == o }?.count?.toInt() ?: 0 }

    val resultsQueue = mutableListOf<LitmusResult>()
    for (n in generateSequence(10_000) { it * 2 }) {
        println("n = $n")
        val param = LitmusTestParameters(
            affinityScheduleUnrestricted(2)[0], 7, null, ::CinteropBarrier
        )
        val results = runner.runTest(n, param, testProducer)
        results.prettyPrint()
        resultsQueue.add(results)
        if (resultsQueue.size == 3) {
            resultsQueue.removeFirst()
            val (r1, r2) = resultsQueue
            val chi = chiSquaredTest(convert(r1), convert(r2))
            println("$chi")
            if (chi) break
        }
    }
}
