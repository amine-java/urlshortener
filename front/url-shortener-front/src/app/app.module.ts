import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ShortenComponent } from './component/shorten/shorten.component';
import {FormsModule} from "@angular/forms";
import {ShortenerApi} from "./service/ShortenerApi";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule, Routes} from "@angular/router";
import { NotFoundComponent } from './component/not-found/not-found.component';
import { SummaryComponent } from './component/summary/summary.component';
import {UrlUtils} from "./service/UrlUtils";


const routes: Routes = [
  { path: '', component: ShortenComponent},
  { path: 'summary', component: SummaryComponent},
  { path: ':shortUrl', component: ShortenComponent},
  { path: '**', component: NotFoundComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    ShortenComponent,
    NotFoundComponent,
    SummaryComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    FormsModule
  ],
  providers: [ShortenerApi , UrlUtils],
  bootstrap: [AppComponent],
  exports: [HttpClientModule]
})
export class AppModule { }
