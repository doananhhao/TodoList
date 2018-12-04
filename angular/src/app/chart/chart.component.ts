import { Component, OnInit, Input } from '@angular/core';
import { ChartData } from 'src/models/ChartData';

const listStyle: String[] = [
  'background-color-green',
  'background-color-yellow',
  'background-color-red'
];

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  @Input() statistics: any;

  constructor() { }

  ngOnInit() {
  }

  initData(): ChartData[] {
    let chartDatas: ChartData[] = this.getData();
    return this.setStyle(chartDatas);
  }

  private setStyle(chartDatas: ChartData[]): ChartData[] {
    chartDatas.forEach((data, index) => {
      data.style = listStyle[index];
    });
    return chartDatas;
  }

  private getTotal(chartDatas: ChartData[]): number {
    let total: number = 0;
    chartDatas.forEach((data) => total += data.count);
    return total;
  }

  private getData() {
    let chartDatas: ChartData[] = [];
    for (let prop in this.statistics) {
      let chartData = new ChartData;
      chartData.title = this.getTitle(prop);
      chartData.count = this.statistics[prop].count;
      chartData.percent = this.statistics[prop].percent;
      if (chartData.count > 0) {
        chartDatas.push(chartData);
      }
    }
    return chartDatas;
  }

  getTitle(variableName: String): String {
    return variableName.replace(/([A-Z])/g, ' $1').trim();
  }

  getPercent(number: number, total: number): number {
    return Math.round((number / total) * 100);
  }

}
