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

    for(n in generateSequence(10_000) { (it * 1.1).toInt() }) {
        val param = LitmusTestParameters(
            affinityScheduleUnrestricted(2)[0], 300, null, ::CinteropBarrier
        )
        val results = runner.runTest(n, param, testProducer)
        println("$n,${results.interestingFrequency}")
//        results.prettyPrint()
    }

}
