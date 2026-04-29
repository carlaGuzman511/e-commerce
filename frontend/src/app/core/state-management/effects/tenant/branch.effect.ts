import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as BranchActions from '../../actions/tenant/branch.actions';
import { BranchHttpClientService } from 'src/app/core/services/http/branch-http-client.service';
import { catchError, exhaustMap, map, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Branch } from 'src/app/core/models/branch.model';

@Injectable()
export class BranchEffects {

  loadBranches$ = createEffect(() =>
    this._actions$.pipe(
      ofType(BranchActions.loadBranches),
      mergeMap(() =>
        this._branchService.getAll().pipe(
          map((response: any) => {
            const branches = response?.content ?? [];
            return BranchActions.loadBranchesSuccess({ branches })
          }),
          catchError(error => of(BranchActions.loadBranchesFailure({ error })))
        )
      )
    )
  );

  createBranch$ = createEffect(() =>
    this._actions$.pipe(
      ofType(BranchActions.createBranch),
      exhaustMap(({ branch }) =>
        this._branchService.create(branch).pipe(
          map(branch => BranchActions.createBranchSuccess({ branch })),
          catchError(error => of(BranchActions.createBranchFailure({ error })))
        )
      )
    )
  );

  loadBranchById$ = createEffect(() =>
    this._actions$.pipe(
      ofType(BranchActions.loadBranchById),
      mergeMap(({ branchId }) =>
        this._branchService.getById(branchId).pipe(
          map(branch => BranchActions.loadBranchByIdSuccess({ branch })),
          catchError(error => of(BranchActions.loadBranchByIdFailure({ error })))
        )
      )
    )
  );

  updateBranch$ = createEffect(() =>
    this._actions$.pipe(
      ofType(BranchActions.updateBranch),
      exhaustMap(({ branch }) => {
        if (!branch.id) {
          return of(
            BranchActions.updateBranchFailure({
              error: 'branch.id is undefined'
            })
          );
        }

        return this._branchService.update(branch.id, branch).pipe(
           map((branch: Branch) => {
            return BranchActions.updateBranchSuccess({ branch })
          }),
          catchError(error => of(BranchActions.updateBranchFailure({ error })))
        );
      })
    )
  );

  deleteBranch$ = createEffect(() =>
    this._actions$.pipe(
      ofType(BranchActions.deleteBranch),
      mergeMap(({ branchId }) =>
        this._branchService.removeById(branchId).pipe(
          map(() => BranchActions.deleteBranchSuccess({ branchId })),
          catchError(error => of(BranchActions.deleteBranchFailure({ error })))
        )
      )
    )
  );
 
  constructor(private _actions$: Actions, private _branchService: BranchHttpClientService){}
}