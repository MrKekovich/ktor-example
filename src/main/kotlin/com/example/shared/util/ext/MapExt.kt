package com.example.shared.util.ext

inline fun <K, V, RK, RV> Map<K, V>.associate(transform: (Map.Entry<K, V>) -> Pair<RK, RV>): Map<RK, RV> =
    this
        .map(transform)
        .toMap()
