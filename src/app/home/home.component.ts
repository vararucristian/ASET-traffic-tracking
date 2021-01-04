import { TrafficService } from './../traffic.service';
import { Component, OnInit } from '@angular/core';
import { interval } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  intersections = [];
  constructor(private Traffic: TrafficService) 
  {
  }

  ngOnInit(): void {
    this.Traffic.getAllintersections(this)
  }

  getIntersectionsData(data){
    this.intersections = data;
    interval(1000).subscribe(_ => {
      this.Traffic.getIntersectionsTrafficData(this.intersections, this);
    });     
  
  }

  updateinstersectionTrafficData(data){
    this.intersections = data;    
    console.log("update intersections =",this.intersections);      
   }
  
}
