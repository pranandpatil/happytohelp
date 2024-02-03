package ap.service.happytohelp.detailscreens

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ap.service.happytohelp.ui.theme.GreenOriginal
import ap.service.happytohelp.ui.theme.HappyToHelpTheme
import java.math.BigInteger
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable
fun SIPCalculatorScreen(navController: NavController?) {
    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(0) }

    val totalReturn = remember { mutableStateOf("") }
    val invAmount = remember { mutableStateOf("") }
    val estReturn = remember { mutableStateOf("") }
    HappyToHelpTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {

            Scaffold(topBar = {
                DetailScreenAppBar("SIP Calculator", Icons.Default.ThumbUp)
                { navController!!.popBackStack() }
            }) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(it)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    CustomTabs(selectedIndex) { index ->
                        selectedIndex = index
                        totalReturn.value = ""
                        invAmount.value = ""
                        estReturn.value = ""
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SIPCalculator(context, selectedIndex, totalReturn, invAmount, estReturn)
                }
            }

        }
    }
}

// For calculating the SIP return
fun calculateReturn(
    monthlyAmount: Int,
    interestPerYear: Float,
    totalYear: Int,
    selectedTab: Int
): Long {

    val periodicInterest: Double = ((interestPerYear / 12) / 100).toDouble()

    return if (selectedTab == 0) {
        (monthlyAmount * ((((1 + periodicInterest).toDouble()
            .pow((totalYear * 12).toDouble()) - 1) / periodicInterest) * (1 + periodicInterest)))
            .toLong()
    } else {
        (monthlyAmount * ((1 + (interestPerYear / 100)).toDouble()
            .pow(totalYear.toDouble()))).toLong()
    }
}

@Composable
fun CustomTabs(selectedTab: Int, tabSelected: (Int) -> Unit) {

    val list = listOf("SIP", "Lumpsum")
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.GreenOriginal, RoundedCornerShape(50))
            .padding(1.dp)
    ) {
        TabRow(selectedTabIndex = selectedTab,
            backgroundColor = MaterialTheme.colors.GreenOriginal,
            modifier = Modifier
                .clip(RoundedCornerShape(50)),
            indicator = { tabPositions: List<TabPosition> ->
                Box {}
            }
        ) {
            list.forEachIndexed { index, text ->
                val selected = selectedTab == index
                Tab(
                    modifier = if (selected) Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color.White
                        )
                    else Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colors.GreenOriginal),
                    selected = selected,
                    onClick = { tabSelected.invoke(index) },
                    text = {
                        Text(
                            text = text,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selected) MaterialTheme.colors.GreenOriginal else Color.LightGray
                        )
                    }
                )
            }
        }
    }

}

@Composable
fun SIPCalculator(
    mainContext: Context, selectedIndex: Int,
    totalReturn: MutableState<String>,
    invAmount: MutableState<String>,
    estReturn: MutableState<String>,
) {

    // Automatically get the values from the views
    val monthlyInvestment = remember { mutableStateOf("10000") }
    val interestPerYear = remember { mutableStateOf("11") }
    val totalYear = remember { mutableStateOf("10") }


    var monthlyInvestmentPosition by remember { mutableStateOf(10000.0f) }
    var interestPerYearPosition by remember { mutableStateOf(11.0f) }
    var totalYearPosition by remember { mutableStateOf(10f) }

    ShowOutlinedTextField(monthlyInvestment.value, {
        if (it.length <= 6) {
            monthlyInvestment.value = it
            if (monthlyInvestment.value.isNotEmpty()) {
                monthlyInvestmentPosition = monthlyInvestment.value.toFloat()
            }
        }
        if (it.isNotEmpty() && it.toFloat() > if (selectedIndex == 0) 100000f else 1000000f) {
            monthlyInvestment.value = if (selectedIndex == 0) "100000" else "1000000"
            if (monthlyInvestment.value.isNotEmpty()) {
                monthlyInvestmentPosition = monthlyInvestment.value.toFloat()
            }
        }
    }, if (selectedIndex == 0) "Monthly Investment" else "Total Investment", "Enter amount")

    ShowSlider(
        monthlyInvestmentPosition,
        {
            monthlyInvestmentPosition = it
            monthlyInvestment.value = monthlyInvestmentPosition.roundToInt().toString()
        },
        if (selectedIndex == 0) 500f..100000f else 500f..1000000f,
        if (selectedIndex == 0) 200 else 2000
    )

    ShowOutlinedTextField(interestPerYear.value, {
        if (it.contains(".")) {
            if (it.length <= 4) {
                interestPerYear.value = it
                if (interestPerYear.value.isNotEmpty()) {
                    interestPerYearPosition = interestPerYear.value.toFloat()
                }
            }
        } else if (it.length <= 2) {
            interestPerYear.value = it
            if (interestPerYear.value.isNotEmpty()) {
                interestPerYearPosition = interestPerYear.value.toFloat()
            }
        }

        if (it.isNotEmpty() && it.toFloat() > 30f) {
            interestPerYear.value = "30"
            if (interestPerYear.value.isNotEmpty()) {
                interestPerYearPosition = interestPerYear.value.toFloat()
            }
        }
    }, "Interest Per Year", "Enter Interest Percentage")

    ShowSlider(
        interestPerYearPosition,
        {
            interestPerYearPosition = it
            interestPerYear.value =
                ((interestPerYearPosition * 10.0).roundToInt() / 10.0).toString()
        },
        1.0f..30.0f,
        300
    )

    ShowOutlinedTextField(totalYear.value, {
        if (it.length <= 2) {
            totalYear.value = it
            if (totalYear.value.isNotEmpty()) {
                totalYearPosition = totalYear.value.toFloat()
            }

        }
        if (it.isNotEmpty() && it.toInt() > 40) {
            totalYear.value = "40"
            if (totalYear.value.isNotEmpty()) {
                totalYearPosition = totalYear.value.toFloat()
            }
        }
    }, "Total Year", "Enter Years")

    ShowSlider(
        totalYearPosition,
        {
            totalYearPosition = it
            totalYear.value = totalYearPosition.roundToInt().toString()
        },
        1f..40f,
        40
    )

    CalculatedValueText("Invested Amount = ", invAmount.value)
    CalculatedValueText("Est. Returns = ", estReturn.value)
    CalculatedValueText("Total Value = ", totalReturn.value)

    OutlinedButton(
        onClick = {
            totalReturn.value = calculateReturn(
                monthlyInvestment.value.toInt(),
                interestPerYear.value.toFloat(),
                totalYear.value.toInt(), selectedIndex
            ).toString()
            invAmount.value = if (selectedIndex == 0) {
                ((monthlyInvestment.value.toDouble() * (totalYear.value.toDouble() * 12)).toLong()).toString()
            } else {
                monthlyInvestment.value.toString()
            }

            estReturn.value = (totalReturn.value.toLong() - invAmount.value.toLong()).toString()
        },
        modifier = Modifier
            .padding(top = 10.dp, bottom = 5.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White),
        border = BorderStroke(2.dp, MaterialTheme.colors.GreenOriginal)
    ) {
        Text(
            text = "Calculate", fontSize = 18.sp,
            color = MaterialTheme.colors.GreenOriginal
        )
    }

    if (totalReturn.value.isNotEmpty() && invAmount.value.isNotEmpty() && estReturn.value.isNotEmpty()) {
        val invAmountPercentage = (invAmount.value.toFloat() * 100) / totalReturn.value.toFloat()
        val estReturnPercentage = (estReturn.value.toFloat() * 100) / totalReturn.value.toFloat()
        PieChart(listOf(invAmountPercentage, estReturnPercentage))
    }
}


@Composable
fun ShowSlider(
    initialPosition: Float,
    onValueChangeEvent: (Float) -> Unit,
    pValueRange: ClosedFloatingPointRange<Float>,
    pSteps: Int
) {
    Slider(
        modifier = Modifier.padding(top = 10.dp),
        value = initialPosition,
        onValueChange = { onValueChangeEvent.invoke(it) },
        valueRange = pValueRange,
        steps = pSteps,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.GreenOriginal,
            activeTrackColor = MaterialTheme.colors.GreenOriginal,
            inactiveTrackColor = Color.LightGray,
            activeTickColor = MaterialTheme.colors.GreenOriginal,
            inactiveTickColor = Color.LightGray
        )
    )
}

@Composable
fun ShowOutlinedTextField(
    initValue: String,
    onValueChanged: (String) -> Unit,
    labelText: String,
    placeHolderText: String
) {
    OutlinedTextField(
        value = initValue,
        onValueChange = {
            onValueChanged.invoke(it)
        },
        label = { Text(text = labelText) },
        placeholder = { Text(text = placeHolderText) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            textColor = Color.Black,
            focusedBorderColor = MaterialTheme.colors.GreenOriginal,
            unfocusedBorderColor = MaterialTheme.colors.GreenOriginal,
            placeholderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
    )
}

@Composable
fun CalculatedValueText(valueNameText: String, valueText: String) {
    Text(
        buildAnnotatedString {
            append(valueNameText)
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                if (valueText != null && valueText.isNotEmpty()) {
                    append(
                        DecimalFormat(" ##,##,##,##,###")
                            .format(BigInteger(valueText))
                    )
                } else {
                    append((valueText))
                }
            }
        },
        color = Color.Black,
        modifier = Modifier.padding(top = 10.dp)
    )
}

@Composable
fun PieChart(
    values: List<Float> = listOf(40f, 60f),
    colors: List<Color> = listOf(Color(0xFF7ACC4B), Color(0xFFBBF29B)/*, Color(0xFF092D40)*/),
    legend: List<String> = listOf("Invested Amount", "Est. Returns"),
    size: Dp = 150.dp
) {

    // Sum of all the values
    val sumOfValues = values.sum()

    // Calculate each proportion value
    val proportions = values.map {
        it * 100 / sumOfValues
    }

    // Convert each proportions to angle
    val sweepAngles = proportions.map {
        360 * it / 100
    }

    Row(
        modifier = Modifier.padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(size = size)) {
            var startAngle = -90f
            for (i in sweepAngles.indices) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i],
                    useCenter = true
                )
                startAngle += sweepAngles[i]
            }

        }
        Spacer(modifier = Modifier.width(20.dp))
        Box(contentAlignment = Alignment.Center) {
            Column() {
                for (i in values.indices) {
                    DisplayLegend(color = colors[i], legend = legend[i])
                }
            }
        }
    }
}

@Composable
fun DisplayLegend(color: Color, legend: String) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.width(16.dp),
            thickness = 4.dp,
            color = color
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(text = legend, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun SIPCalculatorScreenPreview() {
    SIPCalculatorScreen(null)

}