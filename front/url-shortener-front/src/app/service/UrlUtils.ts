export class UrlUtils {

  urlRegex = new RegExp(
    "^((http|https)://)?"
    + "([0-9a-z\\-]+\\.)+[a-z]{2,6}"
    + "(/[a-z0-9\\-._~:?#[\\]\\/@!$&'()*+,;=%]*)?$", "i");


  public isValidURL(url: string): boolean {
    return this.urlRegex.test(url);
  }

  public sanitizeUrl(url: string): string {
    if (!(url.startsWith("http://") || url.startsWith("https://"))) {
      return "https://"  + url
    }
    return url;
  }

}
