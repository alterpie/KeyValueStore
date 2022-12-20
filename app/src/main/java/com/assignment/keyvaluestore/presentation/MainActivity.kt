package com.assignment.keyvaluestore.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.assignment.keyvaluestore.presentation.ui.KeyValueStoreApp
import com.assignment.keyvaluestore.presentation.ui.theme.KeyValueStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            KeyValueStoreTheme {
                KeyValueStoreApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KeyValueStoreTheme {
        KeyValueStoreApp()
    }
}
