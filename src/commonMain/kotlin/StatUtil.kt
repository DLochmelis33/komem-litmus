fun stackFreqSplit(testBatch: List<BasicLitmusTest>) {
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
