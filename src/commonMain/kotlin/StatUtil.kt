fun doStats(testBatch: List<BasicLitmusTest>) {
//    timeBetweenInteresting(testBatch)
}

fun stackFreqSplit(testBatch: List<BasicLitmusTest>) {
    println("run ended")
    val outcomes = listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1)

    val chunkSize = 50_000
    testBatch.chunked(chunkSize).map { part ->
        part.map { it.outcome }
            .groupingBy { it }
            .eachCount()
            .mapKeys { (k, _) -> outcomes.indexOf(k) }
            .mapValues { (_, v) -> v.toDouble() / chunkSize }
            .entries.sortedBy { it.key }
            .let { es -> outcomes.indices.map { i -> es.firstOrNull { it.key == i }?.value ?: 0 } }
            .joinToString(",")
            .let { println(it) }
    }
}

fun timeBetweenInteresting(testBatch: List<BasicLitmusTest>) {
    val outcomeSetup = testBatch.first().getOutcomeSetup()
    val times = mutableListOf<Int>()
    var cnt = 0
    for (r in testBatch) {
        if (outcomeSetup!!.getType(r.outcome) == OutcomeType.INTERESTING) {
            times.add(cnt)
            cnt = 0
        } else {
            cnt++
        }
    }
    val dist = times.groupingBy { it }.eachCount()
    dist.entries
        .map { (k, v) -> "$k,$v" }
        .forEach { println(it) }
}
