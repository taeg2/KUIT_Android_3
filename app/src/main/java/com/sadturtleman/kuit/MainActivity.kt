package com.sadturtleman.kuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sadturtleman.kuit.ui.theme.KuitTheme
import dagger.hilt.android.AndroidEntryPoint

// [TODO 1-b] 주입이 들어오는 입구. 어노테이션 하나를 붙인다.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KuitTheme {
                App()
            }
        }
    }
}
