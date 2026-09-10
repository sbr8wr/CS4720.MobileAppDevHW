package edu.virginia.cs4720.sbr8wr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.virginia.cs4720.sbr8wr.ui.theme.Helloworldsbr8wrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Helloworldsbr8wrTheme {
                TipsCalculator()
            }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipsCalculator(){
    var billText by remember { mutableStateOf("") }
    var tipsPercent by remember {mutableStateOf(20)}

    val newBillText = billText.toDoubleOrNull() ?: 0.0
    val total = newBillText + newBillText * (tipsPercent/100.0)

    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = "Tips Calculator"
        )

        TextField(
            value = billText,
            onValueChange = { billText = it },
            label = {Text("Bill amount")}
        )

        Row {
            listOf(15, 18, 20, 25).forEach { pct ->
                Button(
                    onClick = { tipsPercent = pct },
                    modifier = Modifier.padding(end=8.dp)

                ) {
                    Text("$pct")
                }
            }

        }

        Text("Total: $total")

    }


}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}