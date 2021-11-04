package com.example.basicscodelab2

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab2.ui.theme.BasicsCodelab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelab2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color=MaterialTheme.colors.background){
                    MyApp()
                    //Greeting("Android")
                }
            }
        }
    }
}
@Composable
private fun MyApp(){
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked ={shouldShowOnboarding=false})//콜백
    } else {
        Greetings()
    }
//    Surface(color=MaterialTheme.colors.background){
//        Greeting("Android")

}

@Composable //Composable안에 있는 다른 함수 호출 가능
private fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
//remember는 재구성을 방지하는데 사용. 상태가 재설정되지 않음
    var expanded by remember { mutableStateOf(false) }
//    val extraPadding by animateDpAsState( //목록에 애니메이션 효과주기
//        if(expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio= Spring.DampingRatioMediumBouncy,
//            stiffness=Spring.StiffnessLow
//        )
//    )
    Row(modifier=Modifier
        .padding(24.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ){ //가로 fillMaxWidth 중복
        //텍스트 세로로 배치
        Column(modifier = Modifier
            .weight(1f)
            .padding(12.dp))
        {
            Text(text="Hello,")
            Text(text=name, style=MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ))

        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }

//        OutlinedButton(
//            onClick = { expanded.value=!expanded.value }
//        ) {//버튼
//            Text(if (expanded.value) "Show less" else "Show more")
//        }
    //surface로 색상지정. primary는 보라색
//    Surface(
//        color= MaterialTheme.colors.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp) //박스자체 패딩
//    ){
//
//        }
        //Text(text = "Hello $name!", modifier= Modifier.padding(24.dp))
    }


}


@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

//@Composable
//private fun Greetings(names: List<String> = listOf("World", "Compose")) {
//    Column(modifier = Modifier.padding(vertical = 4.dp)) {//세로로 패딩주기
//        for (name in names) { //for문으로 블럭 그 수만큼 만들기. 컬럼은 텍스트의 세로로 배치
//            Greeting(name = name)
//        }
//    }
//}

@Composable
private fun OnboardingScreen(onContinueClicked: ()->Unit){ //콜백 전달
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            //중앙에 표시
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelab2Theme {
        OnboardingScreen(onContinueClicked={}) //클릭시 아무것도 안함
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
        //MyApp()
        Greetings()
    }
}