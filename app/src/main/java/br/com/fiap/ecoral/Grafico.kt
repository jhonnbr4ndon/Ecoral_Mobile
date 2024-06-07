package br.com.fiap.ecoral

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class Grafico : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grafico, container, false)

        val barChart: BarChart = view.findViewById(R.id.barChart)

        // Dados do gráfico
        val dadosAgora = mutableListOf(10f, 20f, 35f, 25f, 98f, 10f)
        val dadosSemana = mutableListOf(15f, 25f, 30f, 20f, 80f, 15f)
        val dadosMes = mutableListOf(20f, 30f, 40f, 30f, 90f, 20f)
        val dadosAno = mutableListOf(25f, 35f, 45f, 35f, 95f, 25f)

        val dataSet = BarDataSet(getEntries(dadosAgora), "Poluentes")
        dataSet.colors = getColors()
        val barData = BarData(dataSet)
        barChart.data = barData

        // Configurar o eixo x
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false) // Desabilitar as linhas da grade
        xAxis.position = XAxis.XAxisPosition.BOTTOM // Posicionar o eixo x na parte inferior
        xAxis.setDrawAxisLine(false) // Desabilitar a linha do eixo
        xAxis.valueFormatter = IndexAxisValueFormatter(
            mutableListOf(
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )

        // Configurar o eixo y
        val yAxis: YAxis = barChart.axisLeft
        yAxis.setDrawGridLines(true) // Habilitar as linhas da grade
        yAxis.setDrawAxisLine(true) // Habilitar a linha do eixo
        yAxis.setDrawLabels(true) // Habilitar labels no eixo y

        // Definir os valores mínimos e máximos do eixo y
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f
        yAxis.setLabelCount(6, true) // Definir o número de rótulos no eixo y

        // Definir os valores dos rótulos do eixo y
        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return value.toInt().toString()
            }
        }

        // Desabilitar o eixo y direito
        barChart.axisRight.isEnabled = false

        // Desabilitar a legenda
        barChart.legend.isEnabled = false

        // Configurar o gráfico para barras horizontais
        barChart.setFitBars(true)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(false)
        barChart.setDrawGridBackground(false)
        barChart.description.isEnabled = false // Desabilitar a descrição do gráfico
        barChart.setTouchEnabled(false) // Desabilitar interações do usuário
        barChart.setDragEnabled(false) // Desabilitar arrastar
        barChart.setScaleEnabled(false) // Desabilitar zoom
        barChart.setPinchZoom(false) // Desabilitar zoom com dois dedos

        // Adicionar ouvintes de clique aos botões
        view.findViewById<View>(R.id.agora).setOnClickListener {
            updateChartData(barChart, dadosAgora, it as TextView)
        }
        view.findViewById<View>(R.id.semana).setOnClickListener {
            updateChartData(barChart, dadosSemana, it as TextView)
        }
        view.findViewById<View>(R.id.mes).setOnClickListener {
            updateChartData(barChart, dadosMes, it as TextView)
        }
        view.findViewById<View>(R.id.ano).setOnClickListener {
            updateChartData(barChart, dadosAno, it as TextView)
        }

        // Renderizar o gráfico
        barChart.invalidate()

        return view
    }

    private fun getEntries(dados: MutableList<Float>): MutableList<BarEntry> {
        val entries = mutableListOf<BarEntry>()
        for (i in dados.indices) {
            entries.add(BarEntry(i.toFloat(), dados[i]))
        }
        return entries
    }

    private fun getColors(): MutableList<Int> {
        return mutableListOf(
            Color.parseColor("#C0C0C0"), // Cor cinza para metais
            Color.parseColor("#FFA500"), // Cor laranja para plástico
            Color.parseColor("#008080"), // Cor azul-esverdeado para salinidade
            Color.parseColor("#98FB98"), // Cor vermelha para temperatura
            Color.parseColor("#00BCD4"), // Cor azul claro para turbidez
            Color.parseColor("#4169E1")  // Cor azul para pH
        )
    }

    private fun updateChartData(barChart: BarChart, dados: MutableList<Float>, textView: TextView) {
        val dataSet = BarDataSet(getEntries(dados), "Poluentes")
        dataSet.colors = getColors()
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.invalidate()

        // Definir a cor de destaque para o texto do botão pressionado
        val pressedColor = Color.parseColor("#00BCD4") // Defina a cor de destaque desejada
        textView.setTextColor(pressedColor)


        // Restaurar a cor original do texto dos outros botões
        val buttons = listOf<TextView>(
            textView,
            requireView().findViewById(R.id.agora),
            requireView().findViewById(R.id.semana),
            requireView().findViewById(R.id.mes),
            requireView().findViewById(R.id.ano)
        )
        for (button in buttons) {
            if (button != textView) {
                button.setTextColor(Color.BLACK) // Defina a cor original dos outros botões
            }
        }
    }

}
