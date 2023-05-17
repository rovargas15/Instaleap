package com.example.instaflix

import io.mockk.MockKAnnotations
import org.junit.Before

abstract class BaseTest {

    @Before
    open fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }
}
