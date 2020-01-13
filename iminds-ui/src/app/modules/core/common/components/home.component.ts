import { Component, OnInit } from '@angular/core';
import { MasterComponent } from './master.component';

@Component({
  selector: 'stl-home',
  templateUrl: './home.component.html'
})
export class HomeComponent extends MasterComponent implements OnInit {

  constructor() { 
    super();
  }

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true
  };

    public mbarChartLabels:string[] = ['2012', '2013', '2014', '2015', '2016', '2017', '2018'];
    public barChartType:string = 'bar';
    public barChartLegend:boolean = true;
  
    public barChartColors:Array<any> = [
    {
      backgroundColor: 'rgba(130,241,103,0.9)',
      borderColor: 'rgba(105,159,177,1)',
      pointBackgroundColor: 'rgba(105,159,177,1)',
      pointBorderColor: '#fafafa',
      pointHoverBackgroundColor: '#fafafa',
      pointHoverBorderColor: 'rgba(105,159,177)'
    },
    { 
      backgroundColor: 'rgba(240,110,84,0.9)',
      borderColor: 'rgba(77,20,96,1)',
      pointBackgroundColor: 'rgba(77,20,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,20,96,1)'
    }
  ];
    public barChartData:any[] = [
      {data: [55, 60, 75, 82, 56, 62, 80], label: 'Accepted'},
      {data: [58, 55, 60, 79, 66, 57, 90], label: 'Declined'}
    ];
  
    // events
    public chartClicked(e:any):void {
      console.log(e);
    }
  
    public chartHovered(e:any):void {
      console.log(e);
    }
  
    public randomize():void {
      let data = [
        Math.round(Math.random() * 100),
        Math.round(Math.random() * 100),
        Math.round(Math.random() * 100),
        (Math.random() * 100),
        Math.round(Math.random() * 100),
        (Math.random() * 100),
        Math.round(Math.random() * 100)];
      let clone = JSON.parse(JSON.stringify(this.barChartData));
      clone[0].data = data;
      this.barChartData = clone;
    } 


    //////////////////////////////////////////////////////////////////////


    public barChartOptions1:any = {
      scaleShowVerticalLines: false,
      responsive: true
    };
  
      public mbarChartLabels1:string[] = ['TU', 'DU', 'Admin', 'HR', 'Finance'];
      public barChartType1:string = 'bar';
      public barChartLegend1:boolean = true;
    
      public barChartColors1:Array<any> = [
      {
        backgroundColor: 'rgba(73, 93, 235,0.9)',
        borderColor: 'rgba(105,159,177,1)',
        pointBackgroundColor: 'rgba(105,159,177,1)',
        pointBorderColor: '#fafafa',
        pointHoverBackgroundColor: '#fafafa',
        pointHoverBorderColor: 'rgba(105,159,177)'
      },
      { 
        backgroundColor: 'rgba(141, 79, 231,0.9)',
        borderColor: 'rgba(77,20,96,1)',
        pointBackgroundColor: 'rgba(77,20,96,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(77,20,96,1)'
      }
    ];
      public barChartData1:any[] = [
        {data: [12, 18, 2, 6, 4], label: 'Total'},
        {data: [5, 11, 0, 4, 4], label: 'Accepted'}
      ];
    
      // events
      public chartClicked1(e:any):void {
        console.log(e);
      }
    
      public chartHovered1(e:any):void {
        console.log(e);
      }
    
      public randomize1():void {
        let data = [
          Math.round(Math.random() * 100),
          Math.round(Math.random() * 100),
          Math.round(Math.random() * 100),
          (Math.random() * 100),
          Math.round(Math.random() * 100),
          (Math.random() * 100),
          Math.round(Math.random() * 100)];
        let clone = JSON.parse(JSON.stringify(this.barChartData1));
        clone[0].data = data;
        this.barChartData1 = clone;
      } 

  ngOnInit() {
  }

}
