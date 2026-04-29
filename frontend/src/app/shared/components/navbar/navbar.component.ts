import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarComponent {
  constructor(private router: Router) {}

  public loginPage(): void {
    this.router.navigate(['/kiosko-auth/login']);
  }
}
