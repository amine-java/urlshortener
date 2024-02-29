import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ShortenUrlDTO} from "../../model/shortenUrlDTO";

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  @Input()
  resultShortenUrl ?: ShortenUrlDTO;

  constructor(private router: Router ) {
  }
  ngOnInit(): void {
    if (!this.resultShortenUrl){
      this.router.navigate(['/']);
    }
  }

  copy() {
    let copy = "";
    if (this.resultShortenUrl?.shortUrl){
      copy = this.resultShortenUrl.shortUrl;
    }
    navigator.clipboard.writeText(location.href + copy);
  }

}
