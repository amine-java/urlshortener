import {Component, OnInit} from '@angular/core';
import {ShortenerApi} from "../../service/ShortenerApi";
import {ActivatedRoute, Router} from "@angular/router";
import {ShortenUrlDTO} from "../../model/shortenUrlDTO";
import {UrlUtils} from "../../service/UrlUtils";

@Component({
  selector: 'app-shorten',
  templateUrl: './shorten.component.html',
  styleUrls: ['./shorten.component.css']
})
export class ShortenComponent implements OnInit {

  urlToShorten : string = "";
  loading= false;
  showResult = false;
  shortenUrlDTO ?: ShortenUrlDTO;
  invalidUrl: boolean = false;

  constructor(private api : ShortenerApi , private urlUtils : UrlUtils , private router: Router , private route: ActivatedRoute){
  }

  /**
   * If a short url is appended then we fetch the result and redirect it.
   */
  ngOnInit(): void {
    this.route.paramMap.subscribe(parameterMap => {
      const shortUrl  = parameterMap.get('shortUrl');
      if (!!shortUrl){
        this.loading = true;
        this.redirectToOriginalUrl(shortUrl);
      }
    });
  }

  /**
   * Sanitize, validate and fetch the short version of the URL
   */
  onSubmit() {
    // Sanitize the URL
    this.urlToShorten = this.urlUtils.sanitizeUrl(this.urlToShorten);
    if (this.urlUtils.isValidURL(this.urlToShorten)) {
      this.api.shortenUrl(this.urlToShorten).subscribe(res => {
        this.shortenUrlDTO = new ShortenUrlDTO(res);
        this.showResult = true;
        this.invalidUrl = false;
      });
    } else {
      this.invalidUrl = true;
      this.showResult = false;
    }
  }


  /**
   * Redirects the URL to its origin
   * @param url
   */
  redirectToOriginalUrl(url: string) {
    this.api.retrieveUrl(url).subscribe(res => {
        setTimeout(() =>
          {
            window.location.href = res;
          },
          2000);
      },
      onError => {
        this.router.navigate(['/']);
      }
    );
  }
}
