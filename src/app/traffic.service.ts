import { HomeComponent } from './home/home.component';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TrafficService {
  headers: HttpHeaders;
  
  constructor(private http: HttpClient, private router: Router) { 
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    });
  }

  getAllintersections(callbackObj: HomeComponent) {
    let options = {
      headers: this.headers,
    };
    return this.http.get('http://localhost:8080/api/trafficms/getAllIntersections', options)
      .subscribe(data => {
        if (data['success']){
          callbackObj.getIntersectionsData(data["intersections"])
        }
      })
  }


  getIntersectionsTrafficData(intersections: any[], callbackObj: HomeComponent) {
    console.log("get traffic Data");
    let options = {
      headers: this.headers,
    };
    intersections.forEach(intersection => {
      return this.http.get('http://localhost:8080/api/trafficms/getTrafficByIntersection/' + intersection["id"], options)
      .subscribe(data => {
        if (data['success']){
            intersection.TrafficData = data["traffic"];
            intersection.TrafficData.forEach(intersectionData => {
            intersectionData.CameraImg = "data:image/png;base64," + intersectionData["base64Img"];
            intersectionData.targetId = "camera" + intersectionData["id"];
            
          });
          callbackObj.updateinstersectionTrafficData(intersections);
        }
      })  
    });
        
  }
  
}
