package com.chomp.library.core

sealed class Failure {
    object ServerError: Failure()
    object NetworkConnection: Failure()

    abstract class FeatureSpecificFailure: Failure()
}