import React, { useEffect } from 'react';
import * as am4core from "@amcharts/amcharts4/core";
import * as am4charts from "@amcharts/amcharts4/charts";
import am4themes_animated from "@amcharts/amcharts4/themes/animated";
import am4themes_dark from "@amcharts/amcharts4/themes/dark";
import './Amcharts.css'; 

const Amcharts = ({ savings, depMoney, loaMoney, savMoney }) => {

  useEffect(() => {
    // AmCharts 테마 설정
    // am4core.useTheme(am4themes_dark);
    am4core.useTheme(am4themes_animated);

    // 차트 생성
    let chart = am4core.create("chartdiv", am4charts.PieChart3D);
    chart.hiddenState.properties.opacity = 0; // 초기 페이드 인 설정

    chart.legend = new am4charts.Legend();

    chart.data = [
      {
        name: "현금",
        country: "보유 현금",
        litres: savings
      },
      {
        name: "예금",
        country: "예금 자산",
        litres: depMoney
      },
      {
        name: "적금",
        country: "적금 자산",
        litres: savMoney
      },
      {
        name: "대출",
        country: "대출 자산",
        litres: loaMoney
      }
    ];
    chart.innerRadius = 100;

    let series = chart.series.push(new am4charts.PieSeries3D());
    series.labels.template.text = "{name}";
    series.dataFields.value = "litres";
    series.dataFields.category = "country";
    series.dataFields.width = 390;

    // 컴포넌트가 언마운트될 때 차트를 정리
    return () => {
      chart.dispose();
    };
  }, [savings, depMoney, loaMoney, savMoney]); // 빈 배열을 전달하여 useEffect가 한 번만 실행되도록 설정

  return (
    <div id="chartdiv" className="amchart-container"></div>
  );
};

export default Amcharts;
