import { SimpleUserResponse } from './simple-user-response.model';

export interface SimpleAuthResponse {
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
  user: SimpleUserResponse;
}
