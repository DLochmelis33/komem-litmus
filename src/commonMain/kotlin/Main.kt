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

    val sampleTest = testProducer()
//    print(sampleTest.name + ",")
    val param = LitmusTestParameters(null, 5, null, ::CinteropBarrier)
    val results = runner.runTest(1_000_000, param, testProducer)
//    val interestingCount = results.countOfType(OutcomeType.INTERESTING)
//    val forbiddenCount = results.countOfType(OutcomeType.FORBIDDEN)
//    val totalCount = results.sumOf { it.count }
//    println("$totalCount,$interestingCount,$forbiddenCount")
//        if(forbiddenCount != 0L)
    results.prettyPrint()
}
