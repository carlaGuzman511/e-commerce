import {createFeatureSelector, createSelector} from '@ngrx/store';
import {AuthState} from '../../state/auth.state';

export const selectAuthState = createFeatureSelector<AuthState>('category');
export const selectAuthUser = createSelector(selectAuthState, state => state.user);

export const selectAuthAccessToken = createSelector(selectAuthState, state => state.accessToken);

export const selectIsAuthenticated = createSelector(selectAuthState, state => state.isAuthenticated);

export const selectAuthLoading = createSelector(selectAuthState, state => state.loading);

export const selectAuthError = createSelector(selectAuthState, state => state.error);

export const selectAuthUserRoles = createSelector(selectAuthUser, user => user?.roles ?? []);
