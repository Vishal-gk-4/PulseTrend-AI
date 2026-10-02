package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.TelemetryRepository
import com.example.model.AcuityLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("PulseTrend AI", appName)
    }

    @Test
    fun `verify ward beds configuration`() {
        val beds = TelemetryRepository.wardBeds
        assertEquals(12, beds.size)

        val bed104 = beds.firstOrNull { it.bedNumber == 104 }
        assertNotNull(bed104)
        assertEquals("Elena Rostova", bed104?.name)
        assertEquals(AcuityLevel.CRITICAL, bed104?.acuity)
        assertEquals(82, bed104?.riskScore)
    }
}
