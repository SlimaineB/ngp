import { Component } from '@angular/core';

import { User, Hero } from '@app/models';
import { AccountService, HeroService } from '@app/services';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: [ './home.component.css' ]
  })
export class HomeComponent {
    user: User;
    heroes: Hero[] = [];

    constructor(private accountService: AccountService, private heroService:HeroService) {
        this.user = this.accountService.userValue;
    }

    ngOnInit() {
        this.getHeroes();
      }

    getHeroes(): void {
        this.heroService.getHeroes()
          .subscribe(heroes => this.heroes = heroes.slice(1, 5));
      }
}