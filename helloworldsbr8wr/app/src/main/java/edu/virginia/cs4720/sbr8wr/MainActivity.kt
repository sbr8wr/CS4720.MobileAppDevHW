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
                HelloWorld()
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun HelloWorld(){
    var name by remember {mutableStateOf("Your name here")}
    val salutations = listOf("Hello", "Hi There", "Bonjour")

    var selectedSalutation by remember { mutableStateOf(salutations[0])}

    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = "$selectedSalutation, $name!"
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = {Text("Enter your name")}
        )

        Row {
            salutations.forEach { text ->
                Button(

                    onClick = { selectedSalutation = text },
                    modifier = Modifier.padding(end=8.dp)

                ) {
                    Text(text)
                }
            }
        }
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