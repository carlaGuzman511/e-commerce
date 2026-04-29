import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TenantResolver} from './core/services/tenant-resolver.resolver';
import {AuthGuard} from './core/guards/auth.guard';

const routes: Routes = [
  {
    path: 'kiosko-auth',
    loadChildren: () => import('./public/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: ':tenant',
    resolve: {tenant: TenantResolver},
    children: [
      {
        path: '',
        loadChildren: () => import('./public/public.module').then(m => m.PublicModule)
      },
      {
        path: 'secure',
        canActivate: [AuthGuard],
        loadChildren: () => import('./secure/secure.module').then(m => m.SecureModule)
      },
      {path: '**', redirectTo: ''}
    ]
  },
  {path: '**', redirectTo: 'defaultTenant'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
