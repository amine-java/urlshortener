export class ShortenUrlDTO{
  url ?: string;
  shortUrl ?: string;
  nbClick : number = 0;
  lastAccess ?: Date;

  constructor(data : any) {
    this.url = data.url;
    this.shortUrl = data.shortUrl;
    this.nbClick = data.nbClick;
    this.lastAccess = data.lastAccess;
  }

}
