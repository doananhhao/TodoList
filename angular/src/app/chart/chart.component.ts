import { Component, OnInit, Input, HostListener } from '@angular/core';
import { ChartData } from 'src/models/ChartData';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  @Input() statistics: any;

  currentStatistic: any;
  popup = {
    top: 0,
    left: 0
  };
  display = "hidden";

  constructor() { }

  ngOnInit() {
  }

  checkStatistisc(): boolean {
    if (this.statistics.completed) {
      return true;
    }
    return false;
  }

  getData(): ChartData[] {
    let chartDatas: ChartData[] = [];
    for (let prop in this.statistics) {
      let chartData = new ChartData;
      chartData.title = this.getTitle(prop);
      chartData.count = this.statistics[prop].count;
      chartData.percent = this.statistics[prop].percent;
      chartData.style = this.statistics[prop].style;
      if (chartData.count > 0) {
        chartDatas.push(chartData);
      }
    }
    return chartDatas;
  }

  onMouseOver(event, data) {
    this.currentStatistic = data;
    let element = event.target.getBoundingClientRect();
    let chartTodos = document.getElementById('chartTodos').getBoundingClientRect();
    let popupElement = document.getElementsByClassName('popup_chart')[0] || undefined;
    if (popupElement) {
      let popupChart = popupElement.getBoundingClientRect();
      this.popup = {
        top: element.height + element.top + 5 - chartTodos.top,
        left: (element.left + (element.width - popupChart.width)/2 - chartTodos.left)
      }
    } else {
      this.popup = {
        top: element.height + element.top + 5 - chartTodos.top,
        left: (element.left + (element.width - 270)/2 - chartTodos.left)
      }
    }
  }

  @HostListener('mouseleave', ['$event']) mouseLeave() {
    this.currentStatistic = undefined;
  }

  getTitle(variableName: String): String {
    return variableName.replace(/([A-Z])/g, ' $1').trim();
  }

}
