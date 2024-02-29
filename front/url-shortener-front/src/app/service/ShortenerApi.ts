import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ShortenerApi {

  constructor(private http: HttpClient) {
  }

  public shortenUrl(payload : string) : Observable<any>{
    return this.http.post(environment.apiUri + "/api/shorten" , payload ,  {responseType: 'json'});
  }

  public retrieveUrl(url : string): Observable<any>{
    return this.http.get(environment.apiUri + "/api/retrieve/" + url ,  {responseType: 'text'});
  }

}
